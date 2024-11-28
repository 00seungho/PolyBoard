package aikopo.ac.kr.polyboard.controller;

import aikopo.ac.kr.polyboard.dto.BoardDTO;
import aikopo.ac.kr.polyboard.dto.MajorListDTO;
import aikopo.ac.kr.polyboard.dto.PageRequestDTO;
import aikopo.ac.kr.polyboard.service.service.Imp.BoardServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/board/")
@RequiredArgsConstructor
@Log4j2
public class BoardController {
    private final BoardServiceImp boardServiceImp;

    @GetMapping("/")
    public String redirectToLogin() {
        // "/" 경로로 들어오면 "/login" 페이지로 리다이렉트
        return "redirect:/list";
    }

    @GetMapping("/list")
    public void BoardList(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", boardServiceImp.getDepartmentList(pageRequestDTO));
    }

    @GetMapping("/hotlist")
    public String hotList(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", boardServiceImp.getHotBoard(pageRequestDTO));
        return "forward:/board/list"; // URL은 변경되지 않고, list.html 파일을 렌더링
    }

    @GetMapping( "/read")
    public void read(Long id, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        BoardDTO dto = boardServiceImp.get(id);
        model.addAttribute("dto", dto);
    }

    @GetMapping( "/modify")
    public void modify(){
    }

    @GetMapping( "/register")
    public void register(){
    }

    @GetMapping( "/majorlist")
    public void test(Model model){
        List<MajorListDTO> majorListDTOList = boardServiceImp.getMajorList();
        model.addAttribute("dtoList",majorListDTOList);

    }


}
