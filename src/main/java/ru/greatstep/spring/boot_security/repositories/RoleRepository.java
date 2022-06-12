package ru.greatstep.spring.boot_security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.greatstep.spring.boot_security.models.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRolesById(Long id);
}
