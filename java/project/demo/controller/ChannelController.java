package project.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.demo.model.Channel;
import project.demo.service.ChannelService;

@Controller
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService service;

    @PostMapping("/createChannel")
    public String createChannel(@ModelAttribute Channel channel) {
        service.save(channel);

        return "redirect:/";
    }
}
