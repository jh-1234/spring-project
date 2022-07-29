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
        repository.save(channel);
    }
}
