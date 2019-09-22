package rubenquadros.com.rupeek.custom

import retrofit2.Call
import retrofit2.http.GET
import rubenquadros.com.rupeek.model.Data

interface GetImages {

    @GET("5c2443f530000054007a5f3e")
    fun getAllImages(): Call<Data>

}