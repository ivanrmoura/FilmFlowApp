package br.com.ivan.ninjaflixmvvm.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.ivan.ninjaflixmvvm.R
import br.com.ivan.ninjaflixmvvm.data.entities.Filme
import br.com.ivan.ninjaflixmvvm.databinding.ItemFilmeBinding
import br.com.ivan.ninjaflixmvvm.utils.Constants
import coil.load
import coil.transform.RoundedCornersTransformation
import javax.inject.Inject

class FilmeListAdapter @Inject constructor(
    private val onFilmeClick: (Filme) -> Unit
) :
    PagingDataAdapter<Filme, FilmeListAdapter.ImageViewHolder>(diffCallback) {


    inner class ImageViewHolder(val binding: ItemFilmeBinding) :
        ViewHolder(binding.root) {
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Filme>() {
            override fun areItemsTheSame(oldItem: Filme, newItem: Filme): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Filme, newItem: Filme): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemFilmeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentFilme = getItem(position)

        holder.binding.filmeItem.setOnClickListener{onFilmeClick(currentFilme!!)}

        holder.binding.apply {
            holder.itemView.apply {

                val urlImage = "${Constants.URL_BASE_IMAGE}${currentFilme?.posterPath}"
                tituloFilme.text = currentFilme?.title
                imagemFilme.load(urlImage){
                    transformations(RoundedCornersTransformation(8f))
                    crossfade(true)
                    crossfade(100)

                }
                rating.text = "${String.format("%.1f", currentFilme?.voteAverage).replace(",", ".")}/10"
            }
        }
    }
}