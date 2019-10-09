package com.akohsin.photostorage.repositories;

import com.akohsin.photostorage.entities.Photo;
import com.akohsin.photostorage.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PhotoRepository extends JpaRepository<Photo, Long> {


    Page<Photo> getAllBy(Pageable pageable);

    Page<Photo> findAllByFilenameContains(Pageable pageable, String filename);

    Page<Photo> getAllByUser(User user, Pageable pageRequest);

    Page<Photo> findAllByUserAndFilenameContains(User user, String fileName, Pageable pageRequest);

    Long countByUser(User user);
}
