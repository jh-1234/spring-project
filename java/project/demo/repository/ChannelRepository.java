package project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.demo.model.Channel;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Integer> {
    public Channel findByCode(String code);

    public Channel findBySecretCode(String code);

    public List<Channel> findByUserId(Long userId);

    public List<Channel> findByTitleContains(String title);

    @Query("select c.secretCode from Channel c where c.code = :code")
    public String getSecretCode(@Param("code") String code);
}
