package db.coursework.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(unique = true)
    @NotEmpty
    @Size(min = 4, max = 20)
    private String username;

    @Column
    @NotEmpty
    @Size(min = 4)
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "human_id", referencedColumnName = "id")
    private Human human;

    public User(@NotEmpty @Size(min = 4, max = 20) String username, @NotEmpty @Size(min = 4) String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getHuman().getHumanRoles().stream().map(HumanRole::getRole).collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
