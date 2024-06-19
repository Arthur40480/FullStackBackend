package fr.ldnr.FullStackBackend.security.dao;


import fr.ldnr.FullStackBackend.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
    @Query("SELECT u FROM AppUser u JOIN u.roles r WHERE r.rolename = 'HOTEL_MANAGER'")
    List<AppUser> findHotelManagers();
}
