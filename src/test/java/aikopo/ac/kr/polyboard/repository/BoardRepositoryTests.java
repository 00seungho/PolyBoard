package aikopo.ac.kr.polyboard.repository;

import aikopo.ac.kr.polyboard.entity.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MajorRepository majorRepository;
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableResolver;


    @Test
    public void inserMembers(){
        Optional<Major> optionalAI = majorRepository.findByName("인공지능소프트웨어과");
        Optional<Major> optionalElectricity = majorRepository.findByName("전기과");
        Major Ai = optionalAI.orElseThrow(() -> new IllegalArgumentException("Major not found with BoardMajor"));
        Major electricity = optionalElectricity.orElseThrow(() -> new IllegalArgumentException("Major not found with BoardMajor"));

        IntStream.rangeClosed(1,20).forEach(i -> {
            Member member= Member.builder()
                    .email("user"+i+"@kopo.ac.kr")
                    .nickname("usernick"+i)
                    .major(Ai)
                    .name("user"+i)
                    .address("서울시 보광동")
                    .password("1234")
                    .phone("010-0101-01"+i)
                    .position(Position.Student)
                    .role(Role.USER)
                    .build();
            memberRepository.save(member);

    });
        IntStream.rangeClosed(40,60).forEach(j -> {
            Member member2= Member.builder()
                    .email("user"+j+"@kopo.ac.kr")
                    .nickname("usernick"+j)
                    .major(electricity)
                    .name("user"+j)
                    .address("서울시 보광동")
                    .password("1234")
                    .phone("010-0101-01"+j)
                    .position(Position.Student)
                    .role(Role.USER)
                    .build();
            memberRepository.save(member2);
        });

        Member member2= Member.builder()
                .email("professor1@kopo.ac.kr")
                .nickname("강병준교수")
                .major(Ai)
                .name("강병준")
                .address("서울시 보광동")
                .password("1234")
                .phone("010-1234-1234")
                .position(Position.Professor)
                .role(Role.MANAGER)
                .build();
        memberRepository.save(member2);

        Member member3= Member.builder()
                .email("professor2@kopo.ac.kr")
                .nickname("이영주학과장")
                .major(Ai)
                .name("이영주")
                .address("서울시 보광동")
                .password("1234")
                .phone("010-1010-0000")
                .position(Position.Professor)
                .role(Role.ADMIN)
                .build();
        memberRepository.save(member3);
    }

    @Test
    public void insertBoard(){
        Optional<Member> optionalYoungJu = memberRepository.findByEmail("professor2@kopo.ac.kr");
        Member YoungJu = optionalYoungJu.orElseThrow(() -> new IllegalArgumentException("Member not found with Member"));

        Optional<Member> optionalKang = memberRepository.findByEmail("user1@kopo.ac.kr");
        Member Kang = optionalKang.orElseThrow(() -> new IllegalArgumentException("Member not found with Member"));

        Optional<Member> optionalUser1 = memberRepository.findByEmail("user55@kopo.ac.kr");
        Member User1 = optionalUser1.orElseThrow(() -> new IllegalArgumentException("Member not found with Member"));

        IntStream.rangeClosed(1,20).forEach(i -> {
            Board board = Board.builder()
                    .departmentNotice(Boolean.FALSE)
                    .allNotice(Boolean.TRUE)
                    .title("전체 공지사항 테스트입니다."+i)
                    .content("전체 공지사항 테스트입니다.")
                    .likes(0)
                    .disLikes(0)
                    .member(YoungJu)
                    .major(YoungJu.getMajor())
                    .build();
            boardRepository.save(board);
        });

        IntStream.rangeClosed(1,20).forEach(i -> {
            Board board = Board.builder()
                    .departmentNotice(Boolean.TRUE)
                    .allNotice(Boolean.FALSE)
                    .title("학과 공지사항 테스트입니다."+i)
                    .content("학과 공지사항 테스트입니다.")
                    .likes(0)
                    .disLikes(0)
                    .member(Kang)
                    .major(Kang.getMajor())
                    .build();
            boardRepository.save(board);

        });


        IntStream.rangeClosed(1,20).forEach(i -> {
            Board board = Board.builder()
                    .departmentNotice(Boolean.FALSE)
                    .allNotice(Boolean.FALSE)
                    .title("게시판 테스트입니다."+i)
                    .content("학과 게시판 테스트입니다.")
                    .likes(0)
                    .disLikes(0)
                    .member(User1)
                    .major(User1.getMajor())
                    .build();
            boardRepository.save(board);

        });

    }
}
