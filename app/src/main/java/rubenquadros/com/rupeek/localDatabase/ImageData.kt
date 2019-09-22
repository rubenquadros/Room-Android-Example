package rubenquadros.com.rupeek.localDatabase

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "image_data")
data class ImageData(
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "image_url")
    var url: String?,
    @ColumnInfo(name = "image_name")
    var name: String?
)