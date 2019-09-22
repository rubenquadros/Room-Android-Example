package rubenquadros.com.rupeek.localDatabase

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context


@Database(entities = [ImageData::class], version = 1)
abstract class ImageDatabase: RoomDatabase() {

    abstract fun imageDAO(): ImageDAO

    companion object {
        private lateinit var instance: ImageDatabase

        fun getInstance(context: Context): ImageDatabase {
            synchronized(this) {
                instance = Room.databaseBuilder(context.applicationContext, ImageDatabase::class.java, "image_database").fallbackToDestructiveMigration().build()
            }
            return instance
        }
    }
}