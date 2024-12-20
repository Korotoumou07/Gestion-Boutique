package entites;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"login"})
public class User {
    
    
    private int id;
    private String email;
    private String login;
    private String password;
    private Role role;
    private boolean active;

    public User( String email, String login, String password, Client client, Role role, boolean active) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.role = role;
        this.active = active;
    }
    
}
