package aikopo.ac.kr.polyboard.controller.RestAPI;

import aikopo.ac.kr.polyboard.dto.BoardDTO;
import aikopo.ac.kr.polyboard.service.service.Interface.BoardService;
import aikopo.ac.kr.polyboard.service.service.Interface.MainService;
import aikopo.ac.kr.polyboard.service.service.Interface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainRestController {

    private final MainService mainService;

    @GetMapping("/getNoticeList")
    public List<BoardDTO> getNoticeList() {
        List<BoardDTO> noticeList = mainService.getNoticeList();
        return noticeList;
    }

    @GetMapping("/getHotBoardList")
    public List<BoardDTO> getHotBoardList() {
        List<BoardDTO> hotBoardList = mainService.getHotBoardList();
        return hotBoardList;
    }

    @PostMapping("/MyBoardList")
    public List<BoardDTO> MyBoardList(@RequestBody Map<String, String> request) {
        String Major = request.get("major");
        List<BoardDTO> myBoardList = mainService.getMyBoardList(Major);
        return myBoardList;
    }

}
