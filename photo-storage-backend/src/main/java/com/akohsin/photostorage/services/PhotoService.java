package com.akohsin.photostorage.services;

import com.akohsin.photostorage.dto.Direction;
import com.akohsin.photostorage.dto.PaginatedDto;
import com.akohsin.photostorage.dto.PhotoResponseDto;
import com.akohsin.photostorage.dto.SortField;
import com.akohsin.photostorage.entities.Photo;
import com.akohsin.photostorage.repositories.PhotoRepository;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhotoService {

    @Value("${photos.path: ~/.photos/}")
    private String path;

    @Value("${photos.thumbnail-path: ~/.thumbnails/}")
    private String thumbnailsPath;

    private PhotoRepository photoRepository;

    public Optional<PhotoResponseDto> upload(HttpServletRequest request) {
        String fileName = null;
        try {
            fileName = saveFile(request);
            createAndSaveThumbnail(fileName);

            Photo photo = new Photo();
            photo.setFilename(fileName);
            Photo save = photoRepository.save(photo);
            return Optional.of(new PhotoResponseDto(save.getId(), save.getFilename(), save.getCreated().toString()));

        } catch (FileSaveException e) {
            return Optional.empty();
        }

    }

    private void createAndSaveThumbnail(String fileName) throws FileSaveException {
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        try {
            img.createGraphics()
                    .drawImage(ImageIO.read(new File(getItemPath(fileName)))
                            .getScaledInstance(50, 50, Image.SCALE_SMOOTH), 0, 0, null);
            ImageIO.write(img, "jpg", new File(getThumbnailPath(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileSaveException();
        }
    }

    private String saveFile(HttpServletRequest request) throws FileSaveException {
        String fileName = null;
        ServletFileUpload upload = new ServletFileUpload();
        FileItemIterator iterStream = null;
        try {
            iterStream = upload.getItemIterator(request);
            while (iterStream.hasNext()) {
                FileItemStream item = iterStream.next();
                try (InputStream uploadedStream = item.openStream()) {
                    if (!item.isFormField()) {
                        fileName = item.getName();
                        checkDirectory();
                        try (OutputStream out = new FileOutputStream(getItemPath(item.getName()))) {
                            IOUtils.copy(uploadedStream, out);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new FileSaveException();
                }
            }

        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
            throw new FileSaveException();
        }
        return fileName;
    }

    public PaginatedDto<PhotoResponseDto> getBy(Integer perPage, Integer page, SortField sortField, Direction direction) {
        Pageable pageRequest = createPageRequest(perPage, page, sortField, direction);

        List<PhotoResponseDto> responseDtoList = mapEntityToDto(photoRepository.getAllBy(pageRequest));
        return new PaginatedDto<>(responseDtoList, photoRepository.count());
    }
    public PaginatedDto<PhotoResponseDto> findByName(Integer perPage, Integer page, SortField sortField, Direction direction, String fileName) {
        Pageable pageRequest =createPageRequest(perPage, page, sortField, direction);
        List<PhotoResponseDto> responseDtoList = mapEntityToDto(photoRepository.findAllByFilenameContains(pageRequest, fileName));
        return new PaginatedDto<>(responseDtoList, new Long(perPage));
    }

    public boolean delete(Long id) {
        if (photoRepository.existsById(id)) {
            photoRepository.deleteById(id);
            return true;
        } else return false;
    }

    private String getThumbnailPath(String fileName) {
        return thumbnailsPath + fileName;

    }

    private String getItemPath(String fileName) {
        return path + fileName;
    }

    private void checkDirectory() {
        File file = new File(path);
        if (!file.exists()) {
            try {
                Files.createDirectory(Paths.get(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<PhotoResponseDto> mapEntityToDto(Page<Photo> allBy) {
        return allBy.getContent()
                .stream()
                .map(p -> new PhotoResponseDto(p.getId(), p.getFilename(), p.getCreated().toString()))
                .collect(Collectors.toList());
    }

    private Pageable createPageRequest(Integer perPage, Integer page, SortField sortField, Direction direction) {
        PageRequest pageRequest;
        Sort.Direction sortDirection;
        if (direction.equals(Direction.ASC)) {
            sortDirection = Sort.Direction.ASC;
        } else {
            sortDirection = Sort.Direction.DESC;
        }
        if (sortField.equals(SortField.FILENAME)) {
            pageRequest = PageRequest.of(page, perPage, sortDirection, "fileName");
        } else {
            pageRequest = PageRequest.of(page, perPage, sortDirection, "created");
        }
        return pageRequest;
    }
}
