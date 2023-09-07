package com.marko.kladionicajava.service;

import com.marko.kladionicajava.entitiy.FileDescription;
import com.marko.kladionicajava.entitiy.Users;
import com.marko.kladionicajava.repository.FileDescriptionRepository;
import com.marko.kladionicajava.repository.UserRepository;
import com.marko.kladionicajava.tools.FileStorageProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
public class FileService {

    private final Path fileStorageLocation;
    private final UserRepository userRepository;
    private final FileDescriptionRepository fileDescriptionRepository;


    public FileService(FileStorageProperties fileStorageProperties, FileDescriptionRepository fileDescriptionRepository, UserRepository userRepository) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        this.fileDescriptionRepository = fileDescriptionRepository;
        this.userRepository = userRepository;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadImage(MultipartFile file, String username) {

        try {
            String[] originalFilenameSplit = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
            if (originalFilenameSplit.length > 2) {
                throw new IllegalArgumentException("Not supported file name. Please make sure to remove all dots from name of the file!!!");
            }
            String originalFileType = originalFilenameSplit[originalFilenameSplit.length - 1];
            if (!originalFileType.toLowerCase().contains("jpg") || originalFileType.toLowerCase().contains("jpeg")) {
                throw new IllegalArgumentException("File type is not PDF, please upload PDF file!!!");
            }
//            Optional<Users> optionalAppUser = userRepository.findByUsername(username);
//            if (optionalAppUser.isEmpty()) {
//                throw new IllegalArgumentException("User not found!!!");
//            }
//            FileDescription fileDescription = new FileDescription();
//            fileDescription.setFileType(file.getContentType());
//            fileDescription.setOriginalFileName(file.getOriginalFilename());
//            fileDescription.setFileName(UUID.randomUUID() + "." + originalFileType);
//            fileDescription.setUserID("ddfdcdcdc");//optionalAppUser.get().getUsername()
            Path targetLocation = fileStorageLocation
                    .resolve("images/" + UUID.randomUUID() + "." + originalFileType);
//            fileDescription.setLocation("/static/images/" + fileDescription.getFileName());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//            fileDescription = fileDescriptionRepository.save(fileDescription);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String retrieveImage(String fileName) {
        try {
            fileName = "images/" + fileName;
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource.toString();
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }
}
