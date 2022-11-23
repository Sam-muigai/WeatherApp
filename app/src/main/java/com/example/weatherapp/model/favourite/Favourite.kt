package com.example.weatherapp.model.favourite

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "fav_tbl" )
data class Favourite(
    @PrimaryKey
    @ColumnInfo(name = "city")
    val city:String,
    @ColumnInfo(name = "country")
    val country:String
)