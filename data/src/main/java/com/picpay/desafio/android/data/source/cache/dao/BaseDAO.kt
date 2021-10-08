package com.picpay.desafio.android.data.source.cache.dao

import androidx.room.*
import com.picpay.desafio.android.data.source.cache.DatabaseConstants

@Dao
abstract class BaseDAO<in Entity> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(entity: Entity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(entities: List<Entity>): List<Long>

    @Update
    abstract fun update(entity: Entity)

    @Update
    abstract fun update(entity: List<Entity>)

    @Delete
    abstract fun delete(entity: Entity)

    @Transaction
    open fun upsert(entity: Entity) {
        val result = insert(entity)
        if (result == DatabaseConstants.ROW_NOT_INSERTED) update(entity)
    }

    @Transaction
    open fun upsert(entities: List<Entity>) {
        val insertResult = insert(entities)
        val updateList = mutableListOf<Entity>()

        insertResult.forEachIndexed { index, result ->
            if (result == DatabaseConstants.ROW_NOT_INSERTED) updateList.add(entities[index])
        }

        if (updateList.isNotEmpty()) update(updateList)
    }
}
