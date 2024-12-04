package aikopo.ac.kr.polyboard.service.service.Imp;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class BaseService {

    public Boolean checkAuthorize(String email) {
        // 현재 인증 객체를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("인증되지 않은 사용자입니다.");
        }

        Object principal = authentication.getPrincipal();

        // principal이 UserDetails 타입인지 확인합니다.
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;

            return userDetails.getUsername().equals(email);
        }
        return false;
    }

    public List<String> getRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("인증되지 않은 사용자입니다.");
        }

        Object principal = authentication.getPrincipal();

        // principal이 UserDetails 타입인지 확인합니다.
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            return userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()); // 모든 권한을 String 리스트로 반환
    }
        else{
        return null;
        }
    }
}

