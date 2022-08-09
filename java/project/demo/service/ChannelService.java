package project.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.demo.model.Channel;
import project.demo.repository.ChannelRepository;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void save(Channel channel) {
        String whether = channel.getWhether();

        if (whether.equals("public")) {
            channel.setPassword(null);
        }

        String code = createCode();
        String secretCode = passwordEncoder.encode(code);

        channel.setCode(code);
        channel.setSecretCode(secretCode);

        repository.save(channel);
    }

    public List<Channel> getChannel(Long userId) {
        return repository.findByUserId(userId);
    }

    public List<Channel> getAllChannels() {
        return repository.findAll();
    }

    public List<Channel> getChannels(String title) {
        return repository.findByTitleContains(title);
    }

    public boolean isChannel(String code) {
        return repository.findByCode(code) == null;
    }

    public String getSecretCode(String code) {
        return repository.getSecretCode(code);
    }

    public Long getId(String code) {
        return repository.findBySecretCode(code).getId();
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
