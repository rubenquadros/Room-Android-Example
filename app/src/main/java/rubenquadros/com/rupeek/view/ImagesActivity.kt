package rubenquadros.com.rupeek.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import rubenquadros.com.rupeek.R
import rubenquadros.com.rupeek.adapter.ImagesAdapter
import rubenquadros.com.rupeek.localDatabase.ImageData
import rubenquadros.com.rupeek.localDatabase.ImageDatabase
import rubenquadros.com.rupeek.model.Data
import rubenquadros.com.rupeek.model.EachData
import rubenquadros.com.rupeek.viewmodel.ImagesViewModel


class ImagesActivity : AppCompatActivity() {

    private lateinit var imagesViewModel: ImagesViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var imagesAdapter: ImagesAdapter
    private lateinit var dbInstance: ImageDatabase
    private var mList: ArrayList<EachData> = ArrayList()
    private lateinit var mView: View
    private lateinit var noNet: LinearLayout
    private lateinit var swipeView: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)
        init()
        initData()
        initRecyclerView()
        initSwipe()
    }

    private fun init() {
        dbInstance = ImageDatabase.getInstance(this)
        imagesViewModel = ViewModelProviders.of(this).get(ImagesViewModel::class.java)
        mView = findViewById(R.id.parent)
        noNet = findViewById(R.id.noNetwork)
    }

    private fun initData() {
        imagesViewModel.init()
        imagesViewModel.getImages().observe(this,
            Observer<Data> { t ->
                if(t!=null) {
                    updateAdapter(t)
                    imagesViewModel.saveImagesLocally(dbInstance, t)
                }else {
                    getLocalData()
                }
            })
    }

    private fun initRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = GridLayoutManager(this, 2)
        mRecyclerView.layoutManager = layoutManager
        imagesAdapter = ImagesAdapter(imagesViewModel.getImages().value)
        mRecyclerView.adapter = imagesAdapter
    }

    private fun updateAdapter(data: Data?) {
        noNet.visibility = View.GONE
        mRecyclerView.visibility = View.VISIBLE
        swipeView.isRefreshing = false
        imagesAdapter = ImagesAdapter(data)
        mRecyclerView.adapter = imagesAdapter
    }

    private fun getLocalData() {
        imagesViewModel.initLocal(dbInstance)
        imagesViewModel.getAllLocalImages().observe(this,
            Observer<List<ImageData>> { t ->
                if(t!=null && t.isNotEmpty()) {
                    updateAdapterWithLocalData(t)
                }else{
                    swipeView.isRefreshing = false
                    mRecyclerView.visibility = View.GONE
                    noNet.visibility = View.VISIBLE
                }
            })
    }

    private fun updateAdapterWithLocalData(images: List<ImageData>) {
        noNet.visibility = View.GONE
        mRecyclerView.visibility = View.VISIBLE
        swipeView.isRefreshing = false
        for(i in 0 until images.size) {
            val eachData = EachData()
            eachData.url = images[i].url
            eachData.place = images[i].name
            mList.add(eachData)
        }
        val mData = Data()
        mData.data = mList
        imagesAdapter = ImagesAdapter(mData)
        mRecyclerView.adapter = imagesAdapter
    }

    private fun initSwipe() {
        swipeView = findViewById(R.id.refresh)
        swipeView.setOnRefreshListener {
            initData()
        }
    }
}
