package project.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.demo.model.Channel;
import project.demo.repository.ChannelRepository;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository repository;

    public void save(Channel channel) {
        String whether = channel.getWhether();

        if (whether.equals("public")) {
            channel.setPassword(null);
        }

        repository.save(channel);
    }

    public boolean isChannel(String channel) {
        return repository.findByTitle(channel) == null;
    }

    public int getId(String channel) {
        return repository.findByTitle(channel).getId();
    }
}
