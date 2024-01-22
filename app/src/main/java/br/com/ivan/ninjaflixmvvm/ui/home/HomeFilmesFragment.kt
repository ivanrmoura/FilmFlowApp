package br.com.ivan.ninjaflixmvvm.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.ivan.ninjaflixmvvm.data.entities.Filme
import br.com.ivan.ninjaflixmvvm.data.entities.Genres
import br.com.ivan.ninjaflixmvvm.databinding.FragmentHomeFilmesBinding
import br.com.ivan.ninjaflixmvvm.ui.filmedetails.FilmeDetailsActivity
import br.com.ivan.ninjaflixmvvm.utils.Constants
import br.com.ivan.ninjaflixmvvm.utils.NetworkResult
import br.com.ivan.ninjaflixmvvm.utils.autoCleared
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFilmesFragment : Fragment() {

    private var binding: FragmentHomeFilmesBinding by autoCleared()
    private val viewModel: HomeFilmesViewModel by viewModels()

    private val genresViewModel: GenresViewModel by viewModels()

    private lateinit var popularFilmesAdapter: FilmeListAdapter
    private lateinit var topRatedFilmesAdapter: FilmeListAdapter
    private lateinit var genresAdapter: GenresListAdapter

    private var firstTime = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeFilmesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerviews()
        setupObservers()
        loadData()
        configStateListener()
    }

    private fun setupObservers(){
        genresViewModel.getGenresState.observe(viewLifecycleOwner, Observer { result ->
                when(result){
                    is NetworkResult.Sucess ->{
                        result.data?.genres?.let { genresAdapter.setItems(ArrayList(it)) }
                    }
                    is NetworkResult.Error -> {
                        Toast.makeText(requireContext(), "Erro ao carregar os gêneros", Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
        })

        viewModel.upcomingFilmesState.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is NetworkResult.Sucess ->{
                    result.data?.results?.let { configSlider(it) }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), "Erro ao carregar os gêneros", Toast.LENGTH_SHORT).show()
                }
                else -> Unit
            }
        })
    }

    private fun configSlider(filmes: List<Filme>){

        val imageList = ArrayList<SlideModel>() // Create image list
        val filmeList = filmes.subList(0,8)
        for (filme in filmeList){
            val urlImage = "${Constants.URL_BASE_IMAGE}${filme.posterPath}"
            imageList.add(SlideModel(urlImage, filme.title))
        }

        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)

        binding.imageSlider.setItemClickListener(object : ItemClickListener{
            override fun onItemSelected(position: Int) {

            }

            override fun doubleClick(position: Int) {

            }
        })

        binding.imageSlider.startSliding(3000)
    }

    private fun setupRecyclerviews(){

        popularFilmesAdapter = FilmeListAdapter {filme ->  onClickedFilme(filme)}
        binding.recyclerviewPopularFilmes.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false )
        binding.recyclerviewPopularFilmes.adapter = popularFilmesAdapter

        topRatedFilmesAdapter = FilmeListAdapter {filme ->  onClickedFilme(filme)}
        binding.recyclerviewTopRatedFilmes.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false )
        binding.recyclerviewTopRatedFilmes.adapter = topRatedFilmesAdapter

        genresAdapter = GenresListAdapter{genres -> onClickedGenres(genres)}
        binding.genresRecyclerview.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.HORIZONTAL, false)
        binding.genresRecyclerview.adapter = genresAdapter

    }

    fun onClickedGenres(genres: Genres) {
        Toast.makeText(requireContext(), genres.name, Toast.LENGTH_SHORT).show()
    }

    fun onClickedFilme(filme: Filme) {
        navToFilmeDetails(filme)
    }

    private fun loadData(){

        viewModel.getUpcomingFilmes()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.popularFilmesListData.collectLatest {
                    popularFilmesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.topRatedFilmesListData.collectLatest {
                    topRatedFilmesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                }
            }
        }

        genresViewModel.getGenres()
    }

    private fun configStateListener(){
        setupLoagingStateListener(popularFilmesAdapter,
            binding.progressPopularFilmes, binding.progressPopularFilmesPaging)

        setupLoagingStateListener(topRatedFilmesAdapter,
            binding.progressTopRatedFilmes, binding.progressTopRatedFilmesPaging)

    }

    private fun setupLoagingStateListener(adapter: FilmeListAdapter, progressFilmes: ProgressBar, progressPaging: ProgressBar){
        adapter.addLoadStateListener { state ->
            if (state.refresh == LoadState.Loading || state.append == LoadState.Loading){
                if (firstTime){
                    progressFilmes.visibility = View.VISIBLE
                    progressPaging.isVisible = false
                    firstTime = false
                }else {
                    progressFilmes.visibility = View.INVISIBLE
                    progressPaging.isVisible = true
                }
            }else{
                progressFilmes.visibility = View.INVISIBLE
                progressPaging.isVisible = false
            }
        }

    }

    fun Intent.putParcelableExtra(key: String, value: Parcelable) {
        putExtra(key, value)
    }

    private fun navToFilmeDetails(filme: Filme){
        val intent = Intent(requireContext(), FilmeDetailsActivity::class.java)
        intent.putParcelableExtra("filme", filme)
        startActivity(intent)
    }

}

