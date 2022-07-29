package project.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.demo.model.Channel;
import project.demo.model.SaveUser;
import project.demo.model.User;
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
        model.addAttribute("user", new SaveUser());
        return "join";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute("user") SaveUser saveUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "join";
        }

        if (service.isUsername(saveUser.getUsername())) {
            bindingResult.reject("overlap_username", "아이디가 중복되었습니다.");
            return "join";
        }

        if (service.isNickname(saveUser.getNickname())) {
            bindingResult.reject("overlap_nickname", "닉네임이 중복되었습니다.");
            return "join";
        }

        User user = User.builder()
                .username(saveUser.getUsername())
                .password(saveUser.getPassword())
                .nickname(saveUser.getNickname())
                .build();

        service.join(user);
        return "redirect:/";
    }

    @GetMapping("/loginForm")
    public String loginForm(@RequestParam(required = false) String error, @RequestParam(required = false) String exception, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
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

    @RequestMapping("/securedTest")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String securedTest() {
        return "securedEnabled 테스트 페이지입니다.";
    }
}
