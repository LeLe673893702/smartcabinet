package com.llc.smartcabinet.data.service;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.llc.smartcabinet.App;
import com.llc.smartcabinet.constants.Constants;
import com.llc.smartcabinet.data.dao.FaceDao;
import com.llc.smartcabinet.data.model.Face;

/**
 * @author 17173
 * @what
 * @date 2020/1/16
 */
@Database(entities = {Face.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FaceDao userDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance() {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room
                            .databaseBuilder(App.getContext(), AppDatabase.class, Constants.SMART_CABINET_DB)
                            .build();
                }
            }
        }
        return instance;
    }
}
