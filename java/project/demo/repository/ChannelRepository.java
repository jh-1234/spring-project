package project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.model.Channel;

public interface ChannelRepository extends JpaRepository<Channel, Integer> {
    public Channel findByCode(String code);
}
