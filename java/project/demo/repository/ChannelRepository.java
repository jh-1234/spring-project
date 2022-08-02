package project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.model.Channel;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Integer> {
    public Channel findByCode(String code);
    public List<Channel> findByUserId(int userId);
    public List<Channel> findAll();
    public List<Channel> findByTitleContains(String title);
}
