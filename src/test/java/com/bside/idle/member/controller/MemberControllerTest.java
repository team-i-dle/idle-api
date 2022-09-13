package com.bside.idle.member.controller;

import com.bside.idle.auth.Role;
import com.bside.idle.entity.Member;
import com.bside.idle.member.repository.MemberRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


@RunWith(SpringRunner.class)
@SpringBootTest
class MemberControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @After
    public void cleanUp() {
        memberRepository.deleteAll();
    }

    @Test
    public void findMemberId() {
        String nickName = "이주영";
        String email = "jophn808@naver.com";

        memberRepository.save(Member.builder()
                                    .nickName(nickName)
                                    .email(email)
                                    .role(Role.USER)
                                    .build());

        List<Member> memberList = memberRepository.findAll();

        Member findMember = memberList.get(0);

        assertThat(findMember.getNickName()).isEqualTo(nickName);
        assertThat(findMember.getEmail()).isEqualTo(email);
    }
}