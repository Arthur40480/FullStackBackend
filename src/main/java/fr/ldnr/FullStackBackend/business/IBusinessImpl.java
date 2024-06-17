package fr.ldnr.FullStackBackend.business;

import fr.ldnr.FullStackBackend.dao.CityRepository;
import fr.ldnr.FullStackBackend.dao.HotelRepository;
import fr.ldnr.FullStackBackend.entities.City;
import fr.ldnr.FullStackBackend.entities.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IBusinessImpl implements IBusiness {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    HotelRepository hotelRepository;

    //------- CITY ------

    /**
     * Sauvegarde une nouvelle ville ou met à jour une ville existante dans le référentiel.
     * @param city l'entité City à sauvegarder
     * @return l'entité City sauvegardée
     */
    @Override
    public City saveCity(City city) { return cityRepository.save(city); }

    /**
     * Supprime une ville du référentiel par son identifiant.
     * @param id l'identifiant de la ville à supprimer
     */
    @Override
    public void deleteCity(Long id) { cityRepository.deleteById(id); }

    /**
     * Récupère toutes les villes du référentiel.
     * @return une liste de toutes les villes
     */
    @Override
    public List<City> getAllCity() { return cityRepository.findAll(); }

    /**
     * Sauvegarde un nouvel hôtel ou met à jour un hôtel existant dans le référentiel.
     * @param hotel l'entité Hotel à sauvegarder
     * @return l'entité Hotel sauvegardée
     */
    @Override
    public Hotel saveHotel(Hotel hotel) { return hotelRepository.save(hotel); }

    /**
     * Supprime un hôtel du référentiel par son identifiant.
     * @param id l'identifiant de l'hôtel à supprimer
     */
    @Override
    public void deleteHotel(Long id) { hotelRepository.deleteById(id); }

    /**
     * Récupère tous les hôtels du référentiel.
     * @return une liste de tous les hôtels
     */
    @Override
    public List<Hotel> getAllHotel() { return hotelRepository.findAll(); }

    /**
     * Récupère tous les hôtels situés dans une ville donnée par l'identifiant de la ville.
     * @param id l'identifiant de la ville
     * @return une liste d'hôtels situés dans la ville spécifiée
     */
    @Override
    public List<Hotel> getHotelByCity(Long id) {
        City city = cityRepository.findById(id).orElse(null);
        return hotelRepository.findByCity(city);
    }

    /**
     * Récupère un hôtel par son identifiant.
     * @param id l'identifiant de l'hôtel
     * @return un Optional contenant l'hôtel si trouvé, sinon un Optional vide
     */
    @Override
    public Optional<Hotel> getHotelById(Long id) { return hotelRepository.findById(id); }
}
