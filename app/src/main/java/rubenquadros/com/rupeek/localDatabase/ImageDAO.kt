package rubenquadros.com.rupeek.localDatabase

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface ImageDAO {

    @Insert
    fun insertAll(data: ArrayList<ImageData>)

    @Query("SELECT * FROM image_data")
    fun getAllImages(): List<ImageData>

    @Query("DELETE FROM image_data")
    fun deleteAllImages()

}