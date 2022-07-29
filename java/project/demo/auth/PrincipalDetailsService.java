package project.demo.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.demo.model.User;
import project.demo.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User member = repository.findByUsername(username);

        if (member != null) {
            return new PrincipalDetails(member);
        }

        throw new UsernameNotFoundException(username + " is not exist");
    }
}
