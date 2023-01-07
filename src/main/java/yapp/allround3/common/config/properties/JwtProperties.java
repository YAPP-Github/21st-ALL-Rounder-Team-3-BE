package yapp.allround3.common.config.properties;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class JwtProperties {
	private final String appTokenSecret = "78220dc4-df36-46cd-96fd-108136fbc3f6";
	private final String refreshTokenSecret = "78220dc4-df36-46cd-96fd-108136fbc3f6";
	private final String appTokenExpiry = "86400000";
	private final String refreshTokenExpiry = "604800000";
}
