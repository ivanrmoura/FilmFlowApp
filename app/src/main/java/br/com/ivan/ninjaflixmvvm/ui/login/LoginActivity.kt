package br.com.ivan.ninjaflixmvvm.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import br.com.ivan.ninjaflixmvvm.databinding.ActivityLoginBinding
import br.com.ivan.ninjaflixmvvm.ui.MainActivity
import br.com.ivan.ninjaflixmvvm.ui.signup.SignUpActivity
import br.com.ivan.ninjaflixmvvm.utils.NetworkResult
import br.com.ivan.ninjaflixmvvm.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
        initObservers()

    }

    private fun initObservers(){
        loginViewModel.logInState.observe(this, Observer {result ->
            when(result){
                is NetworkResult.Sucess -> {
                    binding.progressLogin.visibility = View.GONE
                    toast("Login realizado com sucesso")
                    openMainActivity()
                    finish()

                }
                is NetworkResult.Error -> {
                    binding.progressLogin.visibility = View.GONE
                    toast(result.message.toString())
                }
                is NetworkResult.Loading -> {
                    binding.progressLogin.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun initListeners() {

        binding.loginBtn.setOnClickListener {
            val email = binding.emailInputed.text.toString()
            val password = binding.passwordInputed.text.toString()

            loginViewModel.logIn(email, password)
        }

        binding.inscrevaseBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}