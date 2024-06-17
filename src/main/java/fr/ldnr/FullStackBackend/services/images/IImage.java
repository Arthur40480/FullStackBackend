package fr.ldnr.FullStackBackend.services.images;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImage {
    Resource loadImageAsResource(String imgName) throws Exception;
    String getContentType(String imgName) throws Exception;
    String uploadAndAssignImageToHotel(Long id, MultipartFile file) throws IOException;
    void uploadImage(MultipartFile file) throws IOException;
}
