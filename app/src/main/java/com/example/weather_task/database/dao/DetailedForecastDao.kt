package com.example.weather_task.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weather_task.database.entity.detailed_forecast.DetailedForecastEntity

@Dao
interface DetailedForecastDao {
    @Query("SELECT * FROM detailed_forecast_table")
    suspend fun getAll(): List<DetailedForecastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(detailedForecast: List<DetailedForecastEntity>)

    @Query("DELETE FROM detailed_forecast_table")
    suspend fun deleteAll()
}
