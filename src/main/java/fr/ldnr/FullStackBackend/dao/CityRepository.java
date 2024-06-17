package fr.ldnr.FullStackBackend.dao;

import fr.ldnr.FullStackBackend.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
