package aikopo.ac.kr.polyboard.controller.RestAPI;

import aikopo.ac.kr.polyboard.dto.PageRequestDTO;
import aikopo.ac.kr.polyboard.dto.PageResultDTO;
import aikopo.ac.kr.polyboard.dto.ReplyDTO;
import aikopo.ac.kr.polyboard.dto.ReplyRequestDTO;
import aikopo.ac.kr.polyboard.service.service.Interface.ReplyService;
import aikopo.ac.kr.polyboard.service.service.Interface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ReplyRestController {

    private final ReplyService replyService;

    @PostMapping("/addReply")
    public ResponseEntity<Map<String, String>> addReply(@RequestBody ReplyRequestDTO replyRequestDTO) {
        replyService.saveReply(replyRequestDTO); // 댓글 저장 로직 실행

        // 응답 메시지 생성
        Map<String, String> response = new HashMap<>();
        response.put("message", "댓글 등록 완료");

        // 200 OK와 함께 메시지를 반환
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getReplyList")
    public ResponseEntity<PageResultDTO<ReplyDTO, Object[]>> getReplyList(
            @RequestParam Long boardId,
            PageRequestDTO pageRequestDTO) {
        PageResultDTO<ReplyDTO, Object[]> pageResultDTO = replyService.getReplies(boardId, pageRequestDTO);
        return ResponseEntity.ok(pageResultDTO);
    }

    @PostMapping("/deleteReply")
    public ResponseEntity<Map<String, String>> deleteReply(@RequestBody ReplyDTO replyDTO){
        replyService.deleteReply(replyDTO.getId());
        Map<String, String> response = new HashMap<>();
        response.put("message", "댓글 삭제 완료");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/modifyReply")
    public ResponseEntity<Map<String, String>> modifyReply(@RequestBody ReplyDTO replyDTO){
        replyService.modifyReply(replyDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "댓글 수정 완료");

        // 200 OK와 함께 메시지를 반환
        return ResponseEntity.ok(response);
    }


}
