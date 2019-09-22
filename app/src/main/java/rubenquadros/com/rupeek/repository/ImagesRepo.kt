package rubenquadros.com.rupeek.repository

import android.arch.lifecycle.MutableLiveData
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rubenquadros.com.rupeek.utility.callbacks.DBCallback
import rubenquadros.com.rupeek.utility.callbacks.ImageCallback
import rubenquadros.com.rupeek.custom.GetImages
import rubenquadros.com.rupeek.custom.RetrofitInstance
import rubenquadros.com.rupeek.localDatabase.ImageDAO
import rubenquadros.com.rupeek.localDatabase.ImageData
import rubenquadros.com.rupeek.localDatabase.ImageDatabase
import rubenquadros.com.rupeek.model.Data

class ImagesRepo: Callback<Data> {

    private lateinit var instance: ImagesRepo
    private lateinit var retrofitInstance: GetImages
    private lateinit var mListener: ImageCallback
    private lateinit var imageDAO: ImageDAO
    private var dataSet: Data? = null
    private val data: MutableLiveData<Data> = MutableLiveData()
    private var images: List<ImageData> = ArrayList()
    private var localData: MutableLiveData<List<ImageData>> = MutableLiveData()

    fun getInstance(): ImagesRepo {
        synchronized(this) {
            instance = ImagesRepo()
        }
        return instance
    }

    fun getImages(): MutableLiveData<Data> {
        setImages()
        mListener = object : ImageCallback {
            override fun onResponseReceived(response: Data?) {
                data.value = response
            }
        }
        return data
    }

    private fun setImages() {
        retrofitInstance = RetrofitInstance().getRetrofitInstance().create(GetImages::class.java)
        val getImages = retrofitInstance.getAllImages()
        getImages.enqueue(this)
    }

    override fun onFailure(call: Call<Data>, t: Throwable) {
        dataSet = null
        mListener.onResponseReceived(dataSet)
    }

    override fun onResponse(call: Call<Data>, response: Response<Data>) {
        dataSet = response.body()
        mListener.onResponseReceived(dataSet)
    }

    fun initDB(instance: ImageDatabase) {
        imageDAO = instance.imageDAO()
    }

    fun insertAllImages(images: ArrayList<ImageData>) {
        doAsync {
            imageDAO.insertAll(images)
        }
    }

    fun getLocalImages(): MutableLiveData<List<ImageData>> {
        setLocalImages(object : DBCallback {
            override fun onQueryExecuted(localImageData: List<ImageData>?) {
                localData.value = localImageData
            }
        })
        return localData
    }

    private fun setLocalImages(dbCallback: DBCallback) {
        doAsync {
            images = imageDAO.getAllImages()
            uiThread { dbCallback.onQueryExecuted(images) }
        }
    }

    fun deleteAllImages() {

    }
}