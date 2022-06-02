package ru.greatstep.spring.boot_security.models;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {

    public User() {
    }

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "userName")
    private String username;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true, name = "email")
    private String email;

    @Column(name = "password")
    private String password;


    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Column(name = "Roles")
    private Collection<Role> roles;

    public String getRolesView() {
        StringBuilder sb = new StringBuilder();
        for (Role role : roles) { sb.append(role.getName());
                                  sb.append("; ");}
        return sb.toString();
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

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return mapRolesToAuthorities(roles);
    }


    @Override
    public String toString() {
        return "Id = " + id + "\n" + "First Name = " + firstName + "\n" + "Last Name = " + lastName
                + "\n" + "Email = " + email + "\n";
    }

}
