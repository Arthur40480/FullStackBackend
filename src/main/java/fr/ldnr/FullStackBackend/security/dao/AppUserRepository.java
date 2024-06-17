package fr.ldnr.FullStackBackend.security.dao;


import fr.ldnr.FullStackBackend.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}
