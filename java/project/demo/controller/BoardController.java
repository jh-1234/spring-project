package project.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.demo.model.Board;
import project.demo.service.BoardService;
import project.demo.service.ChannelService;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final ChannelService channelService;
    private final BoardService boardService;

    @GetMapping("/board")
    public String board(@RequestParam String code, Model model) {
        model.addAttribute("code", code);
        return "board";
    }

    @GetMapping("/view")
    public String view() {
        return "view";
    }

    @GetMapping("/write")
    public String write(@RequestParam String code, Model model) {
        model.addAttribute("board", new Board());
        model.addAttribute("code", code);
        return "write";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute Board board, @RequestParam String code, RedirectAttributes redirectAttributes) {
        int id = channelService.getId(code);
        board.setChannelId(id);
        redirectAttributes.addAttribute("code", code);
        boardService.write(board);

        return "redirect:/board";
    }

    @GetMapping("/modify")
    @ResponseBody
    public String modify() {
        return "modify";
    }
}
