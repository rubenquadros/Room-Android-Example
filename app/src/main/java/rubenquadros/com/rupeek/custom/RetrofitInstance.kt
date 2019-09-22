package rubenquadros.com.rupeek.custom

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rubenquadros.com.rupeek.utility.application.ApplicationConstants

class RetrofitInstance {

    private var retrofit: Retrofit? = null

    fun getRetrofitInstance(): Retrofit {
        if (retrofit == null) {
            retrofit = retrofit2.Retrofit.Builder().baseUrl(ApplicationConstants.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit!!
    }
}