package br.com.ivan.ninjaflixmvvm.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.ivan.ninjaflixmvvm.data.entities.Genres
import br.com.ivan.ninjaflixmvvm.databinding.ItemGenresBinding
import java.util.ArrayList

class GenresListAdapter(
    private val onClickedGenres: (genres: Genres) -> Unit
): RecyclerView.Adapter<GenresListAdapter.GenresViewHolder>(){

    private val items = ArrayList<Genres>()

    fun setItems(items: ArrayList<Genres>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val binding = ItemGenresBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenresViewHolder(binding, onClickedGenres)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        return holder.bind(items[position])
    }

    class GenresViewHolder(
        private val itemBinding: ItemGenresBinding,
        private val onClickedGenres: (genres: Genres) -> Unit
    ): RecyclerView.ViewHolder(itemBinding.root){
        private lateinit var genres: Genres

        init {
            itemBinding.btnGenres.setOnClickListener{
                onClickedGenres(genres)}
        }

        fun bind(item: Genres){
            itemBinding.btnGenres.text = item.name
            this.genres = item
        }

    }

}

