package fr.ldnr.FullStackBackend.web;

import fr.ldnr.FullStackBackend.business.IBusinessImpl;
import fr.ldnr.FullStackBackend.entities.Hotel;
import fr.ldnr.FullStackBackend.services.images.IImageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private IBusinessImpl iBusiness;

    @Autowired
    private IImageImpl iImageimpl;

    /**
     * Télécharge l'image d'un hôtel en tant que ressource.
     * @param idHotel L'identifiant de l'hôtel dont l'image doit être téléchargée.
     * @return ResponseEntity contenant la ressource de l'image.
     * @throws Exception Si une erreur survient lors du chargement de l'image.
     */
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadImg(@PathVariable("id") Long idHotel) throws Exception {
        logger.info("Téléchargement de l'image pour l'hôtel avec ID : {}", idHotel);
        Optional<Hotel> optionalHotel = iBusiness.getHotelById(idHotel);

        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            String imgName = hotel.getImg();
            Resource resource = iImageimpl.loadImageAsResource(imgName);
            String contentType = iImageimpl.getContentType(imgName);

            logger.info("Image {} téléchargée avec succès pour l'hôtel ID : {}", imgName, idHotel);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } else {
            logger.warn("Aucun hôtel trouvé avec l'ID : {}", idHotel);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Télécharge une image.
     * @param file Le fichier image à télécharger.
     * @return ResponseEntity contenant un message indiquant le succès ou l'échec du téléchargement.
     */
    @PostMapping("/download")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        if (file.isEmpty()) {
            logger.warn("Tentative de téléchargement d'un fichier vide.");
            response.put("message", "Le fichier est vide.");
            return ResponseEntity.badRequest().body(response);
        }

        String fileName = file.getOriginalFilename();
        try {
            iImageimpl.uploadImage(file);
            logger.info("Fichier {} téléchargé avec succès.", fileName);
        } catch (IOException e) {
            logger.error("Erreur lors du téléchargement du fichier {}.", fileName, e);
            response.put("message", "Erreur lors du téléchargement du fichier " + fileName + ".");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            logger.error("Une erreur inattendue est survenue lors du téléchargement du fichier {}.", fileName, e);
            response.put("message", "Une erreur inattendue est survenue lors du téléchargement du fichier " + fileName + ".");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        response.put("message", "Le fichier " + fileName + " a été téléchargé avec succès.");
        return ResponseEntity.ok(response);
    }

    /**
     * Télécharge une image et l'assigne à un hôtel.
     * @param idHotel L'identifiant de l'hôtel auquel l'image doit être assignée.
     * @param file Le fichier image à télécharger.
     * @return ResponseEntity contenant un message indiquant le succès ou l'échec du téléchargement et de l'assignation.
     * @throws IOException Si une erreur survient lors du téléchargement du fichier.
     */
    @PostMapping("/download/{id}")
    public ResponseEntity<Map<String, String>> uploadImage(@PathVariable("id") Long idHotel, @RequestParam("file") MultipartFile file) throws IOException {
        Map<String, String> response = new HashMap<>();

        if (file.isEmpty()) {
            logger.warn("Tentative de téléchargement d'un fichier vide pour l'hôtel ID : {}", idHotel);
            response.put("message", "Le fichier est vide.");
            return ResponseEntity.badRequest().body(response);
        }
        String fileName = file.getOriginalFilename();

        try {
            iImageimpl.uploadAndAssignImageToHotel(idHotel, file);
            logger.info("Fichier {} téléchargé et assigné avec succès à l'hôtel ID : {}", fileName, idHotel);
        } catch (IOException e) {
            logger.error("Erreur lors du téléchargement du fichier {} pour l'hôtel ID : {}", fileName, idHotel, e);
            response.put("message", "Erreur lors du téléchargement du fichier " + fileName + ".");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        response.put("message", "Le fichier " + fileName + " a été téléchargé avec succès et a bien été assigné à l'hôtel ID : " + idHotel);
        return ResponseEntity.ok(response);
    }
}