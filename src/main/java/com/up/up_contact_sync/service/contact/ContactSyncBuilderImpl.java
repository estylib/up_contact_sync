package com.up.up_contact_sync.service.contact;

import com.up.up_contact_sync.exceptions.ErrorCode;
import com.up.up_contact_sync.exceptions.UPExceptionBuilder;
import com.up.up_contact_sync.model.UserToken;
import com.up.up_contact_sync.repository.ContactTokenRepository;
import com.up.up_contact_sync.service.hash.HashGenerator;
import com.up.up_contact_sync.service.utils.UPLogger;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactSyncBuilderImpl implements ContactSyncBuilder {
    private final HashGenerator hashGenerator;
    private final ContactTokenRepository contactTokenRepository;

    private final MongoManager mongoManager;
    private final UPExceptionBuilder upExceptionBuilder;


    @Value("${FILE_UPLOADER_URL}")
    private String fileUploaderUrl;
    @Value("${CONTACT_TOKEN_EXPIRED_TIME}")
    private long tokenExpiredTime;
    @Override
    public String buildLink(String username) {
        if(contactTokenRepository.existsByUsername(username)){
            contactTokenRepository.deleteByUsername(username);
        }
        var token =hashGenerator.generateTokenHash();
        var userToken = getUserToken(username, token);

        //save to database
        contactTokenRepository.save(userToken);

        var link =String.format("%s%s", fileUploaderUrl,token);
        UPLogger.log().info("{} buildLink: link: {}", this.getClass().getSimpleName(), link);
        return link;
    }

    @Override
    @SneakyThrows
    public void saveUserContacts(MultipartFile file,
                                 String token) {
       //check token expiration
        var userToken = contactTokenRepository.findByToken(token);
        if(userToken.isPresent()&& userToken.get().getExpiredTokenDate().isAfter(Instant.now())) {
            byte[] fileBytes = file.getBytes();
            var encryptedData = Base64.getEncoder().encodeToString(fileBytes);

            // Store the encrypted content in MongoDB
            mongoManager.saveData(encryptedData, token);
            //update userToken Status
            userTokenUpdateStatus(userToken);
            return;
        }

        throw  upExceptionBuilder.getUpException(ErrorCode.UP_INVALID_CONTACT_TOKEN, HttpStatus.FORBIDDEN,
                Map.of("message", "Check token its invalid or expired"));
    }



    @Override
    @SneakyThrows
    public void getContactsFile(String token,  HttpServletResponse response) {
        //todo check if token expired
        var resource = mongoManager.getFile(token);
        try {
            resource.getInputStream().transferTo(response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            throw  upExceptionBuilder.getUpException(ErrorCode.UP_INVALID_DATA, HttpStatus.BAD_REQUEST,
                    Map.of("message", e.getMessage()));
        }


    }

    private UserToken getUserToken(String username, String token) {
        return UserToken.builder().token(token).username(username).expiredTokenDate(Instant.now().plus(tokenExpiredTime, ChronoUnit.MINUTES)).build();
    }

    private void userTokenUpdateStatus(Optional<UserToken> userToken) {
        var toSave = userToken.get();
        toSave.setUsed(true);
        contactTokenRepository.save(toSave);
    }
}
