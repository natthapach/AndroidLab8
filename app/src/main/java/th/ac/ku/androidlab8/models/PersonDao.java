package th.ac.ku.androidlab8.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MegapiesPT on 22/11/2560.
 */
@Dao
public interface PersonDao {
    @Query("select * from person")
    List<Person> getPersons();

    @Insert
    void insertPerson(Person person);

    @Update
    void updatePerson(Person person);

    @Delete
    void deletePerson(Person person);
}
