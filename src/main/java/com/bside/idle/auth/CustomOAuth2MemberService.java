package com.bside.idle.auth;

import java.util.Collections;

import javax.servlet.http.HttpSession;

// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
// import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
// import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
// import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
// import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
// import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.bside.idle.entity.Member;
import com.bside.idle.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2MemberService /*implements OAuth2UserService*/ {
	/*private final MemberRepository memberRepository;
	private final HttpSession httpSession;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		// OAuth2User oAuth2User = delegate.loadUser(userRequest);
		//
		// String registrationId = userRequest.getClientRegistration().getRegistrationId();
		// String userNameAttributeName = userRequest.getClientRegistration()
		// 	.getProviderDetails()
		// 	.getUserInfoEndpoint()
		// 	.getUserNameAttributeName();
		//
		// OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
		// 	oAuth2User.getAttributes());

		// Member member = saveOrUpdate(attributes);

		// httpSession.setAttribute("member", new SessionMember(member));

		// return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
		// 	attributes.getAttributes(),
		// 	attributes.getNameAttributeKey());

		return null;
	}

	// private Member saveOrUpdate(OAuthAttributes attributes) {
	// 	Member member = memberRepository.findByEmail(attributes.getEmail())
	// 		.map(entity -> entity.update(attributes.getNickName()))
	// 		.orElse(attributes.toEntity());
	//
	// 	return memberRepository.save(member);
	// }*/
}
