package aikopo.ac.kr.polyboard.controller.RestAPI;

import aikopo.ac.kr.polyboard.dto.BoardDTO;
import aikopo.ac.kr.polyboard.dto.LikeDislikeDTO;
import aikopo.ac.kr.polyboard.service.service.Interface.BoardService;
import aikopo.ac.kr.polyboard.service.service.Interface.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardService boardService;
    @PostMapping("/updateLike")
    public ResponseEntity<Map<String, String>> updateLikeDislike(@RequestBody LikeDislikeDTO dto) {
        boardService.addLike(dto);
        Map<String, String> response = new HashMap<>();
        response.put("message", dto.getLikeStatus() ? "추천 완료": "비추천 완료");

        // 200 OK와 함께 메시지를 반환
        return ResponseEntity.ok(response);
    }


}
