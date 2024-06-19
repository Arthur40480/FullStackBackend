package fr.ldnr.FullStackBackend.security.service;

import fr.ldnr.FullStackBackend.entities.Hotel;
import fr.ldnr.FullStackBackend.security.entities.AppRole;
import fr.ldnr.FullStackBackend.security.entities.AppUser;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    public AppUser saveUser(AppUser user);
    public AppUser saveManager(String username);
    public void deleteManager(Long id);
    public AppUser updateManager(Long id, String username);
    public Collection<AppUser> findManagerByHotel(Long id);
    public AppRole saveRole(AppRole role);
    public void addRoleToUser(String username, String rolename);
    public AppUser findUserByUsername(String username);
    public List<AppUser> findAllManagers();
    public Optional<AppUser> findUserById(Long id);
    ResponseEntity<List<AppUser>> listUsers();
    public Hotel assignHotelToManager(Long idUser, Long idHotel);
    public Hotel removeHotelFromManager(Long idUser, Long idHotel);
}
