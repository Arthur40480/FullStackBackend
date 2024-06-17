package fr.ldnr.FullStackBackend.mapper;

import fr.ldnr.FullStackBackend.entities.City;
import fr.ldnr.FullStackBackend.entities.CityDTO;

public class CityMapper {

    /**
     * Convertit une entité City en DTO CityDTO.
     * @param city L'entité City à convertir.
     * @return CityDTO converti à partir de l'entité City.
     */
    public static CityDTO mapToDto(City city) {
        return CityDTO.builder()
                .id(city.getId())
                .name(city.getName())
                .build();
    }

    /**
     * Convertit un DTO CityDTO en entité City.
     * @param cityDTO Le DTO CityDTO à convertir.
     * @return City converti à partir du DTO CityDTO.
     */
    public static City mapToEntity(CityDTO cityDTO) {
        return City.builder()
                .id(cityDTO.getId())
                .name(cityDTO.getName())
                .build();
    }
}
