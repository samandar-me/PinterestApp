package uz.context.pinterestapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SavedImage")
data class SaveImage(

    @PrimaryKey()
    val imageId: String,
    val url: String,
    val title: String
)