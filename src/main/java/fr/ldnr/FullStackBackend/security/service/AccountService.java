package fr.ldnr.FullStackBackend.security.service;


import fr.ldnr.FullStackBackend.security.entities.AppRole;
import fr.ldnr.FullStackBackend.security.entities.AppUser;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
    public AppUser saveUser(AppUser user);
    public AppRole saveRole(AppRole role);
    public void addRoleToUser(String username, String rolename);
    public AppUser findUserByUsername(String username);
    ResponseEntity<List<AppUser>> listUsers();
}
