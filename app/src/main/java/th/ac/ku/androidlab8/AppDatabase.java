package th.ac.ku.androidlab8;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import th.ac.ku.androidlab8.models.Person;
import th.ac.ku.androidlab8.models.PersonDao;

/**
 * Created by MegapiesPT on 22/11/2560.
 */

@Database(entities = {Person.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PersonDao personDao();
}
