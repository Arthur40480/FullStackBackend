package fr.ldnr.FullStackBackend.services.images;

import fr.ldnr.FullStackBackend.business.IBusinessImpl;
import fr.ldnr.FullStackBackend.entities.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class IImageImpl implements IImage {

    @Autowired
    private IBusinessImpl iBusiness;

    @Autowired
    private Environment env;

    private String BASE_PATH;

    /**
     * Initialise le chemin de base pour les images des hôtels après la construction de l'objet.
     */
    @PostConstruct
    public void init() {
        String userHome = env.getProperty("app.home");
        BASE_PATH = userHome + File.separator + "Pictures" + File.separator + "hotels" + File.separator + "images";
    }

    /**
     * Charge une image en tant que ressource.
     * @param imgName Le nom de l'image à charger.
     * @return La ressource représentant l'image.
     * @throws Exception Si l'image n'existe pas ou n'est pas lisible.
     */
    @Override
    public Resource loadImageAsResource(String imgName) throws Exception {
        Path imagePath = Paths.get(BASE_PATH).resolve(imgName);
        Resource resource = new UrlResource(imagePath.toUri());
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new Exception("La lecture du fichier est impossible");
        }
    }

    /**
     * Récupère le type de contenu d'une image.
     * @param imgName Le nom de l'image.
     * @return Le type de contenu de l'image.
     * @throws Exception Si le type de contenu ne peut être déterminé.
     */
    @Override
    public String getContentType(String imgName) throws Exception {
        Path path = Paths.get(BASE_PATH).resolve(imgName);
        String contentType = Files.probeContentType(path);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }

    /**
     * Télécharge une image.
     * @param file Le fichier image à télécharger.
     * @throws IOException Si une erreur survient lors du téléchargement du fichier.
     */
    @Override
    public void uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Aucun fichier n'a été sélectionné.");
        }
        String filename = file.getOriginalFilename();
        file.transferTo(new File(BASE_PATH + File.separator + filename));
    }

    /**
     * Télécharge une image et l'assigne à un hôtel.
     * @param id   L'identifiant de l'hôtel.
     * @param file Le fichier image à télécharger.
     * @return Le nom du fichier téléchargé.
     * @throws IOException Si une erreur survient lors du téléchargement du fichier.
     */
    @Override
    public String uploadAndAssignImageToHotel(Long id, MultipartFile file) throws IOException {
        Optional<Hotel> optionalHotel = iBusiness.getHotelById(id);
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();

            if (file.isEmpty()) {
                throw new IllegalArgumentException("Aucun fichier n'a été sélectionné.");
            }
            String filename = file.getOriginalFilename();
            file.transferTo(new File(BASE_PATH + File.separator + filename));
            hotel.setImg(filename);
            iBusiness.saveHotel(hotel);
            return filename;
        } else {
            throw new IllegalArgumentException("L'hôtel avec l'identifiant " + id + " n'existe pas.");
        }
    }
}
