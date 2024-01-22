package br.com.ivan.ninjaflixmvvm.ui.filmedetails

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.ivan.ninjaflixmvvm.R
import br.com.ivan.ninjaflixmvvm.data.entities.Cast
import br.com.ivan.ninjaflixmvvm.data.entities.Filme
import br.com.ivan.ninjaflixmvvm.data.entities.FilmeDetail
import br.com.ivan.ninjaflixmvvm.data.entities.Genres
import br.com.ivan.ninjaflixmvvm.data.entities.Video
import br.com.ivan.ninjaflixmvvm.databinding.ActivityFilmeDetailsBinding
import br.com.ivan.ninjaflixmvvm.ui.home.GenresListAdapter
import br.com.ivan.ninjaflixmvvm.utils.Constants
import br.com.ivan.ninjaflixmvvm.utils.NetworkResult
import br.com.ivan.ninjaflixmvvm.utils.toast
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FilmeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilmeDetailsBinding
    private val filmeDetainsModel: FilmeDetailsViewModel by viewModels()

    private lateinit var castListAdapter: CastListAdapter
    private lateinit var videoListAdapter: VideoListAdapter

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmeDetailsBinding.inflate(layoutInflater)

        val filme = intent.getParcelableExtra("filme", Filme::class.java)!!
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        setupRecycleviews()
        observersSetup()
        loadData(filme.id)
    }

    fun loadData(idFilme: Int){
        filmeDetainsModel.getFilmeDetails(idFilme)
        filmeDetainsModel.getVideos(idFilme)
        filmeDetainsModel.getCredits(idFilme)
    }

    fun setupRecycleviews(){
        castListAdapter = CastListAdapter { cast -> onClickedCast(cast) }
        binding.recyclerviewCast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerviewCast.adapter = castListAdapter


        videoListAdapter = VideoListAdapter { video -> onClickedVideo(video) }
        binding.recyclerviewVideos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerviewVideos.adapter = videoListAdapter
    }

    fun onClickedVideo(video: Video){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data =
            Uri.parse(Constants.YOUTUBE_VIDEO_URL + video.key)
        startActivity(intent)
    }

    fun onClickedCast(cast: Cast){
        toast(cast.name)
    }

    private fun observersSetup(){
        filmeDetainsModel.filmeDetailsState.observe(this, Observer { result ->
            when(result){
                is NetworkResult.Sucess ->{
                    setupInfoFilme(result.data!!)
                }
                is NetworkResult.Error ->{
                    toast("Erro ao carregar os detalhes do filme: ${result.message}")
                }
                else -> Unit
            }
        })

        filmeDetainsModel.videosState.observe(this, Observer { result ->
            when(result){
                is NetworkResult.Sucess -> {
                    val videos = result.data?.results!!
                    trailerSetup(videos)
                    videoListAdapter.setItems(ArrayList(videos))
                }
                is NetworkResult.Error -> {
                    toast("Erro ao carregar os vídeos do filme ${result.message}")
                }
                else -> Unit
            }
        })

        filmeDetainsModel.creditsState.observe(this, Observer { result ->
            when(result){
                is NetworkResult.Sucess -> {
                    castListAdapter.setItems(ArrayList(result.data?.cast!!))
                }
                is NetworkResult.Error -> {
                    toast("Erro ao carregar os créditos do filme ${result.message}")
                }
                else -> Unit
            }
        })

    }


    private fun trailerSetup(videos: List<Video>){
        val trailer = videos.first { it.type == "Trailer" }

        val listener = object : AbstractYouTubePlayerListener() {
            override fun onReady( youTubePlayer: YouTubePlayer) {
                val videoId = trailer.key
                youTubePlayer.cueVideo(videoId, 0f)

            }
        }
        binding.playerTrailer.addYouTubePlayerListener(listener)
        lifecycle.addObserver(binding.playerTrailer)
    }

    @SuppressLint("SetTextI18n")
    private fun setupInfoFilme(filmeDetails: FilmeDetail) {
        val urlBanner = "${Constants.URL_BASE_IMAGE}${filmeDetails.backdropPath}"
        val urlPoster = "${Constants.URL_BASE_IMAGE}${filmeDetails.posterPath}"

        Glide.with(this).load(urlBanner).centerCrop()
            .into(binding.bannerFilme)

        binding.toolbar.title = filmeDetails.title

        Glide.with(this).load(urlPoster).centerCrop()
            .into(binding.posterImage)

        binding.rating.text = "${String.format("%.1f", filmeDetails.voteAverage).replace(",", ".")}/10"

        //binding.title.text = filmeDetails.title
        binding.overview.text = filmeDetails.overview
        binding.genres.text = buildGenresString(filmeDetails.genres)

        val urlProdutora = "${Constants.URL_BASE_IMAGE}${filmeDetails.productionCompanies[0].logoPath}"
        Glide.with(this).load(urlProdutora)
            .error(R.drawable.imagem_indisponivel)
            .into(binding.produtora)


        binding.produtoraName.text = filmeDetails.productionCompanies[0].name
    }

    private fun buildGenresString(genres: List<Genres>): String{
        var genresString = ""
        if (genres.size == 1) {
            return genres[0].name
        }else if (genres.size > 1){
            for (gen in genres){
                genresString = "$genresString#${gen.name} "
            }
        }
        return genresString

    }


    private fun configTopBarMenuClick(){
        binding.toolbar.setOnMenuItemClickListener { menuItem ->

            when(menuItem.itemId){
                R.id.favorite -> {
                    Toast.makeText(this, "favorite clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.more -> {
                    Toast.makeText(this, "more clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }

        }
    }

}