package project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.model.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    public Member findByUsername(String username);
}
