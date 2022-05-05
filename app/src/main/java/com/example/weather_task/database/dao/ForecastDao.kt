package com.example.weather_task.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weather_task.database.entity.forecast.ForecastEntity

@Dao
interface ForecastDao {
    @Query("SELECT * FROM forecast_table")
    suspend fun getAll(): List<ForecastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forecasts: ForecastEntity)

    @Query("DELETE FROM forecast_table")
    suspend fun deleteAll()
}
