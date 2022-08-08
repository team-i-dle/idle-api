package com.bside.idle.auth;

import java.util.Map;

import com.bside.idle.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String email;
	private String nickName;

	@Builder
	public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String email, String nickName) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.email = email;
		this.nickName = nickName;
	}

	public static OAuthAttributes of(String registrationId, String userNameAttributeName,
		Map<String, Object> attributes) {
		if ("kakao".equals(registrationId)) {
			return ofKakao("id", attributes);
		}

		return null;
	}

	private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
		Map<String, Object> attributesAccount = (Map<String, Object>)attributes.get("kakao_account");
		Map<String, Object> attributesProfile = (Map<String, Object>)attributesAccount.get("profile");

		return OAuthAttributes.builder()
			.email((String)attributesAccount.get("email").toString())
			.nickName((String)attributesProfile.get("nickname").toString())
			.attributes(attributesAccount)
			.nameAttributeKey(userNameAttributeName)
			.build();
	}

	public Member toEntity() {
		return Member.builder()
			.email(email)
			.nickName(nickName)
			.role(Role.USER)
			.build();
	}
}
