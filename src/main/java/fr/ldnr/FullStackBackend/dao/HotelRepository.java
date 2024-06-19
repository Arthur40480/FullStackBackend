package fr.ldnr.FullStackBackend.dao;

import fr.ldnr.FullStackBackend.entities.City;
import fr.ldnr.FullStackBackend.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    public List<Hotel> findByCity(City city);
    List<Hotel> findByManagers_Id(Long managerId);
}
