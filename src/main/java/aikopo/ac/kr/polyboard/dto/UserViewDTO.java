package aikopo.ac.kr.polyboard.dto;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserViewDTO {
    private Boolean login;
    private String nickName;
    private String Name;
    private String Major;
    private String Role;
}
