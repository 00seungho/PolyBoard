package aikopo.ac.kr.polyboard.controller;

import aikopo.ac.kr.polyboard.dto.UserRegDTO;
import aikopo.ac.kr.polyboard.service.service.Imp.UserServiceImp;
import aikopo.ac.kr.polyboard.service.service.Interface.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user/")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String redirectToLogin() {
        // "/" 경로로 들어오면 "/login" 페이지로 리다이렉트
        return "redirect:/error/404";
    }
    @GetMapping("/findid")
    public void findid(){
    }

    @GetMapping("/findpw")
    public void findpw(){
    }

    @GetMapping("/login")
    public void login() {
    }

    @GetMapping("/register")
    public void register(Model model){
        model.addAttribute("dtoList",userService.getMajorDtoList());
    }

    @GetMapping("/usermodify")
    public void userModify(){
    }

    @GetMapping("/userprofile")
    public void userProfile(){
    }

    @PostMapping("/register")
    public String singup(UserRegDTO dto, RedirectAttributes redirectAttributes) {
        log.info(dto.toString());
        Boolean Success = userService.saveMember(dto);
        if(Success){
            redirectAttributes.addFlashAttribute("result","회원가입이 완료되었습니다.");
            return "redirect:/user/login";
        }
        else{
            redirectAttributes.addFlashAttribute("result","회원가입에 실패했습니다. 다시 시도해주세요.");
            return "redirect:/user/singup";
        }
    }

}
