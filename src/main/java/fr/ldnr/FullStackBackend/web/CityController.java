package fr.ldnr.FullStackBackend.web;

import fr.ldnr.FullStackBackend.business.IBusinessImpl;
import fr.ldnr.FullStackBackend.entities.City;
import fr.ldnr.FullStackBackend.entities.CityDTO;
import fr.ldnr.FullStackBackend.entities.Hotel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin("*")
@RestController
public class CityController {

    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    @Autowired
    IBusinessImpl iBusiness;

    /**
     * Récupère la liste de toutes les villes.
     * @return Liste de toutes les villes.
     */
    @GetMapping("/cities")
    public List<CityDTO> getAllCity() {
        logger.info("Récupération de toutes les villes");
        return iBusiness.getAllCity();
    }

    /**
     * Récupère une ville par son identifiant.
     * @param id l'identifiant de la ville à récupérer
     * @return un Optional contenant le DTO de la ville si elle est trouvée, sinon un Optional vide
     */
    @GetMapping("/city/{id}")
    public Optional<CityDTO> getCityById(@PathVariable("id") Long id) {
        return iBusiness.getCityById(id);
    }

    /**
     * Enregistre une nouvelle ville.
     * @param cityDTO Le DTO CityDTO représentant la ville à enregistrer.
     * @return ResponseEntity contenant la ville créée et l'URI de localisation.
     */
    @PostMapping("/city")
    public ResponseEntity<CityDTO> saveCity(@RequestBody CityDTO cityDTO) {
        CityDTO cityCreated = iBusiness.saveCityDTO(cityDTO);

        if (Objects.isNull(cityCreated)) {
            logger.warn("La ville n'a pas été enregistrée correctement : {}", cityDTO);
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cityCreated.getId())
                .toUri();

        logger.info("La ville a été enregistrée avec succès avec ID : {}", cityCreated.getId());
        return ResponseEntity.created(location).body(cityCreated);
    }

    /**
     * Supprime une ville par son ID.
     * @param id ID de la ville à supprimer.
     */
    @DeleteMapping("/city/{id}")
    public void deleteCity(@PathVariable("id") Long id) {
        logger.info("Suppression de la ville avec ID : {}", id);
        iBusiness.deleteCity(id);
    }
}
