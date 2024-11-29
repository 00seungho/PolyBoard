package aikopo.ac.kr.polyboard.controller;

import aikopo.ac.kr.polyboard.dto.UserViewDTO;
import aikopo.ac.kr.polyboard.service.service.Interface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final UserService userService;

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        UserViewDTO userViewDTO = userService.getUserViewDto();
        model.addAttribute("loginDTO", userViewDTO); // 모든 뷰에 전역적으로 loginDTO 전달
    }
}