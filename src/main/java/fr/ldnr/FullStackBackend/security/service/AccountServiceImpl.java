package fr.ldnr.FullStackBackend.security.service;

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

import java.util.List;

@Service
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService{

    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public AppUser saveUser(AppUser user){
        String hashPW = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashPW);
        log.info("Sauvegarde d'un nouvel utilisateur {} en base", user);
        return appUserRepository.save(user);
    }

    @Override
    public AppRole saveRole(AppRole role) {
        log.info("Sauvegarde d'un nouveau role en base");
        return appRoleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String userName, String roleName){
        AppRole role = appRoleRepository.findByRolename(roleName);
        AppUser user = appUserRepository.findByUsername(userName);
        user.getRoles().add(role);
        log.info("association d'un rôle à un utilisateur");
    }

    @Override
    public AppUser findUserByUsername(String username){
        return appUserRepository.findByUsername(username);
    }

    @Override
    public ResponseEntity<List<AppUser>> listUsers(){
        return ResponseEntity.ok().body(appUserRepository.findAll());
    }

}
