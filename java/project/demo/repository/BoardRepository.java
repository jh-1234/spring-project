package project.demo.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.demo.model.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    public List<Board> findByChannelIdOrderByIdDesc(Long id, Pageable pageable);

    public Board findById(Long id);

    @Query("select count(b.id) from Board b where b.channelId = :id")
    public Long getBoardSize(@Param("id") Long id);
}
