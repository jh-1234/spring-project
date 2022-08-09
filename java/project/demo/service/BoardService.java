package project.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.demo.model.Board;
import project.demo.repository.BoardRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    @Value("${file.dir}")
    private String fileDir;

    private final BoardRepository repository;

    public List<Board> getBoards(Long id, Pageable pageable) {
        return repository.findByChannelIdOrderByIdDesc(id, pageable);
    }

    public Board getBoard(Long id) {
        return repository.findById(id);
    }

    public void write(Board board) {
        repository.save(board);
    }

    public void fileSave(MultipartFile file, Board board) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String newName = createFileName(originalFilename);
        file.transferTo(new File(getFullPath(newName)));
        board.setUploadFileName(originalFilename);
        board.setStoreFileName(newName);
    }

    public String createFileName(String fileName) {
        int pos = fileName.lastIndexOf(".");
        String ext = fileName.substring(pos);

        return UUID.randomUUID().toString() + ext;
    }

    public Long getBoardSize(Long id) {
        return repository.getBoardSize(id);
    }

    public String getFullPath(String filename) {
        return fileDir + filename;
    }
}
