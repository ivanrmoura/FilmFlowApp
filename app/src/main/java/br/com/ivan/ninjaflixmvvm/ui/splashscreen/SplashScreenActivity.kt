package br.com.ivan.ninjaflixmvvm.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import br.com.ivan.ninjaflixmvvm.R
import br.com.ivan.ninjaflixmvvm.databinding.ActivitySplashScreenBinding
import br.com.ivan.ninjaflixmvvm.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)

    }
}