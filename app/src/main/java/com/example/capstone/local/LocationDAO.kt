package com.example.capstone.local

import android.content.Context
import androidx.room.*

@Dao
interface LocationDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(locationWeatherModelEntity: LocationDatabaseEntities)

    @Query("SELECT * FROM favourite_weathers")
    fun getAll(): List<LocationDatabaseEntities>
}

@Database(entities = [LocationDatabaseEntities::class], version = 3, exportSchema = false)
abstract class LocationRoomDatabase : RoomDatabase() {
    abstract val locationDAO: LocationDAO
}

private lateinit var INSTANCE: LocationRoomDatabase

fun getDatabase(context: Context): LocationRoomDatabase {

    synchronized(LocationRoomDatabase::class.java) {

        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                LocationRoomDatabase::class.java,
                "favourite_weathers"
            ).build()
        }
    }

    return INSTANCE
}