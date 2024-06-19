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
     * @return ResponseEntity contenant la liste des utilisateurs.
     */
    @GetMapping("/users")
    ResponseEntity<List<AppUser>> getUsers(){
        return this.accountService.listUsers();
    }

    /**
     * Enregistre un nouvel utilisateur.
     * @param user Utilisateur à enregistrer.
     * @return L'utilisateur enregistré.
     */
    @PostMapping("/users")
    public AppUser postUser(@RequestBody AppUser user){
        return this.accountService.saveUser(user);
    }

    /**
     * Enregistre un nouveau rôle.
     * @param role Rôle à enregistrer.
     * @return Le rôle enregistré.
     */
    @PostMapping("/role")
    public AppRole postRole(@RequestBody AppRole role){
        return this.accountService.saveRole(role);
    }

    /**
     * Associe un rôle à un utilisateur.
     * @param userRoleForm Formulaire contenant le nom de l'utilisateur et le nom du rôle.
     */
    @PostMapping("/roleUser")
    public void postRoleToUser(@RequestBody UserRoleForm userRoleForm) {
        accountService.addRoleToUser(userRoleForm.getUsername(), userRoleForm.getRolename());
    }
}

/**
 * Classe pour représenter le formulaire d'association d'un rôle à un utilisateur.
 */
@Data
class UserRoleForm {
    private String username;
    private String rolename;
}
