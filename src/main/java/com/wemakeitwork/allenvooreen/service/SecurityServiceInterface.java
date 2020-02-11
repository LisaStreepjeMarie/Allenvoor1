package com.wemakeitwork.allenvooreen.service;

public interface SecurityServiceInterface {
    String findLoggedInUsername();

    void autoLogin(String memberName, String password, boolean enabled);

    String validatePasswordResetToken(int id, String token);

}
