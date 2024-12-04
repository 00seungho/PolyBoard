package aikopo.ac.kr.polyboard.security.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        // 404 에러 페이지로 리다이렉트
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404"; // 404 페이지 템플릿 이름
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle404(NoHandlerFoundException ex) {
        return "error/404"; // 404 페이지 템플릿 이름
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handle403(AccessDeniedException ex) { return "error/403"; }
}
