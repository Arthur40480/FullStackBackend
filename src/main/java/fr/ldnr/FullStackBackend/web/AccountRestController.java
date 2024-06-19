package fr.ldnr.FullStackBackend.web;

import fr.ldnr.FullStackBackend.entities.Hotel;
import fr.ldnr.FullStackBackend.security.entities.AppRole;
import fr.ldnr.FullStackBackend.security.entities.AppUser;
import fr.ldnr.FullStackBackend.security.service.AccountServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/username")
    public AppUser getUserByUsername(@RequestParam String username) {
        return accountService.findUserByUsername(username);
    }

    /**
     * Récupère un utilisateur par son identifiant.
     * @param id L'identifiant de l'utilisateur à récupérer.
     * @return Une {@link Optional} contenant l'utilisateur trouvé, ou {@link Optional#empty()} si aucun utilisateur correspondant n'est trouvé.
     */
    @GetMapping("/user/{id}")
    Optional<AppUser> getUserById(@PathVariable("id") Long id){
        return this.accountService.findUserById(id);
    }

    /**
     * Récupère la liste de tous les utilisateurs ayant le rôle "HOTEL_MANAGER".
     * @return Une liste contenant tous les utilisateurs qui sont des managers d'hôtels
     */
    @GetMapping("/managers")
    List<AppUser> getManagers() {
        return this.accountService.findAllManagers();
    }

    /**
     * Récupère la collection de gestionnaires associés à un hôtel spécifié.
     * @param id L'identifiant de l'hôtel pour lequel récupérer les gestionnaires.
     * @return Une collection d'utilisateurs (gestionnaires) associés à l'hôtel spécifié.
     */
    @GetMapping("/managers/{id}")
    Collection<AppUser> getManagersByHotel(@PathVariable("id") Long id) {
        return accountService.findManagerByHotel(id);
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
     * Créer un nouveau manager avec le nom d'utilisateur spécifié.
     * @param username Nom d'utilisateur du nouveau manager.
     * @return L'objet AppUser créé qui représente le nouveau manager.
     */
    @PostMapping("/manager")
    public AppUser postUser(@RequestBody String username){
        return this.accountService.saveManager(username);
    }

    /**
     * Mettre à jour le nom d'utilisateur d'un manager existant.
     * @param id       ID du manager à mettre à jour.
     * @param username Nouveau nom d'utilisateur pour le manager.
     * @return L'objet AppUser mis à jour qui représente le manager.
     */
    @PostMapping("/manager/{id}")
    public AppUser updateManager(@PathVariable Long id, @RequestBody String username) {
        return accountService.updateManager(id, username);
    }

    /**
     * Supprime un gestionnaire (AppUser) à partir de son ID.
     * @param id L'ID du gestionnaire à supprimer.
     */
    @DeleteMapping("/manager/{id}")
    public void deleteManager(@PathVariable Long id) {
        accountService.deleteManager(id);
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

    /**
     * Assigner un hôtel à un manager.
     * @param idUser   ID du manager auquel assigner l'hôtel.
     * @param idHotel  ID de l'hôtel à assigner.
     * @return ResponseEntity contenant l'objet Hotel assigné et le statut HTTP OK.
     */
    @GetMapping("assign/{idUser}/{idHotel}")
    public ResponseEntity<Hotel> assignHotelToManager(@PathVariable("idUser") Long idUser, @PathVariable("idHotel") Long idHotel) {
        Hotel hotel = accountService.assignHotelToManager(idUser, idHotel);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    /**
     * Supprimer un hôtel d'un manager.
     * @param idUser   ID du manager dont l'hôtel doit être supprimé.
     * @param idHotel  ID de l'hôtel à supprimer.
     * @return ResponseEntity contenant l'objet Hotel supprimé et le statut HTTP OK.
     */
    @GetMapping("remove/{idUser}/{idHotel}")
    public ResponseEntity<Hotel> removeHotelToManager(@PathVariable("idUser") Long idUser, @PathVariable("idHotel") Long idHotel) {
        Hotel hotel = accountService.removeHotelFromManager(idUser, idHotel);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
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
