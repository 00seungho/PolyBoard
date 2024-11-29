package aikopo.ac.kr.polyboard.service.service.Interface;

import aikopo.ac.kr.polyboard.dto.MajorListDTO;
import aikopo.ac.kr.polyboard.dto.UserRegDTO;
import aikopo.ac.kr.polyboard.dto.UserViewDTO;
import aikopo.ac.kr.polyboard.entity.Major;
import aikopo.ac.kr.polyboard.entity.Member;

import java.util.List;

public interface UserService {
    // 중복 이메일 검증 로직
    Boolean equalEmailValidation(String email);
    // 중복 닉네임 검증 로직
    Boolean equalNickNameValidation(String email);


    // 회원가입 로직
    Boolean saveMember(UserRegDTO userRegDTO);
    // 비밀번호 찾기 로직

    //학과 목록 출력로직
    List<MajorListDTO> getMajorDtoList();

    //유저 로그인 공통 dto 로직
    UserViewDTO getUserViewDto();
}
