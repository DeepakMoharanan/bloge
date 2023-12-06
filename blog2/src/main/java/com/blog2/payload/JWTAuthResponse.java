package com.blog2.payload;

public class JWTAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public JWTAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}