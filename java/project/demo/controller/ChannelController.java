package project.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.demo.model.Channel;
import project.demo.service.ChannelService;

@Controller
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService service;

    @PostMapping("/create")
    public String create(@ModelAttribute Channel channel) {
        service.save(channel);

        return "redirect:/";
    }

    @PostMapping("/enter")
    public String enter(@RequestParam String code, RedirectAttributes redirectAttributes) {
        if (service.isChannel(code)) {
            redirectAttributes.addFlashAttribute("error", true);
            redirectAttributes.addFlashAttribute("message", "해당 채널이 존재하지 않습니다.");
            return "redirect:/";
        }

        int id = service.getId(code);
        redirectAttributes.addAttribute("code", id);

        return "redirect:/board";
    }
}
