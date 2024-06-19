package fr.ldnr.FullStackBackend;

import fr.ldnr.FullStackBackend.business.IBusinessImpl;
import fr.ldnr.FullStackBackend.entities.City;
import fr.ldnr.FullStackBackend.entities.Hotel;
import fr.ldnr.FullStackBackend.security.entities.AppRole;
import fr.ldnr.FullStackBackend.security.entities.AppUser;
import fr.ldnr.FullStackBackend.security.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class FullStackBackendApplication implements CommandLineRunner {

	@Autowired
	IBusinessImpl iBusiness;

	@Autowired
	AccountServiceImpl accountService;

	public static void main(String[] args) { SpringApplication.run(FullStackBackendApplication.class, args); }

	@Override
	public void run(String... args) throws Exception {
		generatedData();
	}

	public void generatedData() {

		List<Hotel> ListHotel = new ArrayList<>();
		List<AppUser> ListManager = new ArrayList<>();

		City paris = new City(null, "Paris", ListHotel);
		City marseille = new City(null, "Marseille", ListHotel);
		City toulouse = new City(null, "Toulouse", ListHotel);
		City bordeaux = new City(null, "Bordeaux", ListHotel);
		City cannes = new City(null, "Cannes", ListHotel);

		iBusiness.saveCity(paris);
		iBusiness.saveCity(marseille);
		iBusiness.saveCity(toulouse);
		iBusiness.saveCity(bordeaux);
		iBusiness.saveCity(cannes);

		iBusiness.saveHotel(new Hotel(null, "Hôtel de Crillon","Découvrez notre hôtel de luxe niché en bord de mer, offrant des vues imprenables, des suites somptueuses, une cuisine gastronomique et un spa indulgent. Profitez d'un service exceptionnel et d'instants mémorables dans un cadre paradisiaque.", "0554323457", "15 Rue des Lilas, 75020 Paris", 4, 12,10, 80, "hotel1.jpg", paris, new ArrayList<>()));
		iBusiness.saveHotel(new Hotel(null, "Le Bristol Paris", "Découvrez notre hôtel de luxe niché en bord de mer, offrant des vues imprenables, des suites somptueuses, une cuisine gastronomique et un spa indulgent. Profitez d'un service exceptionnel et d'instants mémorables dans un cadre paradisiaque.", "0559037457", "9 Avenue des artisans, 75020 Paris", 5, 8, 45, 160, "hotel2.jpg", paris, new ArrayList<>()));
		iBusiness.saveHotel(new Hotel(null, "L'Escapade Dorée", "Découvrez notre hôtel de luxe niché en bord de mer, offrant des vues imprenables, des suites somptueuses, une cuisine gastronomique et un spa indulgent. Profitez d'un service exceptionnel et d'instants mémorables dans un cadre paradisiaque.","0554326636", "14 Impasse de Bretagne, 75020 Paris", 3, 20,6, 45, "hotel3.jpg", paris, new ArrayList<>()));

		iBusiness.saveHotel(new Hotel(null, "Sofitel Vieux-Port", "Découvrez notre hôtel de luxe niché en bord de mer, offrant des vues imprenables, des suites somptueuses, une cuisine gastronomique et un spa indulgent. Profitez d'un service exceptionnel et d'instants mémorables dans un cadre paradisiaque.","0554326636", "9 rue du soleil, 13001 Marseille", 2, 20,12, 70, "hotel4.jpg", marseille, new ArrayList<>()));
		iBusiness.saveHotel(new Hotel(null, "Hôtel Ibis", "Découvrez notre hôtel de luxe niché en bord de mer, offrant des vues imprenables, des suites somptueuses, une cuisine gastronomique et un spa indulgent. Profitez d'un service exceptionnel et d'instants mémorables dans un cadre paradisiaque.","0551234636", "10 Place de la République, 13001 Marseille", 5, 5,23, 125, "hotel5.jpg", marseille, new ArrayList<>()));
		iBusiness.saveHotel(new Hotel(null, "Radisson Blu Hotel", "Découvrez notre hôtel de luxe niché en bord de mer, offrant des vues imprenables, des suites somptueuses, une cuisine gastronomique et un spa indulgent. Profitez d'un service exceptionnel et d'instants mémorables dans un cadre paradisiaque.","0554329809", "15 Quai des Belges, 13001 Marseille", 1, 30,44, 35, "hotel6.jpg", marseille, new ArrayList<>()));

		iBusiness.saveHotel(new Hotel(null, "Hôtel Palladia", "Découvrez notre hôtel de luxe niché en bord de mer, offrant des vues imprenables, des suites somptueuses, une cuisine gastronomique et un spa indulgent. Profitez d'un service exceptionnel et d'instants mémorables dans un cadre paradisiaque.","0554329876", "10 Rue de Metz, 31000 Toulouse", 1, 7, 5,85, "hotel7.jpg", toulouse, new ArrayList<>()));
		iBusiness.saveHotel(new Hotel(null, "Hôtel des Beaux Arts", "Découvrez notre hôtel de luxe niché en bord de mer, offrant des vues imprenables, des suites somptueuses, une cuisine gastronomique et un spa indulgent. Profitez d'un service exceptionnel et d'instants mémorables dans un cadre paradisiaque.","0554329547", "35 Rue du Taur, 31000 Toulouse", 5, 13,22, 190, "hotel8.jpg", toulouse, new ArrayList<>()));
		iBusiness.saveHotel(new Hotel(null, "Le Crowne Plaza", "Découvrez notre hôtel de luxe niché en bord de mer, offrant des vues imprenables, des suites somptueuses, une cuisine gastronomique et un spa indulgent. Profitez d'un service exceptionnel et d'instants mémorables dans un cadre paradisiaque.","0554453436", "18 Place du Capitole, 31000 Toulouse", 4, 20,155, 200, "hotel9.jpg", toulouse, new ArrayList<>()));

		iBusiness.saveHotel(new Hotel(null, "InterContinental Bordeaux", "Découvrez notre hôtel de luxe niché en bord de mer, offrant des vues imprenables, des suites somptueuses, une cuisine gastronomique et un spa indulgent. Profitez d'un service exceptionnel et d'instants mémorables dans un cadre paradisiaque.","0555565636", "3 Rue Sainte-Catherine, 33000 Bordeaux", 3, 90,100, 45, "hotel10.jpg", bordeaux, new ArrayList<>()));
		iBusiness.saveHotel(new Hotel(null, "Hôtel de Sèze", "Découvrez notre hôtel de luxe niché en bord de mer, offrant des vues imprenables, des suites somptueuses, une cuisine gastronomique et un spa indulgent. Profitez d'un service exceptionnel et d'instants mémorables dans un cadre paradisiaque.","0554678936", "22 Quai de Bacalan, 33300 Bordeaux", 3, 2, 8, 65, "hotel11.jpg", bordeaux,new ArrayList<>()));
		iBusiness.saveHotel(new Hotel(null, "Yndo Hotel", "Découvrez notre hôtel de luxe niché en bord de mer, offrant des vues imprenables, des suites somptueuses, une cuisine gastronomique et un spa indulgent. Profitez d'un service exceptionnel et d'instants mémorables dans un cadre paradisiaque.","0554009036", "8 Place de la Bourse, 33000 Bordeaux", 5, 11,87, 110, "hotel12.jpg", bordeaux,new ArrayList<>()));

		iBusiness.saveHotel(new Hotel(null, "Hôtel Martinez", "Découvrez notre hôtel de luxe niché en bord de mer, offrant des vues imprenables, des suites somptueuses, une cuisine gastronomique et un spa indulgent. Profitez d'un service exceptionnel et d'instants mémorables dans un cadre paradisiaque.","0554010136", "45 Boulevard de la Croisette, 06400 Cannes", 2, 10,44, 80, "hotel13.jpg", cannes,new ArrayList<>()));
		iBusiness.saveHotel(new Hotel(null, "Grand Hyatt Cannes", "Découvrez notre hôtel de luxe niché en bord de mer, offrant des vues imprenables, des suites somptueuses, une cuisine gastronomique et un spa indulgent. Profitez d'un service exceptionnel et d'instants mémorables dans un cadre paradisiaque.","0559899636", "12 Rue des Serbes, 06400 Cannes", 2, 9, 105,22, "hotel14.jpg", cannes,new ArrayList<>()));
		iBusiness.saveHotel(new Hotel(null, "Le Majestic Cannes", "Découvrez notre hôtel de luxe niché en bord de mer, offrant des vues imprenables, des suites somptueuses, une cuisine gastronomique et un spa indulgent. Profitez d'un service exceptionnel et d'instants mémorables dans un cadre paradisiaque.","0554376396", "20 Avenue Montaigne, 06400 Cannes", 3, 32, 32,95, "hotel15.jpg", cannes,new ArrayList<>()));

		accountService.saveUser(new AppUser(null,"arthur", "1234", new ArrayList<>(), new ArrayList<>()));
		accountService.saveUser(new AppUser(null,"ambre", "1234", new ArrayList<>(), new ArrayList<>()));
		accountService.saveUser(new AppUser(null,"pierre", "1234", new ArrayList<>(), new ArrayList<>()));
		accountService.saveUser(	new AppUser(null,"pascale", "1234", new ArrayList<>(), new ArrayList<>()));
		accountService.saveUser(	new AppUser(null,"rogenka", "1234", new ArrayList<>(), new ArrayList<>()));


		accountService.saveRole(new AppRole(null,"ADMIN"));
		accountService.saveRole(new AppRole(null,"USER"));
		accountService.saveRole(new AppRole(null,"HOTEL_MANAGER"));

		accountService.addRoleToUser("arthur", "ADMIN");
		accountService.addRoleToUser("arthur", "USER");
		accountService.addRoleToUser("ambre", "USER");
		accountService.addRoleToUser("pierre", "USER");
		accountService.addRoleToUser("pascale", "USER");
		accountService.addRoleToUser("rogenka", "USER");

	}
}
