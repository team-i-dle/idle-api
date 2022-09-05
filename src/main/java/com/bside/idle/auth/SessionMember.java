package com.bside.idle.auth;

import com.bside.idle.entity.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private String email;
    private String nickName;

    public SessionMember(Member member) {
        this.email = member.getEmail();
        this.nickName = member.getNickName();
    }
}
