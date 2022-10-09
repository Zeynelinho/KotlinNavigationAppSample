package com.zeynelinho.proje4.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zeynelinho.proje4.model.Place


@Database(entities = [Place::class], version = 1)
abstract class PlaceDatabase : RoomDatabase() {
    abstract fun placeDao() : PlaceDao

}