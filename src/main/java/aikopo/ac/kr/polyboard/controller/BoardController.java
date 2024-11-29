package aikopo.ac.kr.polyboard.controller;

import aikopo.ac.kr.polyboard.dto.BoardDTO;
import aikopo.ac.kr.polyboard.dto.MajorListDTO;
import aikopo.ac.kr.polyboard.dto.PageRequestDTO;
import aikopo.ac.kr.polyboard.entity.Board;
import aikopo.ac.kr.polyboard.service.service.Imp.BoardServiceImp;
import aikopo.ac.kr.polyboard.service.service.Interface.BoardService;
import aikopo.ac.kr.polyboard.service.service.Interface.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/board/")
@RequiredArgsConstructor
@Log4j2
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    @GetMapping("/")
    public String redirectToLogin() {
        // "/" 경로로 들어오면 "/login" 페이지로 리다이렉트
        return "redirect:/list";
    }

    @GetMapping("/list")
    public void BoardList(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", boardService.getDepartmentList(pageRequestDTO));
    }

    @GetMapping("/hotlist")
    public String hotList(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", boardService.getHotBoard(pageRequestDTO));
        return "forward:/board/list"; // URL은 변경되지 않고, list.html 파일을 렌더링
    }

    @GetMapping( "/read")
    public void read(Long id,PageRequestDTO requestDTO, Model model){
        model.addAttribute("requestDTO",requestDTO);
        BoardDTO dto = boardService.get(id);
        model.addAttribute("dto", dto);
    }



    @GetMapping( "/register")
    public void register(Model model, PageRequestDTO requestDTO){
        model.addAttribute("requestDTO",requestDTO);
        model.addAttribute("dtoList",userService.getMajorDtoList());
    }

    @PostMapping( "/register")
    public String registerPost(BoardDTO dto, @ModelAttribute PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        Long id = boardService.register(dto);
        redirectAttributes.addAttribute("id", id); // 새로 생성된 ID
        redirectAttributes.addAttribute("page", requestDTO.getPage()); // 기본값 1
        redirectAttributes.addAttribute("type", requestDTO.getType() != null ? requestDTO.getType() : ""); // 빈 문자열
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword() != null ? requestDTO.getKeyword() : ""); // 빈 문자열
        redirectAttributes.addAttribute("category", requestDTO.getCategory() != null ? requestDTO.getCategory() : ""); // 빈 문자열
        return "redirect:/board/read";
    }


    @GetMapping( "/majorlist")
    public void test(Model model){
        List<MajorListDTO> majorListDTOList = boardService.getMajorList();
        model.addAttribute("dtoList",majorListDTOList);
    }

    @PostMapping("/remove")
    public String remove(long id,@ModelAttribute PageRequestDTO requestDTO ,RedirectAttributes redirectAttributes){
        boardService.removeWithReplies(id);
        redirectAttributes.addAttribute("page", requestDTO.getPage()); // 기본값 1
        redirectAttributes.addAttribute("type", requestDTO.getType() != null ? requestDTO.getType() : ""); // 빈 문자열
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword() != null ? requestDTO.getKeyword() : ""); // 빈 문자열
        redirectAttributes.addAttribute("category", requestDTO.getCategory() != null ? requestDTO.getCategory() : ""); // 빈 문자열
        return "redirect:/board/list";
    }

    @GetMapping( "/modify")
    public void modify(Long id, PageRequestDTO requestDTO,Model model){
        log.info("★★");
        BoardDTO boardDTO = boardService.get(id);
        model.addAttribute("dto",boardDTO);
        model.addAttribute("requestDTO",requestDTO);
    }

    @PostMapping( "/modify")
    public String modifyPost(BoardDTO dto, @ModelAttribute PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        Long id = boardService.modify(dto);
        redirectAttributes.addAttribute("id", id); // 새로 생성된 ID
        redirectAttributes.addAttribute("page", requestDTO.getPage()); // 기본값 1
        redirectAttributes.addAttribute("type", requestDTO.getType() != null ? requestDTO.getType() : ""); // 빈 문자열
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword() != null ? requestDTO.getKeyword() : ""); // 빈 문자열
        redirectAttributes.addAttribute("category", requestDTO.getCategory() != null ? requestDTO.getCategory() : ""); // 빈 문자열
        return "redirect:/board/read";
    }


}
