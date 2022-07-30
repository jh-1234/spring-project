package project.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.demo.model.Board;
import project.demo.repository.BoardRepository;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository repository;

    public void write(Board board) {
        repository.save(board);
    }
}
