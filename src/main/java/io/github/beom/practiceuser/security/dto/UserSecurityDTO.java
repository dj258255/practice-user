package io.github.beom.practiceuser.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
public class UserSecurityDTO extends User implements OAuth2User {

    private String id;
    private String pw;
    private String email;
    private boolean del;
    private boolean social;
    private Map<String, Object> props; //소셜 로그인 정보

    public UserSecurityDTO(String username, String password, String email,
                           boolean del, boolean social,
                           Collection<? extends GrantedAuthority> authorities) {

        super(username, password, authorities);

        this.id = username;
        this.pw = password;
        this.email = email;
        this.del = del;
        this.social = social;
    }


    @Override
    public Map<String, Object> getAttributes(){
        return this.getProps();
    }

    @Override
    public String getName(){
        return this.id;
    }
}
