package aikopo.ac.kr.polyboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error/")
public class ErrorController {
    @GetMapping("/404")
    public void notFound() {
    }

    @GetMapping("/403")
    public void forbidden() {
    }
}
