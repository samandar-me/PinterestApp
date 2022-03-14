package uz.context.pinterestapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SaveImage::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun dao(): Dao

    companion object {
        private var INSTANCE: MyDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MyDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    MyDatabase::class.java, "image.db"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }
    }
}