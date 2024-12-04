package aikopo.ac.kr.polyboard.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserViewDTO {
    private Boolean login;
    private String nickName;

    private String Name;
    private String Major;
    private String Role;
}
