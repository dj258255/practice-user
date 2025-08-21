package io.github.beom.practiceuser.user.domain;


import io.github.beom.practiceuser.base.BaseEntity;
import lombok.*;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "roleSet")
public class User extends CompleteBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // auto_increment

    @Column(nullable = false, unique = true)
    private String email;  // 모든 사용자의 로그인 ID

    private String pw;  // 일반 사용자만 사용, 소셜 사용자는 null

    private String name;  // 사용자 이름

    private String profileImage;  // 프로필 이미지 URL

    private boolean del;

    private Set<UserRole> roleSet = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserOAuth userOAuth;

    public void changePw(String pw){
        this.pw = pw;
    }

    public void changeEmail(String email){this.email = email;}

    public void changeDel(boolean del){this.del = del;}

    public void addRole(UserRole role){this.roleSet.add(role);}

    public void clearRole(){this.roleSet.clear();}


}
