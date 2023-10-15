package com.up.up_contact_sync.controllers;

import com.up.up_contact_sync.service.contact.ContactSyncBuilder;
import com.up.up_contact_sync.service.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact-sync")
@RequiredArgsConstructor
public class ContactSyncLinkController {
    private final ContactSyncBuilder contactSyncBuilder;
    private final SecurityUtils securityUtils;

    @GetMapping("/link")
    public String getLink(Authentication auth){
        var username = securityUtils.getUsernameFromToken(auth);
        return contactSyncBuilder.buildLink(username);
    }



}
