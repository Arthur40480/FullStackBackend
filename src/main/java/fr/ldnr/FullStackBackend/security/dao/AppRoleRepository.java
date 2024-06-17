package fr.ldnr.FullStackBackend.security.dao;

import fr.ldnr.FullStackBackend.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByRolename(String rolename);
}
