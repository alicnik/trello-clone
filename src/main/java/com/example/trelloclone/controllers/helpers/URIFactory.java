package com.example.trelloclone.controllers.helpers;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class URIFactory {
    public static URI create() {
        return URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/register")
                .toUriString()
        );
    }
}
