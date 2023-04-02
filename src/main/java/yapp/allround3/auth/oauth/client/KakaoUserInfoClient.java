package yapp.allround3.auth.oauth.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import yapp.allround3.common.exception.CustomException;

@Component("KAKAO")
@Slf4j
public class KakaoUserInfoClient implements OauthUserInfoClient {

    private final ObjectMapper objectMapper = new ObjectMapper();
    public OauthUserInfo getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String body = response.getBody();
        try {
            JsonNode jsonNode = objectMapper.readTree(body);
            String id = jsonNode.get("id").asText();
            String nickname = jsonNode.get("properties").get("nickname").asText();
            String email = jsonNode.get("kakao_account").get("email").asText();
            String imageUrl = jsonNode.get("properties").get("profile_image").asText();

            return OauthUserInfo.builder()
                    .oauthId(id)
                    .nickname(nickname)
                    .email(email)
                    .imageUrl(imageUrl)
                    .build();
        } catch (Exception e) {
            throw new CustomException("Oauth 회원 정보 조회 에러");
        }
    }
}
