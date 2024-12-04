package aikopo.ac.kr.polyboard.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRegDTO {
    String email;
    String password;
    String passwordConfirmation;
    String number;
    String nickName;
    String name;
    String position;
    String major;
    String address;
}
