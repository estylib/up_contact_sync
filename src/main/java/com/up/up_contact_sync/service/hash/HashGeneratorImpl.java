package com.up.up_contact_sync.service.hash;

import com.up.up_contact_sync.repository.ContactTokenRepository;
import com.up.up_contact_sync.service.utils.UPLogger;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HashGeneratorImpl implements HashGenerator {
    @Value("${USE_LETTERS}")
    private boolean useLetters;

    @Value("${USE_NUMBERS}")
    private boolean useNumbers;
    @Value("${TOKEN_LENGTH}")
    private int tokenLength;

    private final ContactTokenRepository contactTokenRepository;



    @Override
    public String generateTokenHash() {
        var res = true;
        String generatedString="";
        while(res) {
            generatedString = RandomStringUtils.random(tokenLength, useLetters, useNumbers);
            res =contactTokenRepository.findByToken(generatedString).isPresent();
        }

        UPLogger.log().info("{} method: generateTokenHash, token: {}", this.getClass().getSimpleName(), generatedString);
        return generatedString;
    }
}
