package com.bside.idle.auth;

import com.bside.idle.entity.DefaultCriteria;
import com.bside.idle.entity.Member;
import com.bside.idle.entity.MemberCriteria;
import com.bside.idle.member.repository.MemberRepository;
import com.bside.idle.notice.repository.MemberCriteriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomOAuth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    private final MemberCriteriaRepository memberCriteriaRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                                                    .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member member = saveOrUpdate(attributes);

        Member memberByIdWithCriteria = memberRepository
                .findMemberByIdWithCriteria(member.getId())
                .orElse(null);

        if (memberByIdWithCriteria == null) {
            List<DefaultCriteria> defaultCriteriaList = memberRepository.findDefaultCriteria();

            for (int i = 0; i < defaultCriteriaList.size(); i++) {
                DefaultCriteria item = defaultCriteriaList.get(i);

                MemberCriteria memberCriteria = new MemberCriteria();
                memberCriteria.setCriteriaName(item.getCriteriaName());
                memberCriteria.setWeight((long) (i + 1));
                memberCriteria.setMember(member);

                memberCriteriaRepository.save(memberCriteria);
            }
        }

        httpSession.setAttribute("member", new SessionMember(member));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
                                                                                                attributes.getAttributes(),
                                                                                                attributes.getNameAttributeKey());
    }

    private Member saveOrUpdate(OAuthAttributes attributes) {
        Member member = memberRepository.findByEmail(attributes.getEmail())
                .orElse(attributes.toEntity());


        return memberRepository.save(member);
    }
}
