package com.example.deadquiz.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Insert
    suspend fun insertAll(items: List<Item>)

    @Query("SELECT * from items")
    suspend fun getAllItems() : List<Item>

    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int) : Flow<Item>

    @Query("SELECT EXISTS(SELECT 1 FROM items)")
    suspend fun hasItems(): Boolean
}