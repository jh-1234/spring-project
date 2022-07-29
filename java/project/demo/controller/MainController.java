package project.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.demo.model.Channel;
import project.demo.model.Member;
import project.demo.service.MainService;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService service;

    @RequestMapping("/")
    public String index(Model model) {
        String nickname = service.getNickname();

        if (nickname != null) {
            model.addAttribute("nickname", nickname);
        }

        model.addAttribute("channel", new Channel());

        return "index";
    }

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("member", new Member());
        return "join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute Member member) {
        service.join(member);
        return "redirect:/";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/user/info")
    @ResponseBody
    public String info() {
        return "내정보 페이지입니다.";
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String admin() {
        return "관리자 페이지입니다.";
    }
}
