package com.place4code.reddit.model;

import com.place4code.reddit.validator.PasswordMatch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Getter
@Setter
@NoArgsConstructor
@PasswordMatch
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 8, max = 32)
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(min = 8, max = 128)
    private String password;

    @Transient
    @NotEmpty(message = "Please enter password confirmation")
    private String confirmPassword;

    @NotNull
    @Column(nullable = false)
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Role> roles = new HashSet<>();

    @NotNull
    @NotEmpty(message = "Please enter login.")
    @Column(nullable = false, unique = true)
    private String login;

    @NotNull
    @Column(nullable = false)
    private boolean avatar;

    private String activationCode;

    public User(String email, String password, boolean enabled, String login, boolean avatar) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.login = login;
        this.avatar = avatar;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName()))
                             .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
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

    public void addRole(Role role) {
        roles.add(role);
    }

    public void addRoles(Set<Role> roles) {
        roles.forEach(this::addRole);
    }

}
