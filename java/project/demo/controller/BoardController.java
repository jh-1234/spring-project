package project.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.demo.model.Board;
import project.demo.service.BoardService;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;

    @GetMapping("/board")
    public String board(@RequestParam int code, Model model) {
        model.addAttribute("code", code);
        return "board";
    }

    @GetMapping("/view")
    public String view() {
        return "view";
    }

    @GetMapping("/write")
    public String write(@RequestParam int code, Model model) {
        model.addAttribute("board", new Board());
        model.addAttribute("code", code);
        return "write";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute Board board, @RequestParam int code, RedirectAttributes redirectAttributes) {
        board.setChannelId(code);
        redirectAttributes.addAttribute("code", code);
        service.write(board);

        return "redirect:/board";
    }

    @GetMapping("/modify")
    @ResponseBody
    public String modify() {
        return "modify";
    }
}
