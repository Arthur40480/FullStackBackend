package fr.ldnr.FullStackBackend.business;

import fr.ldnr.FullStackBackend.entities.City;
import fr.ldnr.FullStackBackend.entities.CityDTO;
import fr.ldnr.FullStackBackend.entities.Hotel;

import java.util.List;
import java.util.Optional;

public interface IBusiness {

    //CITY
    City saveCity(City city);
    CityDTO saveCityDTO(CityDTO cityDTO);
    void deleteCity(Long id);
    List<CityDTO> getAllCity();

    //HOTEL
    Hotel saveHotel (Hotel hotel);
    void deleteHotel(Long id);
    List<Hotel> getAllHotel();
    List<Hotel> getHotelByCity(Long id);
    Optional<Hotel> getHotelById(Long id);
}
