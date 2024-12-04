package aikopo.ac.kr.polyboard.service.service.Interface;

import aikopo.ac.kr.polyboard.dto.PageRequestDTO;
import aikopo.ac.kr.polyboard.dto.PageResultDTO;
import aikopo.ac.kr.polyboard.dto.ReplyDTO;
import aikopo.ac.kr.polyboard.dto.ReplyRequestDTO;
import aikopo.ac.kr.polyboard.entity.Reply;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReplyService {
    void saveReply(ReplyRequestDTO replyRequestDTO);
    PageResultDTO<ReplyDTO, Object[]> getReplies(Long boardId, PageRequestDTO pageRequestDTO);
    void modifyReply(ReplyDTO replyDTO);
    void deleteReply(Long id);
}
