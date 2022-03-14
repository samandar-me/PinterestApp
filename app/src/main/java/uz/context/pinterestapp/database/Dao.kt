package uz.context.pinterestapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveImage(saveImage: SaveImage)

    @Query("SELECT * FROM SavedImage")
    fun getImages(): List<SaveImage>

    @Query("DELETE FROM SavedImage")
    fun deleteAll()
}