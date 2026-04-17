package com.example.deadquiz.data

import com.example.deadquiz.data.db.Item
import kotlinx.serialization.Serializable

@Serializable
data class DeadlockItem(
    val name: String,
    val cost: Int? = null,
    val shopable: Boolean = false,
    val description: Description? = null,
)

@Serializable
data class Description(
    val desc: String? = null
)

fun DeadlockItem.toItem(): Item {
    return Item(
        name = name,
        cost = cost ?: 0,
        description = description?.desc ?: ""
    )
}