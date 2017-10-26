package com.jundat95.tinhngo.sharelocation.repository.local.model;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by tinhngo on 10/26/17.
 */

public class AttendModel {

    private String id;
    private String title;
    private String description;
    private String locationName;
    private String time;
    private String phoneNumber;
    private String userId;
    private String type;
    private LatLng latLng;
    private List<Bitmap> imageSrc;

    public AttendModel(String id, String title, String description, String locationName, String time, String phoneNumber, String userId, String type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.locationName = locationName;
        this.time = time;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.type = type;
    }

    public AttendModel(String id, String title, String description, String locationName, String time, String phoneNumber, String userId, String type, LatLng latLng, List<Bitmap> imageSrc) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.locationName = locationName;
        this.time = time;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.type = type;
        this.latLng = latLng;
        this.imageSrc = imageSrc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public List<Bitmap> getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(List<Bitmap> imageSrc) {
        this.imageSrc = imageSrc;
    }

}
