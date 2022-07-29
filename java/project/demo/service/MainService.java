package project.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.demo.auth.PrincipalDetails;
import project.demo.model.Member;
import project.demo.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MainService {

    private final MemberRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(Member member) {
        String rawPassword = member.getPassword();
        String encodePassword = bCryptPasswordEncoder.encode(rawPassword);

        member.setPassword(encodePassword);
        member.setRole("ROLE_USER");
        repository.save(member);
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
