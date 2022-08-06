package com.bside.idle.notice.dto;

import java.util.List;

import org.hibernate.Criteria;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class NoticeRegisterDto {

	private Long memberId;
	private String title;
	private String url;
	private List<CriteriaDto> criteria;

}
