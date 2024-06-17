package fr.ldnr.FullStackBackend;

import fr.ldnr.FullStackBackend.security.entities.AppRole;
import fr.ldnr.FullStackBackend.security.entities.AppUser;
import fr.ldnr.FullStackBackend.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class FullStackBackendApplication implements CommandLineRunner {

	@Autowired
	private AccountService accountService;

	public static void main(String[] args) { SpringApplication.run(FullStackBackendApplication.class, args); }

	@Override
	public void run(String... args) throws Exception {
		generatedData();
	}

	private void generatedData(){
		accountService.saveUser(new AppUser(null,"arthur", "1234", new ArrayList<>()));
		accountService.saveUser(new AppUser(null,"ambre", "1234", new ArrayList<>()));

		accountService.saveRole(new AppRole(null,"ADMIN"));
		accountService.saveRole(new AppRole(null,"USER"));

		accountService.addRoleToUser("arthur", "ADMIN");
		accountService.addRoleToUser("arthur", "USER");
		accountService.addRoleToUser("ambre", "USER");
	}

}
