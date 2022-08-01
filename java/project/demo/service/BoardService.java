package project.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.demo.model.Board;
import project.demo.repository.BoardRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository repository;

    public List<Board> getBoard(int id) {
        return repository.findByChannelIdOrderByIdDesc(id);
    }

    public void write(Board board) {
        repository.save(board);
    }
}
