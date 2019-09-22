package rubenquadros.com.rupeek.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import rubenquadros.com.rupeek.localDatabase.ImageData
import rubenquadros.com.rupeek.localDatabase.ImageDatabase
import rubenquadros.com.rupeek.model.Data
import rubenquadros.com.rupeek.repository.ImagesRepo

private lateinit var mRepo: ImagesRepo
private lateinit var mImages: MutableLiveData<Data>
private var allData: ArrayList<ImageData> = ArrayList()
private lateinit var mLocalImages: MutableLiveData<List<ImageData>>

class ImagesViewModel: ViewModel() {

    fun init() {
        mRepo = ImagesRepo().getInstance()
        mImages = mRepo.getImages()
    }

    fun getImages() : LiveData<Data> {
        return mImages
    }

    fun saveImagesLocally(mInstance: ImageDatabase, imageData: Data?) {
        mRepo.initDB(mInstance)
        for (i in imageData!!.data!!.indices) {
            allData.add(ImageData(i,imageData.data!![i].url, imageData.data!![i].place))
        }
        mRepo.insertAllImages(allData)
    }

    fun initLocal(mInstance: ImageDatabase) {
        mRepo.initDB(mInstance)
        mLocalImages = mRepo.getLocalImages()
    }

    fun getAllLocalImages(): LiveData<List<ImageData>> {
        return mLocalImages
    }
}