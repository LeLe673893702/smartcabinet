package com.llc.smartcabinet.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.llc.smartcabinet.data.model.Face;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import retrofit2.http.DELETE;

/**
 * @author 17173
 * @what
 * @date 2020/1/16
 */
@Dao
public interface FaceDao {
    @Query("SELECT * From face")
    List<Face> getAllFaces();

    @Query("SELECT * From face WHERE groupId = :groupId")
    Flowable<List<Face>> getFacesByGroupId(String groupId);

    @Delete
    void deleteFace(Face... faces);

    @Insert
    void insertAll(Face... faces);
}
