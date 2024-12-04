package aikopo.ac.kr.polyboard.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true, nullable = false) // 고유 및 NULL 불허
    private String email;

    @Column(unique = true, nullable = false) // 고유 및 NULL 불허
    private String nickname;

    @Column(unique = true, nullable = false) // 고유 및 NULL 불허
    private String phone;

    private String password;
    private String address;

    @ManyToOne
    @JoinColumn(referencedColumnName = "ID")
    private Major major;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Enumerated(EnumType.STRING)
    private Position position;

    public String getRole(){
        return role.toString();
    }

    public void changePhone(String phone){
        this.phone = phone;
    }

    public void changeAddress(String address){
        this.address = address;
    }

    public void changeNickName(String nickname){
        this.nickname = nickname;
    }


}
