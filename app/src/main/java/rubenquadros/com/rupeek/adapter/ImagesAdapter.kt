package rubenquadros.com.rupeek.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import rubenquadros.com.rupeek.R
import rubenquadros.com.rupeek.model.Data
import java.lang.Exception

@SuppressLint("RecyclerView")
class ImagesAdapter(private val images: Data?): RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.images_layout, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if(images == null) {
            0
        }else{
            images.data!!.size
        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        if(images != null) {
            Picasso.get().load(images.data!![p1].url)
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .into(p0.mImageView, object : Callback {
                    override fun onSuccess() {

                    }

                    override fun onError(e: Exception?) {
                        Picasso.get().load(images.data!![p1].url)
                            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                            .networkPolicy(NetworkPolicy.OFFLINE)
                            .into(p0.mImageView, object : Callback {
                                override fun onSuccess() {

                                }

                                override fun onError(e: Exception?) {
                                    //no images in db
                                }
                            })
                    }

                })
            p0.mTextView?.text = images.data!![p1].place
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val mImageView: ImageView? = itemView.findViewById(R.id.imageView)
        val mTextView: TextView? = itemView.findViewById(R.id.textView)
    }

}