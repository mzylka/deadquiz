package com.example.deadquiz.data.db

import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    suspend fun getAllItems(): List<Item>
    fun getItemStream(id: Int): Flow<Item?>
    suspend fun insertItem(item: Item)
    suspend fun insertItems(items: List<Item>)
    suspend fun hasItems(): Boolean
}