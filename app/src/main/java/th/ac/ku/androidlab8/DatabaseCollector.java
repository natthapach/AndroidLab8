package th.ac.ku.androidlab8;

import android.arch.persistence.room.Room;

/**
 * Created by MegapiesPT on 22/11/2560.
 */

public class DatabaseCollector {
    private static DatabaseCollector instance;
    private AppDatabase db;
    public static DatabaseCollector getInstance(){
        if (instance == null)
            instance = new DatabaseCollector();
        return instance;
    }
    private DatabaseCollector(){
//        db = Room.databaseBuilder()
    }
}
