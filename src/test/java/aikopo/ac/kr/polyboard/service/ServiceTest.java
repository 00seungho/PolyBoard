package aikopo.ac.kr.polyboard.service;

import aikopo.ac.kr.polyboard.dto.*;
import aikopo.ac.kr.polyboard.entity.Board;
import aikopo.ac.kr.polyboard.entity.LikeDislike;
import aikopo.ac.kr.polyboard.entity.Major;
import aikopo.ac.kr.polyboard.entity.Member;
import aikopo.ac.kr.polyboard.repository.BoardRepository;
import aikopo.ac.kr.polyboard.repository.MajorRepository;
import aikopo.ac.kr.polyboard.repository.MemberRepository;
import aikopo.ac.kr.polyboard.security.CustomUserDetailsService;
import aikopo.ac.kr.polyboard.service.service.Imp.BoardServiceImp;
import aikopo.ac.kr.polyboard.service.service.Imp.ReplyServiceImp;
import aikopo.ac.kr.polyboard.service.service.Interface.BoardService;
import aikopo.ac.kr.polyboard.service.service.Interface.MainService;
import aikopo.ac.kr.polyboard.service.service.Interface.ReplyService;
import aikopo.ac.kr.polyboard.service.service.Interface.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
public class ServiceTest {
    @Autowired
    BoardService boardService;
    @Autowired
    MainService mainService;
    @Autowired
    ReplyService replyService;
    @Autowired
    UserService userService;
    @Autowired
    MajorRepository majorRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final Random RANDOM = new Random();

    private static final List<String> FIRST_NAMES = Arrays.asList(
            "김", "이", "박", "최", "정", "강", "조", "윤", "장", "임",
            "한", "오", "서", "신", "권", "황", "안", "송", "전", "홍",
            "유", "고", "문", "양", "손", "배", "조", "백", "허", "유",
            "남", "심", "노", "정", "하", "곽", "성", "차", "주", "우",
            "구", "신", "임", "전", "민", "유", "류", "나", "진", "지",
            "엄", "채", "원", "천", "방", "공", "강", "현", "함", "변",
            "염", "양", "변", "여", "추", "노", "도", "소", "신", "석",
            "선", "설", "마", "길", "주", "연", "방", "위", "표", "명",
            "기", "반", "라", "왕", "금", "옥", "육", "인", "맹", "제",
            "모", "장", "남", "탁", "국", "여", "진", "어"
    );

    private static final List<String> SECOND_NAMES = Arrays.asList(
            "김", "이", "박", "최", "정", "강", "조", "윤", "장", "임",
            "한", "오", "서", "신", "권", "황", "안", "송", "전", "홍",
            "유", "고", "문", "양", "손", "배", "조", "백", "허", "유",
            "남", "심", "노", "정", "하", "곽", "성", "차", "주", "우",
            "구", "신", "임", "전", "민", "유", "류", "나", "진", "지",
            "엄", "채", "원", "천", "방", "공", "강", "현", "함", "변",
            "염", "양", "변", "여", "추", "노", "도", "소", "신", "석",
            "선", "설", "마", "길", "주", "연", "방", "위", "표", "명",
            "기", "반", "라", "왕", "금", "옥", "육", "인", "맹", "제",
            "모", "장", "남", "탁", "국", "여", "진", "어"
    );
    @Autowired
    private BoardRepository boardRepository;
    @Autowired

    public static String getRandomFirstname() {
        return FIRST_NAMES.get(RANDOM.nextInt(FIRST_NAMES.size()));
    }
    public static String getRandomSecondname() {
        List<String> tempSecondnames = new ArrayList<>(SECOND_NAMES); // 복사본 생성
        Collections.shuffle(tempSecondnames); // 랜덤 셔플

        int nameLength = RANDOM.nextDouble() < 0.2 ? 1 : 2; // 20% 확률로 2글자 이름 생성
        StringBuilder secondname = new StringBuilder();

        for (int i = 0; i < nameLength; i++) {
            secondname.append(tempSecondnames.get(i));
        }

        return secondname.toString();
    }

    public static String generateRandomName() {
        return getRandomFirstname() + getRandomSecondname();
    }

    private static char getRandomHangul() {
        int HANGUL_START = 0xAC00; // 가
        int HANGUL_END = 0xD7A3;   // 힣
        int codePoint = HANGUL_START + RANDOM.nextInt(HANGUL_END - HANGUL_START + 1);
        return (char) codePoint;
    }
    public static String generateNickname() {
        // 2~6자의 길이 결정
        int length = 2 + RANDOM.nextInt(5); // 최소 2자, 최대 6자
        StringBuilder nickname = new StringBuilder();

        // 랜덤 한글 추가
        for (int i = 0; i < length; i++) {
            nickname.append(getRandomHangul());
        }

        return nickname.toString();
    }

    @Test
    public void insertMajor(){
        Major major1 = Major.builder()
                .name("인공지능소프트웨어")
                .build();
        majorRepository.save(major1);

        Major major2 = Major.builder()
                .name("전기")
                .build();
        majorRepository.save(major2);

        Major major3 = Major.builder()
                .name("공지")
                .build();
        majorRepository.save(major3);

        Major major4 = Major.builder()
                .name("친환경산업디자인")
                .build();
        majorRepository.save(major4);

        Major major5 = Major.builder()
                .name("클라우드컴퓨팅")
                .build();
        majorRepository.save(major5);

        Major major6 = Major.builder()
                .name("기계설계")
                .build();
        majorRepository.save(major6);

        Major major7 = Major.builder()
                .name("메카트로닉스(인공지능융합로봇)")
                .build();
        majorRepository.save(major7);

        Major major8 = Major.builder()
                .name("메타버스콘텐츠")
                .build();
        majorRepository.save(major8);

        Major major9 = Major.builder()
                .name("미래형자동차")
                .build();
        majorRepository.save(major9);

        Major major10 = Major.builder()
                .name("시각디자인")
                .build();
        majorRepository.save(major10);

        Major major11 = Major.builder()
                .name("지능형에너지설비")
                .build();
        majorRepository.save(major11);
    }

    @Test
    public void addUser(){
        int userCount = 10;
        List<MajorListDTO> majors = userService.getMajorDtoList();
        List<String> majorNames = majors.stream()
                .map(major -> major.getName())
                .collect(Collectors.toList());

        IntStream.rangeClosed(0,userCount).forEach(i->{
            UserRegDTO userRegDTO = UserRegDTO.builder()
                    .address("한국폴리텍대학 서울정수캠퍼스")
                    .number("010"+"1111"+String.format("%03d", i))
                    .major("인공지능소프트웨어과")
                    .nickName("관리자"+generateNickname())
                    .name(generateRandomName())
                    .password("aaa111!!!")
                    .passwordConfirmation("aaa111!!!")
                    .email("admin"+i+"@kopo.ac.kr")
                    .position("Staff")
                    .build();
            userService.saveMember(userRegDTO);
        });

        userCount = 30;
        IntStream.rangeClosed(0,userCount).forEach(i->{
            String name = generateRandomName();
            int pick = RANDOM.nextInt(majors.size());
            UserRegDTO userRegDTO = UserRegDTO.builder()
                    .address("한국폴리텍대학 서울정수캠퍼스")
                    .number("010"+"2222"+String.format("%03d", i))
                    .major(majorNames.get(pick))
                    .nickName(majorNames.get(pick)+name+"교수")
                    .name(generateRandomName())
                    .password("aaa111!!!")
                    .passwordConfirmation("aaa111!!!")
                    .email("manager"+i+"@kopo.ac.kr")
                    .position("Professor")
                    .build();
            userService.saveMember(userRegDTO);
        });

        userCount = 200;
        IntStream.rangeClosed(0,userCount).forEach(i->{
            int pick = RANDOM.nextInt(majors.size());
            UserRegDTO userRegDTO = UserRegDTO.builder()
                    .address("한국폴리텍대학 서울정수캠퍼스")
                    .number("010"+"3333"+String.format("%03d", i))
                    .major(majorNames.get(pick))
                    .nickName(generateNickname())
                    .name(generateRandomName())
                    .password("aaa111!!!")
                    .passwordConfirmation("aaa111!!!")
                    .email("user"+i+"@kopo.ac.kr")
                    .position("Student")
                    .build();
            userService.saveMember(userRegDTO);
        });


    }
    @Test
    public void insertBoard(){
        List<Member> members = memberRepository.findAll();

        List<Member> students = members.stream()
                .filter(member -> "Student".equals(member.getPosition().name()))
                .collect(Collectors.toList());

        List<Member> professors = members.stream()
                .filter(member -> "Professor".equals(member.getPosition().name()))
                .collect(Collectors.toList());

        List<Member> staffs = members.stream()
                .filter(member -> "Staff".equals(member.getPosition().name()))
                .collect(Collectors.toList());

        List<UsernamePasswordAuthenticationToken> studentAuthentications = new ArrayList<>();
        List<UsernamePasswordAuthenticationToken> professorAuthentications = new ArrayList<>();
        List<UsernamePasswordAuthenticationToken> staffAuthentications = new ArrayList<>();
        students.forEach(student->{
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(student.getEmail());
            studentAuthentications.add(new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities()));
        });
        professors.forEach(professor->{
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(professor.getEmail());
            professorAuthentications.add(new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities()));
        });
        staffs.forEach(staff->{
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(staff.getEmail());
            staffAuthentications.add(new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities()));
        });
        IntStream.rangeClosed(0,5).forEach(i->{
            int pick = RANDOM.nextInt(staffs.size());
            UsernamePasswordAuthenticationToken authentication = staffAuthentications.get(pick);
            Member member = staffs.get(pick);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            BoardDTO boardDTO = BoardDTO.builder()
                    .departmentNotice(Boolean.FALSE)
                    .allNotice(Boolean.TRUE)
                    .writerMajor(member.getMajor().getName())
                    .writerEmail(member.getEmail())
                    .writerName(member.getName())
                    .boardMajor("공지")
                    .title("공지"+i)
                    .content("공지입니다. 내용은 다음과 같습니다. "+generateNickname()+generateNickname())
                    .likes(0)
                    .disLikes(0)
                    .build();
            boardService.register(boardDTO);
        });

        IntStream.rangeClosed(0,20).forEach(i->{
            int pick = RANDOM.nextInt(professors.size());
            UsernamePasswordAuthenticationToken authentication = professorAuthentications.get(pick);
            Member member = professors.get(pick);
            String major = member.getMajor().getName();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            BoardDTO boardDTO = BoardDTO.builder()
                    .departmentNotice(Boolean.TRUE)
                    .allNotice(Boolean.FALSE)
                    .writerMajor(member.getMajor().getName())
                    .writerEmail(member.getEmail())
                    .writerName(member.getName())
                    .boardMajor(major)
                    .title("학과 공지"+i)
                    .content("학과 공지입니다. 내용은 다음과 같습니다. "+generateNickname()+generateNickname())
                    .likes(0)
                    .disLikes(0)
                    .build();
            boardService.register(boardDTO);
        });

        List<MajorListDTO> majors = userService.getMajorDtoList();
        List<String> majorNames = majors.stream()
                .map(major -> major.getName())
                .collect(Collectors.toList());
        IntStream.rangeClosed(0,1000).forEach(i->{


            int pick = RANDOM.nextInt(students.size());
            int majorPick = RANDOM.nextInt(majors.size());
            UsernamePasswordAuthenticationToken authentication = studentAuthentications.get(pick);
            Member member = students.get(pick);
            String major = RANDOM.nextBoolean()? majorNames.get(majorPick) : member.getMajor().getName();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            BoardDTO boardDTO = BoardDTO.builder()
                    .departmentNotice(Boolean.FALSE)
                    .allNotice(Boolean.FALSE)
                    .writerMajor(member.getMajor().getName())
                    .writerEmail(member.getEmail())
                    .writerName(member.getName())
                    .boardMajor(major)
                    .title("와 학과 게시판이다~"+i)
                    .content("학과 게시판이다~"+generateNickname()+generateNickname())
                    .likes(0)
                    .disLikes(0)
                    .build();
            boardService.register(boardDTO);
        });
    }

    @Test
    public void insertReply(){
        List<Member> members = memberRepository.findAll();
        List<Board> boards = boardRepository.findAll();
        List<Member> memberList = members.stream()
                .filter(member -> "Student".equals(member.getPosition().name()) || "Professor".equals(member.getPosition().name()))
                .collect(Collectors.toList());

        List<UsernamePasswordAuthenticationToken> memberAuthentication = new ArrayList<>();

        memberList.forEach(member->{
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(member.getEmail());
            memberAuthentication.add(new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities()));
        });
        IntStream.rangeClosed(0,7000).forEach(i->{
            int boardPick = RANDOM.nextInt(boards.size());
            int memberPick = RANDOM.nextInt(memberList.size());
            Boolean like = RANDOM.nextBoolean();
            Member member = memberList.get(memberPick);
            Board board = boards.get(boardPick);
            UsernamePasswordAuthenticationToken authentication = memberAuthentication.get(memberPick);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            ReplyRequestDTO replyRequestDTO = ReplyRequestDTO.builder()
                    .boardId(board.getId())
                    .memberWriter(member.getNickname())
                    .content(like? "추천합니다~":"비추천합니다~")
                    .build();
            replyService.saveReply(replyRequestDTO);
            LikeDislikeDTO likeDislikeDTO = LikeDislikeDTO.builder()
                    .boardId(board.getId())
                    .likeStatus(like)
                    .memberId(member.getId())
                    .build();
            boardService.addLike(likeDislikeDTO);
        });
    }
}
