package aikopo.ac.kr.polyboard.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public class UserLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws java.io.IOException {
        // 현재 페이지 URL로 리다이렉트
        String refererUrl = request.getHeader("Referer");
        response.sendRedirect(refererUrl != null ? refererUrl : "/");
    }
}
