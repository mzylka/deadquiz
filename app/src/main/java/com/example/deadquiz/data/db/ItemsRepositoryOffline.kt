package com.example.deadquiz.data.db

import kotlinx.coroutines.flow.Flow

class ItemsRepositoryOffline(private val itemDAO: ItemDAO): ItemsRepository {
    override suspend fun getAllItems(): List<Item> = itemDAO.getAllItems()
    override fun getItemStream(id: Int): Flow<Item?> = itemDAO.getItem(id)
    override suspend fun insertItem(item: Item) = itemDAO.insert(item)
    override suspend fun insertItems(items: List<Item>) = itemDAO.insertAll(items)
    override suspend fun hasItems(): Boolean = itemDAO.hasItems()
}