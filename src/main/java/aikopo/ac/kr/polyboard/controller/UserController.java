package aikopo.ac.kr.polyboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class UserController {
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
    public void login(){
    }

    @GetMapping("/register")
    public void register(){
    }

    @GetMapping("/usermodify")
    public void userModify(){
    }

    @GetMapping("/userprofile")
    public void userProfile(){
    }



}
