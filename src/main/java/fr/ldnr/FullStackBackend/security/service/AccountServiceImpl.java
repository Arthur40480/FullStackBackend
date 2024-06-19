package fr.ldnr.FullStackBackend.security.service;

import fr.ldnr.FullStackBackend.dao.HotelRepository;
import fr.ldnr.FullStackBackend.entities.Hotel;
import fr.ldnr.FullStackBackend.security.dao.AppRoleRepository;
import fr.ldnr.FullStackBackend.security.dao.AppUserRepository;
import fr.ldnr.FullStackBackend.security.entities.AppRole;
import fr.ldnr.FullStackBackend.security.entities.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService{

    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final HotelRepository hotelRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, HotelRepository hotelRepository) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.hotelRepository = hotelRepository;
    }

    /**
     * Sauvegarde un utilisateur dans la base de données après avoir hashé son mot de passe.
     * @param user L'utilisateur à sauvegarder
     * @return L'utilisateur sauvegardé
     */
    @Override
    public AppUser saveUser(AppUser user){
        String hashPW = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashPW);
        log.info("Sauvegarde d'un nouvel utilisateur {} en base", user);
        return appUserRepository.save(user);
    }

    /**
     * Enregistre un nouveau gestionnaire (AppUser) avec un mot de passe par défaut et le rôle par défaut "HOTEL_MANAGER".
     * @param username Le nom d'utilisateur du nouveau gestionnaire.
     * @return Le gestionnaire (AppUser) enregistré avec le mot de passe hashé et le rôle par défaut.
     */
    @Override
    public AppUser saveManager(String username) {
        String defaultPassword = "1234";
        String hashPW = bCryptPasswordEncoder.encode(defaultPassword);

        AppUser newManager = new AppUser();
        newManager.setPassword(hashPW);
        newManager.setUsername(username);

        AppRole defaultRole = appRoleRepository.findByRolename("HOTEL_MANAGER");
        newManager.getRoles().add(defaultRole);

        return appUserRepository.save(newManager);
    }

    /**
     * Met à jour le nom d'utilisateur d'un gestionnaire existant identifié par son ID.
     * @param id L'ID du gestionnaire à mettre à jour.
     * @param username Le nouveau nom d'utilisateur du gestionnaire.
     * @return Le gestionnaire (AppUser) mis à jour avec le nouveau nom d'utilisateur.
     * @throws RuntimeException Si aucun gestionnaire avec l'ID spécifié n'est trouvé.
     */
    @Override
    public AppUser updateManager(Long id, String username) {
        AppUser existingManager = appUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        existingManager.setUsername(username);
        return appUserRepository.save(existingManager);
    }

    /**
     * Supprime un gestionnaire (AppUser) en fonction de son ID.
     * @param id L'ID du gestionnaire à supprimer.
     */
    @Override
    public void deleteManager(Long id) {
        appUserRepository.deleteById(id);
    }

    /**
     * Récupère la collection de managers associés à un hôtel spécifié.
     * @param id L'identifiant de l'hôtel pour lequel récupérer les gestionnaires.
     * @return Une collection d'utilisateurs (gestionnaires) associés à l'hôtel spécifié.
     * @throws EntityNotFoundException Si aucun hôtel n'est trouvé avec l'ID spécifié.
     */
    @Override
    public Collection<AppUser> findManagerByHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hôtel non trouvé avec ID : " + id));
        return hotel.getManagers();
    }

    /**
     * Sauvegarde un rôle dans la base de données.
     * @param role Le rôle à sauvegarder
     * @return Le rôle sauvegardé
     */
    @Override
    public AppRole saveRole(AppRole role) {
        log.info("Sauvegarde d'un nouveau role en base");
        return appRoleRepository.save(role);
    }

    /**
     * Ajoute un rôle à un utilisateur spécifié par son nom d'utilisateur.
     * @param userName Le nom d'utilisateur de l'utilisateur auquel ajouter le rôle
     * @param roleName Le nom du rôle à ajouter
     */
    @Override
    public void addRoleToUser(String userName, String roleName){
        AppRole role = appRoleRepository.findByRolename(roleName);
        AppUser user = appUserRepository.findByUsername(userName);
        user.getRoles().add(role);
        log.info("Association d'un rôle à un utilisateur");
    }

    /**
     * Recherche un utilisateur par son nom d'utilisateur.
     * @param username Le nom d'utilisateur de l'utilisateur à rechercher
     * @return L'utilisateur trouvé, ou null s'il n'existe pas
     */
    @Override
    public AppUser findUserByUsername(String username){
        return appUserRepository.findByUsername(username);
    }

    /**
     * Recherche un utilisateur par son identifiant.
     * @param id L'identifiant de l'utilisateur à rechercher.
     * @return Une {@link Optional} contenant l'utilisateur trouvé, ou {@link Optional#empty()} si aucun utilisateur correspondant n'est trouvé.
     */
    @Override
    public Optional<AppUser> findUserById(Long id) {
        return appUserRepository.findById(id);
    }

    /**
     * Récupère tous les utilisateurs ayant le rôle "HOTEL_MANAGER".
     * @return La liste des utilisateurs ayant le rôle "HOTEL_MANAGER"
     */
    @Override
    public List<AppUser> findAllManagers() {
        return appUserRepository.findHotelManagers();
    }

    /**
     * Liste tous les utilisateurs enregistrés dans la base de données.
     * @return Une ResponseEntity contenant la liste de tous les utilisateurs
     */
    @Override
    public ResponseEntity<List<AppUser>> listUsers(){
        return ResponseEntity.ok().body(appUserRepository.findAll());
    }

    @Override
    @Transactional
    public Hotel assignHotelToManager(Long idUser, Long idHotel) {
        AppUser manager = appUserRepository.findById(idUser)
                .orElseThrow(() -> new EntityNotFoundException("Manager à l'id" + idUser + " n'éxiste pas"));
        Hotel hotel = hotelRepository.findById(idHotel)
                .orElseThrow(() -> new EntityNotFoundException("Hôtel à l'id" + idUser + " n'éxiste pas"));

        manager.getManagedHotels().add(hotel);
        appUserRepository.save(manager);

        hotel.getManagers().add(manager);
        hotelRepository.save(hotel);

        return hotel;
    }

    @Override
    @Transactional
    public Hotel removeHotelFromManager(Long idUser, Long idHotel) {
        AppUser manager = appUserRepository.findById(idUser)
                .orElseThrow(() -> new EntityNotFoundException("Manager à l'id" + idUser + " n'éxiste pas"));

        Hotel hotel = hotelRepository.findById(idHotel)
                .orElseThrow(() -> new EntityNotFoundException("Hôtel à l'id" + idUser + " n'éxiste pas"));

        manager.getManagedHotels().remove(hotel);
        appUserRepository.save(manager);

        hotel.getManagers().remove(manager);
        hotelRepository.save(hotel);

        return hotel;
    }
}
