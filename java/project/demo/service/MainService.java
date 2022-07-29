package project.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.demo.auth.PrincipalDetails;
import project.demo.model.User;
import project.demo.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MainService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(User member) {
        String rawPassword = member.getPassword();
        String encodePassword = bCryptPasswordEncoder.encode(rawPassword);

        member.setPassword(encodePassword);
        member.setRole("ROLE_USER");
        repository.save(member);
    }

    public boolean isUsername(String username) {
        User result = repository.findByUsername(username);

        return result != null;
    }

    public boolean isNickname(String nickname) {
        User result = repository.findByNickname(nickname);

        return result != null;
    }

    public String getNickname() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!principal.toString().equals("anonymousUser")) {
            PrincipalDetails user = (PrincipalDetails) principal;

            return user.getNickname();
        }

        return null;
    }
}
