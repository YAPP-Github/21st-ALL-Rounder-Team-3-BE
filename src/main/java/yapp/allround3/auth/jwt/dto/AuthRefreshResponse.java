package yapp.allround3.auth.jwt.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthRefreshResponse {

	private String appToken;

	public static AuthRefreshResponse from(String appToken) {
		return new AuthRefreshResponse(appToken);
	}
}
