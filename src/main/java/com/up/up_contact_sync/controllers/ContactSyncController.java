package com.up.up_contact_sync.controllers;

import com.up.up_contact_sync.service.contact.ContactSyncBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/contact-sync/contacts")
@RequiredArgsConstructor
public class ContactSyncController {
    private final ContactSyncBuilder contactSyncBuilder;
    @PostMapping()
    public void sendContacts(@RequestParam("file") MultipartFile file,
                             @RequestParam("token") String token){
        contactSyncBuilder.saveUserContacts(file, token);
    }

    @GetMapping("/{token}")
    public void getContacts(@PathVariable("token")String token, HttpServletResponse response){
         contactSyncBuilder.getContactsFile(token, response);
    }
}
