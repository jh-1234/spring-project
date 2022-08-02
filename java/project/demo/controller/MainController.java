package project.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.demo.model.Channel;
import project.demo.model.SaveUser;
import project.demo.model.User;
import project.demo.service.ChannelService;
import project.demo.service.MainService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;
    private final ChannelService channelService;

    @RequestMapping("/")
    public String index(Model model) {
        String nickname = mainService.getNickname();
        List<Channel> channels = channelService.getAllChannels();

        if (nickname != null) {
            model.addAttribute("nickname", nickname);
        }

        model.addAttribute("channel", new Channel());
        model.addAttribute("channels", channels);

        return "index";
    }

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("user", new SaveUser());
        return "join";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute("user") SaveUser saveUser, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "join";
        }

        if (mainService.isUsername(saveUser.getUsername())) {
            bindingResult.reject("overlap_username", "아이디가 중복되었습니다.");
            return "join";
        }

        if (mainService.isNickname(saveUser.getNickname())) {
            bindingResult.reject("overlap_nickname", "닉네임이 중복되었습니다.");
            return "join";
        }

        User user = User.builder()
                .username(saveUser.getUsername())
                .password(saveUser.getPassword())
                .nickname(saveUser.getNickname())
                .build();

        mainService.join(user);

        redirectAttributes.addFlashAttribute("status", true);
        redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다.");

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
        List<Channel> channel = channelService.getChannel(mainService.getUserId());

        StringBuilder sb = new StringBuilder();
        sb.append("내가 생성한 채널 목록<br>");

        for (Channel c : channel) {
            sb.append("채널명 : ").append(c.getTitle()).append(" >> ").append("채널코드 : ").append(c.getCode()).append("<br>");
        }

        return sb.toString();
    }

    @GetMapping("/search")
    @ResponseBody
    public String search(@RequestParam String value) {
        List<Channel> list = channelService.getChannels(value);

        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"result_element\">");

        for (Channel c : list) {
            sb.append("<p>").append(c.getTitle()).append("</p>");
            sb.append("<a>").append("참여하기").append("</a>");
        }
        sb.append("</div>");

        return sb.toString();
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
