package com.openclassrooms.front.services;

import jakarta.servlet.http.HttpSession;

public interface AuthService {
    boolean authenticate(String username, String password, HttpSession session);
    void logout(HttpSession session);
}
