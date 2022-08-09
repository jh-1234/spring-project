package project.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import project.demo.model.Board;
import project.demo.service.BoardService;
import project.demo.service.ChannelService;
import project.demo.service.MainService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final MainService mainService;
    private final ChannelService channelService;
    private final BoardService boardService;
    private static final int PAGE_SIZE = 10;

    @GetMapping("/board")
    public String board(@RequestParam String code, @RequestParam int page, Model model, @PageableDefault(sort = "id", size = PAGE_SIZE, direction = Sort.Direction.DESC) Pageable pageable) {
        String nickname = getNickname();
        Long id = channelService.getId(code);
        List<Board> boards = boardService.getBoards(id, pageable);
        Long boardSize = boardService.getBoardSize(id);
        Long size =  boardSize % PAGE_SIZE == 0 ? boardSize / PAGE_SIZE - 1 : boardSize / PAGE_SIZE;

        model.addAttribute("code", code);
        model.addAttribute("nickname", nickname);
        model.addAttribute("boards", boards);
        model.addAttribute("size", size);
        model.addAttribute("page", page);
        return "board";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, @RequestParam String code, Model model) {
        String nickname = getNickname();
        Board board = boardService.getBoard(id);

        model.addAttribute("board", board);
        model.addAttribute("nickname", nickname);
        model.addAttribute("code", code);
        return "view";
    }

    @GetMapping("/write")
    public String write(@RequestParam String code, @RequestParam int page, Model model) {
        String nickname = getNickname();
        model.addAttribute("board", new Board());
        model.addAttribute("code", code);
        model.addAttribute("nickname", nickname);
        model.addAttribute("page", page);
        return "write";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute Board board, @RequestParam String code, @RequestParam int page, @RequestParam MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        Long id = channelService.getId(code);
        board.setChannelId(id);
        redirectAttributes.addAttribute("code", code);
        redirectAttributes.addAttribute("page", page);

        if (!file.isEmpty()) {
            boardService.fileSave(file, board);
        }

        boardService.write(board);

        return "redirect:/board";
    }

    @GetMapping("/modify")
    @ResponseBody
    public String modify() {
        return "modify";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) throws MalformedURLException {
        Board board = boardService.getBoard(id);
        String storeFileName = board.getStoreFileName();
        String uploadFileName = board.getUploadFileName();

        UrlResource resource = new UrlResource("file:" + boardService.getFullPath(storeFileName));

        String encode = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encode + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);
    }

    public String getNickname() {
        return mainService.getNickname();
    }
}
