package fr.ldnr.FullStackBackend.web;

import fr.ldnr.FullStackBackend.security.entities.AppRole;
import fr.ldnr.FullStackBackend.security.entities.AppUser;
import fr.ldnr.FullStackBackend.security.service.AccountServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class AccountRestController {

    @Autowired
    AccountServiceImpl accountService;

    /**
     * Récupère la liste de tous les utilisateurs.
     * @return une réponse HTTP contenant la liste de tous les utilisateurs.
     */
    @GetMapping("/users")
    ResponseEntity<List<AppUser>> getUsers() {
        return this.accountService.listUsers();
    }

    /**
     * Ajoute un nouvel utilisateur.
     * @param user l'utilisateur à ajouter.
     * @return l'utilisateur ajouté.
     */
    @PostMapping("/users")
    public AppUser postUser(@RequestBody AppUser user) {
        return this.accountService.saveUser(user);
    }

    /**
     * Ajoute un nouveau rôle.
     * @param role le rôle à ajouter.
     * @return le rôle ajouté.
     */
    @PostMapping("/role")
    public AppRole postRole(@RequestBody AppRole role) {
        return this.accountService.saveRole(role);
    }

    /**
     * Associe un rôle à un utilisateur.
     * @param userRoleForm le formulaire contenant le nom d'utilisateur et le nom du rôle.
     */
    @PostMapping("/roleUser")
    public void postRoleToUser(@RequestBody UserRoleForm userRoleForm) {
        accountService.addRoleToUser(userRoleForm.getUsername(), userRoleForm.getRolename());
    }
}

/**
 * Formulaire de transfert pour associer un rôle à un utilisateur.
 */
@Data
class UserRoleForm {
    private String username;
    private String rolename;
}
