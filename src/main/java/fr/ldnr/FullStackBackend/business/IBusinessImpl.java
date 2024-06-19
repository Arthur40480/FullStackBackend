package fr.ldnr.FullStackBackend.business;

import fr.ldnr.FullStackBackend.dao.CityRepository;
import fr.ldnr.FullStackBackend.dao.HotelRepository;
import fr.ldnr.FullStackBackend.entities.City;
import fr.ldnr.FullStackBackend.entities.CityDTO;
import fr.ldnr.FullStackBackend.entities.Hotel;
import fr.ldnr.FullStackBackend.mapper.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public CityDTO saveCityDTO(CityDTO cityDTO) {
        City city = cityRepository.save(CityMapper.mapToEntity(cityDTO));
        return CityMapper.mapToDto(city);
    }

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
    public List<CityDTO> getAllCity() {
        return cityRepository.findAll()
                .stream()
                .map(CityMapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une ville par son identifiant et la transforme en CityDTO.
     * @param id l'identifiant de la ville à récupérer
     * @return un Optional contenant le DTO de la ville si elle est trouvée, sinon un Optional vide
     */
    @Override
    public Optional<CityDTO> getCityById(Long id) {
        return cityRepository.findById(id)
                .map(CityMapper::mapToDto);
    }

    //------- HOTEL ------
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

    @Override
    public List<Hotel> getHotelsByManagerId(Long id) {
        return hotelRepository.findByManagers_Id(id);
    }

    /**
     * Récupère un hôtel par son identifiant.
     * @param id l'identifiant de l'hôtel
     * @return un Optional contenant l'hôtel si trouvé, sinon un Optional vide
     */
    @Override
    public Optional<Hotel> getHotelById(Long id) { return hotelRepository.findById(id); }
}
