package com.bside.idle.web.controller;

import com.bside.idle.auth.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {

        SessionMember member = (SessionMember) httpSession.getAttribute("member");

        if (member != null) {
            model.addAttribute("userName", "LoginSuccess!!");
        }

        return "index";
    }

}
