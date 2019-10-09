package com.akohsin.photostorage.dto;

public class PhotoResponseDto {

    private Long id;

    private String filename;

    private String created;

    private byte[] thumbnail;

    public PhotoResponseDto() {
    }

    public PhotoResponseDto(Long id, String filename, String created, byte[] thumbnail) {
        this.id = id;
        this.filename = filename;
        this.created = created;
        this.thumbnail = thumbnail;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
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
