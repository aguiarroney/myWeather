package com.example.capstone.repository

import com.example.capstone.local.LocationDatabaseEntities
import com.example.capstone.local.LocationRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomRepository(private val database: LocationRoomDatabase) : Repository() {

    suspend fun insertLocationWeather(locationWeatherModelEntity: LocationDatabaseEntities) {
        withContext(Dispatchers.IO) {
            database.locationDAO.insert(locationWeatherModelEntity)
        }
    }

    suspend fun getAllWeather(): List<LocationDatabaseEntities> {
        return withContext(Dispatchers.IO) {
            database.locationDAO.getAll()
        }
    }

}