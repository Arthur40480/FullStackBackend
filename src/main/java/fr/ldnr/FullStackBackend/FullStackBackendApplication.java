package fr.ldnr.FullStackBackend;

import fr.ldnr.FullStackBackend.business.IBusinessImpl;
import fr.ldnr.FullStackBackend.entities.City;
import fr.ldnr.FullStackBackend.entities.Hotel;
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

	public static void main(String[] args) { SpringApplication.run(FullStackBackendApplication.class, args); }

	@Override
	public void run(String... args) throws Exception {
		generatedData();
	}

	public void generatedData() {

		List<Hotel> ListHotel = new ArrayList<>();
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

		iBusiness.saveHotel(new Hotel(null, "Hôtel de Crillon", "0554323457", "15 Rue des Lilas, 75020 Paris", 4, 12, 80, "hotel1.jpg", paris));
		iBusiness.saveHotel(new Hotel(null, "Le Bristol Paris", "0559037457", "9 Avenue des artisans, 75020 Paris", 5, 8, 160, "hotel2.jpg", paris));
		iBusiness.saveHotel(new Hotel(null, "Hôtel de Crillon", "0554326636", "14 Impasse de Bretagne, 75020 Paris", 3, 20, 45, "hotel3.jpg", paris));

		iBusiness.saveHotel(new Hotel(null, "Sofitel Vieux-Port", "0554326636", "9 rue du soleil, 13001 Marseille", 2, 20, 70, "hotel4.jpg", marseille));
		iBusiness.saveHotel(new Hotel(null, "Hôtel Ibis", "0551234636", "10 Place de la République, 13001 Marseille", 5, 5, 125, "hotel5.jpg", marseille));
		iBusiness.saveHotel(new Hotel(null, "Radisson Blu Hotel", "0554329809", "15 Quai des Belges, 13001 Marseille", 1, 30, 35, "hotel6.jpg", marseille));

		iBusiness.saveHotel(new Hotel(null, "Hôtel Palladia", "0554329876", "10 Rue de Metz, 31000 Toulouse", 1, 7, 80, "hotel7.jpg", toulouse));
		iBusiness.saveHotel(new Hotel(null, "Hôtel des Beaux Arts", "0554329547", "35 Rue du Taur, 31000 Toulouse", 5, 13, 190, "hotel8.jpg", toulouse));
		iBusiness.saveHotel(new Hotel(null, "Le Crowne Plaza", "0554453436", "18 Place du Capitole, 31000 Toulouse", 4, 20, 200, "hotel9.jpg", toulouse));

		iBusiness.saveHotel(new Hotel(null, "InterContinental Bordeaux", "0555565636", "3 Rue Sainte-Catherine, 33000 Bordeaux", 3, 90, 45, "hotel10.jpg", bordeaux));
		iBusiness.saveHotel(new Hotel(null, "Hôtel de Sèze", "0554678936", "22 Quai de Bacalan, 33300 Bordeaux", 3, 2, 65, "hotel11.jpg", bordeaux));
		iBusiness.saveHotel(new Hotel(null, "Yndo Hotel", "0554009036", "8 Place de la Bourse, 33000 Bordeaux", 5, 11, 110, "hotel12.jpg", bordeaux));

		iBusiness.saveHotel(new Hotel(null, "Hôtel Martinez", "0554010136", "45 Boulevard de la Croisette, 06400 Cannes", 2, 10, 80, "hotel13.jpg", cannes));
		iBusiness.saveHotel(new Hotel(null, "Grand Hyatt Cannes", "0559899636", "12 Rue des Serbes, 06400 Cannes", 2, 9, 105, "hotel14.jpg", cannes));
		iBusiness.saveHotel(new Hotel(null, "Le Majestic Cannes", "0554376396", "20 Avenue Montaigne, 06400 Cannes", 3, 32, 95, "hotel15.jpg", cannes));

	}
}
