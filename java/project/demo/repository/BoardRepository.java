package project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.model.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    public List<Board> findByChannelIdOrderByIdDesc(int id);
    public Board findById(int id);
}
