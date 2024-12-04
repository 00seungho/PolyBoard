package aikopo.ac.kr.polyboard.controller.RestAPI;

import aikopo.ac.kr.polyboard.dto.UserRegDTO;
import aikopo.ac.kr.polyboard.entity.Member;
import aikopo.ac.kr.polyboard.service.service.Interface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping("/emailcheck")
    public ResponseEntity<Map<String, String>> checkEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        Map<String, String> response = new HashMap<>();

        if (email == null || email.isEmpty()) {
            response.put("message", "이메일이 비어있으면 안됩니다.");
            return ResponseEntity.badRequest().body(response);
        }

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if (!email.matches(emailRegex)) {
            response.put("message", "유효한 이메일 형식이 아닙니다.");
            return ResponseEntity.badRequest().body(response);
        }

        Boolean isAvailable = userService.equalEmailValidation(email);

        if (isAvailable) {
            response.put("message", "사용 가능한 이메일 입니다.");
            return ResponseEntity.ok(response);

        } else {
            response.put("message", "이미 등록된 이메일 입니다.");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/nicknamecheck")
    public ResponseEntity<Map<String, String>> checkNickName(@RequestBody Map<String, String> request) {
        String NickName = request.get("nickName");
        Boolean isAvailable = userService.equalNickNameValidation(NickName);
        Map<String, String> response = new HashMap<>();

        if (NickName == null || NickName.isEmpty()) {
            response.put("message", "닉네임이 비어있으면 안됩니다.");
            return ResponseEntity.badRequest().body(response);
        }

        if (NickName.length() > 10) {
            response.put("message", "닉네임은 10자를 초과할 수 없습니다.");
            return ResponseEntity.badRequest().body(response);
        }

        if (isAvailable) {
            response.put("message", "사용 가능한 닉네임입니다.");
            return ResponseEntity.ok(response);

        } else {
            response.put("message", "이미 등록된 닉네임 입니다.");
            return ResponseEntity.badRequest().body(response);
        }
    }



}
