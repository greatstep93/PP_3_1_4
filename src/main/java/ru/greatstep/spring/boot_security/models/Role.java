package ru.greatstep.spring.boot_security.models;

import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Data
@Table(name="roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "roles_name")
    private String name;

    public Role(String name) {
        this.name = name;
    }

    public Role() {
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
