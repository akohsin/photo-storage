package com.akohsin.photostorage.controllers;

import com.akohsin.photostorage.dto.Direction;
import com.akohsin.photostorage.dto.PaginatedDto;
import com.akohsin.photostorage.dto.PhotoResponseDto;
import com.akohsin.photostorage.dto.SortField;
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
@RequestMapping(path = "/photo/")
public class PhotoController {

    private PhotoService photoService;

    private UserService userService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping
    public ResponseEntity<PhotoResponseDto> upload(HttpServletRequest request) throws FileUploadException {
        Optional<PhotoResponseDto> mapLayer = photoService.upload(request);
        if (!mapLayer.isPresent()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        else return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<PaginatedDto<PhotoResponseDto>> getBy(@RequestParam(name = "perPage") Integer perPage,
                                                                @RequestParam(name = "page") Integer page,
                                                                @RequestParam(name = "sortBy") SortField sortField,
                                                                @RequestParam(name = "filename", required = false) String fileName,
                                                                @RequestParam(name = "direction") Direction direction) {

        if (Objects.nonNull(fileName)) {
            PaginatedDto<PhotoResponseDto> photos = photoService.findByName(perPage, page, sortField, direction, fileName);
            if (photos.getContent().isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            else return ResponseEntity.status(HttpStatus.OK).body(photos);
        } else {
            PaginatedDto<PhotoResponseDto> photos = photoService.getBy(perPage, page, sortField, direction);
            if (photos.getContent().isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            else return ResponseEntity.status(HttpStatus.OK).body(photos);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (Objects.nonNull(id)) {
            if (photoService.delete(id)) return ResponseEntity.status(HttpStatus.OK).build();
            else return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
