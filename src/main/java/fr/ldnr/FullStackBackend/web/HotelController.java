package fr.ldnr.FullStackBackend.web;

import fr.ldnr.FullStackBackend.business.IBusinessImpl;
import fr.ldnr.FullStackBackend.entities.Hotel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@CrossOrigin("*")
@RestController
public class HotelController {

    private static final Logger logger = LoggerFactory.getLogger(HotelController.class);

    @Autowired
    IBusinessImpl iBusiness;

    /**
     * Récupère la liste de tous les hôtels.
     * @return Liste de tous les hôtels.
     */
    @GetMapping("/hotels")
    public List<Hotel> getAllHotels() {
        logger.info("Récupération de tous les hôtels");
        return iBusiness.getAllHotel();
    }

    /**
     * Récupère la liste des hôtels pour une ville donnée.
     * @param id ID de la ville pour laquelle récupérer les hôtels.
     * @return Liste des hôtels pour la ville spécifiée.
     */
    @GetMapping("/hotels/{id}")
    public List<Hotel> getHotelByCity(@PathVariable("id") Long id) {
        logger.info("Récupération des hôtels pour la ville avec ID : {}", id);
        return iBusiness.getHotelByCity(id);
    }

    /**
     * Récupère un hôtel par son ID.
     * @param id ID de l'hôtel à récupérer.
     * @return L'hôtel correspondant à l'ID spécifié.
     * @throws NoSuchElementException Si aucun hôtel n'est trouvé pour l'ID spécifié.
     */
    @GetMapping("/hotel/{id}")
    public Hotel getHotelById(@PathVariable("id") Long id) {
        logger.info("Récupération de l'hôtel avec ID : {}", id);
        return iBusiness.getHotelById(id)
                .orElseThrow(() -> new NoSuchElementException("Aucun hôtel trouvé avec l'ID : " + id));
    }

    /**
     * Enregistre un nouvel hôtel.
     * @param hotel L'hôtel à enregistrer.
     * @return ResponseEntity contenant l'hôtel créé et l'URI de localisation.
     */
    @PostMapping("/hotel")
    public ResponseEntity<Hotel> saveHotel(@RequestBody Hotel hotel) {
        Hotel hotelCreated = iBusiness.saveHotel(hotel);

        if (Objects.isNull(hotelCreated)) {
            logger.warn("L'hôtel n'a pas été enregistré correctement : {}", hotel);
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(hotelCreated.getId())
                .toUri();

        logger.info("L'hôtel a été enregistré avec succès avec ID : {}", hotelCreated.getId());
        return ResponseEntity.created(location).body(hotelCreated);
    }

    /**
     * Supprime un hôtel par son ID.
     * @param id ID de l'hôtel à supprimer.
     */
    @DeleteMapping("/hotel/{id}")
    public void deleteHotel(@PathVariable("id") Long id) {
        logger.info("Suppression de l'hôtel avec ID : {}", id);
        iBusiness.deleteHotel(id);
    }
}