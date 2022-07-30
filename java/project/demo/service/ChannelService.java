package project.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.demo.model.Channel;
import project.demo.repository.ChannelRepository;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository repository;

    public void save(Channel channel) {
        String whether = channel.getWhether();

        if (whether.equals("public")) {
            channel.setPassword(null);
        }

        channel.setCode(createCode());

        repository.save(channel);
    }

    public boolean isChannel(String code) {
        return repository.findByCode(code) == null;
    }

    public int getId(String code) {
        return repository.findByCode(code).getId();
    }

    public String createCode() {
        return new Random()
                .ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(15)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
