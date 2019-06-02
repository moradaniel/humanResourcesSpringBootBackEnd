package com.baeldung.springbootsecurityrest.controller;

import org.humanResources.security.model.User2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;

@RestController
@CrossOrigin
public class UserController {


    @RequestMapping(value="/login",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //@RequestMapping(value="/login", method=RequestMethod.POST, produces="application/json")
    //@ResponseBody
    //@RequestMapping("/login")
    public boolean login(@RequestBody User2 user) {
        return
                user.getUsername().equals("username") && user.getPassword().equals("password");
    }

    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization")
                .substring("Basic".length()).trim();
        return () ->  new String(Base64.getDecoder()
                .decode(authToken)).split(":")[0];
    }
}