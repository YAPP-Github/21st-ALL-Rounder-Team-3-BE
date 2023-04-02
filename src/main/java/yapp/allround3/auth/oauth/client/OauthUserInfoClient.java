package yapp.allround3.auth.oauth.client;

public interface OauthUserInfoClient {
    OauthUserInfo getUserInfo(String accessToken);
}
