package judgev2.demo.domain.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    private String username;
    private String password;
    private String email;
    private String git;
    private Set<Role> authorities;


    public User() {
    }
    @Column(name = "username", nullable = false, unique = true)
    public String getUsername() {
        return username;
    }
    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Transient
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    @ManyToMany(targetEntity = Role.class,fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id")
    )
    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities){
        this.authorities = authorities;
    }

    @Column(name = "password",nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Column(name = "git")
    public String getGit() {
        return git;
    }

    public void setGit(String git) {
        this.git = git;
    }



}
