package com.bside.idle.jsoup;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsoupTest {

	List<String> pages = List.of("https://kcb.recruiter.co.kr/app/jobnotice/view?systemKindCode=MRS2&jobnoticeSn=86961",
		"https://www.naver.com","https://zetcode.com/java/jsoup/","https://careers.smilegate.com/apply/announce/view?seq=4385");

	@Test
	void test() {
		pages.forEach(page -> {
			try {
				Document document = Jsoup.connect(page).get();
				log.info("title={}", document.title());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}