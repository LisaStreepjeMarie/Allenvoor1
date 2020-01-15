package com.wemakeitwork.allenvooreen.service;

public interface SecurityService {
    String findLoggedInUsername();

    // void autoLogin(String username, String password);
    void autoLogin(String membername, String password);
}
