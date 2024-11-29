package aikopo.ac.kr.polyboard.controller;

import aikopo.ac.kr.polyboard.dto.PageRequestDTO;
import aikopo.ac.kr.polyboard.service.service.Imp.BoardServiceImp;
import aikopo.ac.kr.polyboard.service.service.Imp.MainServiceImp;
import aikopo.ac.kr.polyboard.service.service.Interface.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;
    @GetMapping("/")
    public String redirectToLogin() {
        // "/" 경로로 들어오면 "/login" 페이지로 리다이렉트
        return "redirect:/main";
    }
    @GetMapping("/main")
    public void mainPage(Model model){
        model.addAttribute("model",mainService.getNoticeList());
    }

}
