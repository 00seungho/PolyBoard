package aikopo.ac.kr.polyboard.security;

import aikopo.ac.kr.polyboard.entity.Board;
import aikopo.ac.kr.polyboard.entity.Member;
import aikopo.ac.kr.polyboard.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        log.info("입력받은 값: "+userEmail);
        Optional<Member> memberOptional = memberRepository.findByEmail(userEmail);
        Member member = memberOptional.orElseThrow(() -> new IllegalArgumentException("id not found: " + userEmail));
        UserDetails user = User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole()) // 역할 지정
                .build();
        log.info("유저 정보: "+ user.toString());
        return user;
    }
}
