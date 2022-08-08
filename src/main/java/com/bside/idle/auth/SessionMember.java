package com.bside.idle.auth;

import com.bside.idle.entity.Member;
import lombok.Getter;

@Getter
public class SessionMember {
    private String email;
    private String nickName;

    public SessionMember(Member member) {
        this.email = member.getEmail();
        this.nickName = member.getNickName();
    }
}
