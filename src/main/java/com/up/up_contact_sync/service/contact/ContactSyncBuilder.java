package com.up.up_contact_sync.service.contact;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

public interface ContactSyncBuilder {
    String buildLink(String username);

    void saveUserContacts(MultipartFile file, String token);

    void getContactsFile(String token,  HttpServletResponse response);
}
