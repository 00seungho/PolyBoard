package aikopo.ac.kr.polyboard.dto;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
