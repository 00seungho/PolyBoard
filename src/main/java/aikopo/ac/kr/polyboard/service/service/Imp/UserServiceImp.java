package aikopo.ac.kr.polyboard.service.service.Imp;

import aikopo.ac.kr.polyboard.dto.MajorListDTO;
import aikopo.ac.kr.polyboard.dto.UserRegDTO;
import aikopo.ac.kr.polyboard.dto.UserViewDTO;
import aikopo.ac.kr.polyboard.entity.*;
import aikopo.ac.kr.polyboard.repository.MajorRepository;
import aikopo.ac.kr.polyboard.repository.MemberRepository;
import aikopo.ac.kr.polyboard.service.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MajorRepository majorRepository;

    @Override
    public Boolean equalEmailValidation(String email) {
        return memberRepository.findByEmail(email).isEmpty();
    }

    @Override
    public Boolean equalNickNameValidation(String NickName) {
        return memberRepository.findByNickname(NickName).isEmpty();
    }


    @Override
    public Boolean saveMember(UserRegDTO userRegDTO) {
        Member member = DTOToEntity(userRegDTO);
        memberRepository.save(member);
        return Boolean.TRUE;
    }

    @Override
    public List<MajorListDTO> getMajorDtoList() {
        List<Major> majorList = majorRepository.findAll();

        List<MajorListDTO> majorListDTOList = majorList.stream()
                .filter(entity -> !"공지".equals(entity.getName())) // "공지"인 항목 제외
                .map(this::entityToDTO) // entityToDTO 메서드 적용
                .collect(Collectors.toList());

        return majorListDTOList;
    }

    @Override
    public UserViewDTO getUserViewDto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증되지 않은 상태 처리
        if (authentication == null || !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {
            return UserViewDTO.builder()
                    .login(false)
                    .build();
        }

        // 인증된 사용자 정보 처리
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String userName = ((UserDetails) principal).getUsername();
            Optional<Member> memberOptional = memberRepository.findByEmail(userName);
            Member member = memberOptional.orElseThrow(() ->
                    new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userName));

            return UserViewDTO.builder()
                    .login(true)
                    .Name(member.getName())
                    .nickName(member.getNickname())
                    .Major(member.getMajor().getName())
                    .Role(member.getRole())
                    .build();
        }

        // 기본값 반환
        return UserViewDTO.builder()
                .login(false)
                .build();
}

    public MajorListDTO entityToDTO(Major major){
        MajorListDTO majorListDTO =MajorListDTO.builder()
                .id(major.getId())
                .name(major.getName())
                .build();
        return majorListDTO;
    }

    public Member DTOToEntity(UserRegDTO userRegDTO){
        String positionStr = userRegDTO.getPosition();
        Position position = Position.valueOf(positionStr);
        Role role;
        Optional<Major> majorOptional = majorRepository.findByName(userRegDTO.getMajor());
        Major major = majorOptional.orElseThrow(()->new IllegalAccessError("notfound major"));

        if(positionStr.equals("Staff")){
            role = Role.ADMIN;
        }
        else if(positionStr.equals("Professor")){
            role = Role.MANAGER;
        }
        else{
            role = Role.USER;
        }
        Member member = Member.builder()
                .role(role)
                .address(userRegDTO.getAddress())
                .phone(userRegDTO.getNumber())
                .password(passwordEncoder.encode(userRegDTO.getPassword()))
                .nickname(userRegDTO.getNickName())
                .name(userRegDTO.getName())
                .position(position)
                .major(major)
                .address(userRegDTO.getAddress())
                .email(userRegDTO.getEmail())
                .build();
        return member;
    }


}
