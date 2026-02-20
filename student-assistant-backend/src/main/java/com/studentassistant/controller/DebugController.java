package com.studentassistant.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/debug")
public class DebugController {

    @GetMapping("/me")
    public String checkJwt(HttpServletRequest request) {

        Object username = request.getAttribute("username");
        Object role = request.getAttribute("role");

        return "Username: " + username + " | Role: " + role;
    }
}