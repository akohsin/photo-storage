package com.akohsin.photostorage.controllers;

import com.akohsin.photostorage.dto.Direction;
import com.akohsin.photostorage.dto.PaginatedDto;
import com.akohsin.photostorage.dto.PhotoResponseDto;
import com.akohsin.photostorage.dto.SortField;
import com.akohsin.photostorage.entities.User;
import com.akohsin.photostorage.security.SecurityConstants;
import com.akohsin.photostorage.services.PhotoService;
import com.akohsin.photostorage.services.UserService;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/photo")
public class PhotoController {

    private PhotoService photoService;

    private UserService userService;

    @Autowired
    public PhotoController(PhotoService photoService, UserService userService) {
        this.photoService = photoService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<PhotoResponseDto> upload(HttpServletRequest request) throws FileUploadException {
        Optional<User> userByToken = userService.getByToken(request);
        if (userByToken.isPresent()) {
            Optional<PhotoResponseDto> mapLayer = photoService.upload(request, userByToken.get());
            if (!mapLayer.isPresent()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            else return ResponseEntity.status(HttpStatus.CREATED).build();
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping
    public ResponseEntity<PaginatedDto<PhotoResponseDto>> getBy(@RequestParam(name = "perPage") Integer perPage,
                                                                @RequestParam(name = "page") Integer page,
                                                                @RequestParam(name = "sortBy") SortField sortField,
                                                                @RequestParam(name = "filename", required = false) String filename,
                                                                @RequestParam(name = "direction") Direction direction,
                                                                HttpServletRequest request) {
        Optional<User> userByToken = userService.getByToken(request);
        if (userByToken.isPresent()) {
            if (Objects.nonNull(filename) && !filename.trim().equals("")) {
                PaginatedDto<PhotoResponseDto> photos = photoService.findByName(perPage, page, sortField, direction, filename, userByToken.get());
                if (photos.getContent().isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                else return ResponseEntity.status(HttpStatus.OK).body(photos);
            } else {
                PaginatedDto<PhotoResponseDto> photos = photoService.getBy(perPage, page, sortField, direction, userByToken.get());
                if (photos.getContent().isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                else return ResponseEntity.status(HttpStatus.OK).body(photos);
            }
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id, HttpServletRequest request) {
        Optional<User> userByToken = userService.getByToken(request);
        if (userByToken.isPresent()) {
            if (Objects.nonNull(id)) {
                if (photoService.delete(id, userByToken.get())) return ResponseEntity.status(HttpStatus.OK).build();
                else return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhotoResponseDto> getFullImage(@PathVariable Long id, HttpServletRequest request) {
        Optional<User> userByToken = userService.getByToken(request);
        if (userByToken.isPresent()) {
            if (Objects.nonNull(id)) {
                PhotoResponseDto fullImage = photoService.getFullImage(id, userByToken.get());
                if (fullImage.getThumbnail().length > 0) return ResponseEntity.status(HttpStatus.OK).body(fullImage);
                else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
