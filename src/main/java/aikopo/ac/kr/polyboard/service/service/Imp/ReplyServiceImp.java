package aikopo.ac.kr.polyboard.service.service.Imp;

import aikopo.ac.kr.polyboard.dto.*;
import aikopo.ac.kr.polyboard.entity.Board;
import aikopo.ac.kr.polyboard.entity.Member;
import aikopo.ac.kr.polyboard.entity.Reply;
import aikopo.ac.kr.polyboard.repository.BoardRepository;
import aikopo.ac.kr.polyboard.repository.MemberRepository;
import aikopo.ac.kr.polyboard.repository.ReplyRepository;
import aikopo.ac.kr.polyboard.security.handler.UserAccessDeniedHandler;
import aikopo.ac.kr.polyboard.service.service.Interface.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImp extends BaseService implements ReplyService {
    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    public void saveReply(ReplyRequestDTO replyRequestDTO) {
        // Board 조회
        Board board = boardRepository.findById(replyRequestDTO.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // Member 조회
        Member member = memberRepository.findByNickname(replyRequestDTO.getMemberWriter())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // Reply 엔티티 생성
        Reply reply = Reply.builder()
                .content(replyRequestDTO.getContent())
                .board(board)
                .member(member)
                .build();

        replyRepository.save(reply);
    }


    @Override
    public void modifyReply(ReplyDTO replyDTO) {
        Reply reply = replyRepository.findById(replyDTO.getId()).orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        if(checkAuthorize(reply.getMember().getEmail())){
            reply.changeContent(replyDTO.getContent());
            replyRepository.save(reply);
        }
    }

    @Override
    public void deleteReply(Long id) {
        Reply reply = replyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        List<String> role = getRole();
        if(checkAuthorize(reply.getMember().getEmail()) || role.contains("ROLE_ADMIN")){
            replyRepository.deleteById(id);
        }

    }


    @Override
    public PageResultDTO<ReplyDTO, Object[]> getReplies(Long boardId, PageRequestDTO pageRequestDTO) {
        Function<Object[], ReplyDTO> fn = (en -> entityToDTO((Reply) en[0]));
        Page<Object[]> result = replyRepository.findRepliesWithMemberNickname(boardId, pageRequestDTO.getPageable(Sort.by("id").descending()));
        return new PageResultDTO<>(result, fn);
    }

    private ReplyDTO entityToDTO(Reply reply) {
        ReplyDTO replyDTO = ReplyDTO.builder()
                .id(reply.getId())
                .content(reply.getContent())
                .writer(reply.getMember().getNickname())
                .boardId(reply.getBoard().getId())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
        return replyDTO;
    }

}
