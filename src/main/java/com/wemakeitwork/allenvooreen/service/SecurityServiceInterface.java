package com.wemakeitwork.allenvooreen.service;

public interface SecurityServiceInterface {
    String findLoggedInUsername();

    void autoLogin(String membername, String password);
}
