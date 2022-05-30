package ru.greatstep.spring.boot_security.models;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    private String password;

    @ManyToMany
    @JoinTable(name = "users_roles",
                joinColumns = @JoinColumn(name="user_id"),
                inverseJoinColumns = @JoinColumn(name="role_id"))
    private Collection<Role> roles;


    public User() {
    }

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public Collection<Role> getRoles() {return roles;}
    public void setRoles(Collection<Role> roles) {this.roles = roles;}

    @Override
    public String toString() {
        return "Id = " + id + "\n" + "First Name = " + firstName + "\n" + "Last Name = " + lastName
                + "\n" + "Email = " + email + "\n";
    }
}
