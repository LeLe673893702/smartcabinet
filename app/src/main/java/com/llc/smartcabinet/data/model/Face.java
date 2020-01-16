package com.llc.smartcabinet.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author newler
 * @what
 * @date 2020/1/12
 */
@Entity
public class Face {
    @PrimaryKey
    private String faceId;
    private String groupId;
    private String feature;

    public Face(String faceId, String groupId, String feature) {
        this.faceId = faceId;
        this.groupId = groupId;
        this.feature = feature;
    }

    public Face() {
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }
}
