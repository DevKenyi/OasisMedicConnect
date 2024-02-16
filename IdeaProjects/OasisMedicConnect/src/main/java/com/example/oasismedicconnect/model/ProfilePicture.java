package com.example.oasismedicconnect.model;

import jakarta.persistence.Entity;
@Entity
public class ProfilePicture extends Profile {
    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public String getImageUrl() {
        return super.getImageUrl();
    }


    @Override
    public void setImageUrl(String imageUrl) {
        super.setImageUrl(imageUrl);
    }
}
