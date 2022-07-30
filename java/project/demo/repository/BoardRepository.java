package project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
