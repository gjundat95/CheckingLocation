package com.jundat95.tinhngo.sharelocation.repository.local.model;

/**
 * Created by tinhngo on 10/26/17.
 */

public class ImageModel {

    private String id;
    private String idAttend;
    private String image;

    public ImageModel(String id, String idAttend, String image) {
        this.id = id;
        this.idAttend = idAttend;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdAttend() {
        return idAttend;
    }

    public void setIdAttend(String idAttend) {
        this.idAttend = idAttend;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
