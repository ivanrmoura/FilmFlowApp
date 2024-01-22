package br.com.ivan.ninjaflixmvvm.ui.filmedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.ivan.ninjaflixmvvm.data.entities.Cast
import br.com.ivan.ninjaflixmvvm.data.entities.Video
import br.com.ivan.ninjaflixmvvm.databinding.ItemCastBinding
import br.com.ivan.ninjaflixmvvm.databinding.ItemVideoBinding
import br.com.ivan.ninjaflixmvvm.utils.Constants
import coil.load
import coil.transform.RoundedCornersTransformation

class VideoListAdapter(
    private val onClickedVideo: (video: Video) -> Unit
): RecyclerView.Adapter<VideoListAdapter.ViewHolder>(){

    private val items = ArrayList<Video>()

    fun setItems(items: ArrayList<Video>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClickedVideo)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(items[position])
    }

    class ViewHolder(
        private val itemBinding: ItemVideoBinding,
        private val onClickedVideo: (video: Video) -> Unit
    ): RecyclerView.ViewHolder(itemBinding.root){
        private lateinit var video: Video

        init {
            itemBinding.root.setOnClickListener{
                onClickedVideo(video)}
        }

        fun bind(item: Video){
            itemBinding.nomeVideo.text = item.name
            val urlImage = "${Constants.YOUTUBE_THUMBNAIL_URL}${item.key}${Constants.YOUTUBE_THUMBNAIL_URL_ENDPOINT}"
            itemBinding.imagemVideo.load(urlImage){
                transformations(RoundedCornersTransformation(8f))
                crossfade(true)
                crossfade(100)
            }

            this.video = item
        }

    }

}

