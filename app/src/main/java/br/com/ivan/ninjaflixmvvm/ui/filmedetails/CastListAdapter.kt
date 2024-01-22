package br.com.ivan.ninjaflixmvvm.ui.filmedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.ivan.ninjaflixmvvm.data.entities.Cast
import br.com.ivan.ninjaflixmvvm.databinding.ItemCastBinding
import br.com.ivan.ninjaflixmvvm.utils.Constants
import coil.load
import coil.transform.RoundedCornersTransformation
import com.bumptech.glide.Glide
import java.util.ArrayList

class CastListAdapter(
    private val onClickedGenres: (cast: Cast) -> Unit
): RecyclerView.Adapter<CastListAdapter.CastViewHolder>(){

    private val items = ArrayList<Cast>()

    fun setItems(items: ArrayList<Cast>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastViewHolder(binding, onClickedGenres)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        return holder.bind(items[position])
    }

    class CastViewHolder(
        private val itemBinding: ItemCastBinding,
        private val onClickedGenres: (cast: Cast) -> Unit
    ): RecyclerView.ViewHolder(itemBinding.root){
        private lateinit var cast: Cast

        init {
            itemBinding.root.setOnClickListener{
                onClickedGenres(cast)}
        }

        fun bind(item: Cast){
            itemBinding.nomeCast.text = item.name
            val urlImage = "${Constants.URL_BASE_IMAGE}${item.profilePath}"
            itemBinding.imagemCast.load(urlImage){
                transformations(RoundedCornersTransformation(8f))
                crossfade(true)
                crossfade(100)
            }

            this.cast = item
        }

    }

}

