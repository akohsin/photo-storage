package com.akohsin.photostorage.dto;

public class PhotoResponseDto {

    private Long id;

    private String filename;

    private String created;

    public PhotoResponseDto() {
    }

    public PhotoResponseDto(Long id, String filename, String created) {
        this.id = id;
        this.filename = filename;
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
