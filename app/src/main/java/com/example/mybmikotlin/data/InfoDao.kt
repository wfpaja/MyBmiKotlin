package com.example.mybmikotlin.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface InfoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(info: Info)

    @Query("DELETE FROM info")
    suspend fun deleteAll()

    @Query("DELETE FROM info WHERE id IN (:idList)")
    suspend fun deleteByIds(idList: List<Long>)

    @Query("SELECT * FROM info WHERE bmi >= 18.5 AND bmi <24")
    fun getNormalList(): Flow<List<Info>>

    @Query("SELECT * FROM info WHERE bmi <18.5")
    fun getThinList(): Flow<List<Info>>

    @Query("SELECT * FROM info WHERE bmi >=24")
    fun getFatList(): Flow<List<Info>>
}