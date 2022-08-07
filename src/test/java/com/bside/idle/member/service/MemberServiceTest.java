package com.bside.idle.member.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.bside.idle.entity.MemberCriteria;
import com.bside.idle.member.repository.MemberRepository;

@SpringBootTest
class MemberServiceTest {

	@Mock
	MemberRepository repository;

	@InjectMocks
	MemberService service;

	MemberCriteria memberCriteria;

	@BeforeEach
	void setUp() {
	}

	@Test
	@DisplayName("ansdjkansjdk")
	void test() {

	}

}