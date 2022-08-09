package project.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.demo.model.Channel;
import project.demo.service.ChannelService;
import project.demo.service.MainService;

@Controller
@RequiredArgsConstructor
public class ChannelController {

    private final MainService mainService;
    private final ChannelService channelService;

    @PostMapping("/create")
    public String create(@ModelAttribute Channel channel) {
        Long userId = mainService.getUserId();
        channel.setUserId(userId);
        channelService.save(channel);

        return "redirect:/";
    }

    @PostMapping("/enter")
    public String enter(@RequestParam String code, RedirectAttributes redirectAttributes) {
        if (channelService.isChannel(code)) {
            redirectAttributes.addFlashAttribute("status", true);
            redirectAttributes.addFlashAttribute("message", "해당 채널이 존재하지 않습니다.");
            return "redirect:/";
        }

        String secretCode = channelService.getSecretCode(code);

        redirectAttributes.addAttribute("code", secretCode);
        redirectAttributes.addAttribute("page", 0);

        return "redirect:/board";
    }
}
