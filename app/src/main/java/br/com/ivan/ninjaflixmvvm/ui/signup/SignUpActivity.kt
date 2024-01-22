package br.com.ivan.ninjaflixmvvm.ui.signup

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.ivan.ninjaflixmvvm.R
import br.com.ivan.ninjaflixmvvm.databinding.ActivitySignupBinding
import br.com.ivan.ninjaflixmvvm.domain.model.User
import br.com.ivan.ninjaflixmvvm.utils.Constants.Companion.EMAIL_ALREADY_EXISTS
import br.com.ivan.ninjaflixmvvm.utils.NetworkResult
import br.com.ivan.ninjaflixmvvm.utils.isInputEmpty
import br.com.ivan.ninjaflixmvvm.utils.toast
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private val LOG = "SignUpActivity"

    private lateinit var binding: ActivitySignupBinding

    private val signUpViewModel: SignUpViewModel by viewModels()

    private lateinit var uriImagem: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObservers()
        initListener()
        uriImagem = Uri.EMPTY
    }

    private fun initObservers() {
        signUpViewModel.signUpState.observe(this, Observer { result ->
            when (result) {
                is NetworkResult.Sucess -> {
                    if (result.data?.id?.isNotEmpty() == true) {
                        val userCreated = result.data
                        signUpViewModel.saveUser(userCreated, uriImagem)
                    } else {
                        Log.d(LOG, "Erro ao cadastrar usuário")
                    }
                    binding.progressSignup.visibility = View.GONE
                }

                is NetworkResult.Error -> {
                    Log.d(LOG, "Erro de conexão")
                    binding.progressSignup.visibility = View.GONE
                    manageRegisterErrorMessages(result.message!!)
                }

                is NetworkResult.Loading -> {
                    binding.progressSignup.visibility = View.VISIBLE
                }

                else -> Unit
            }
        })
    }
        //save user in firestore

    private fun initListener(){
        binding.signupBtn.setOnClickListener {
            if (isUserDataOk()){
                signUpViewModel.signUp(createUser(), binding.signupPassword.text.toString())
            }
        }

        binding.fabSelectPhoto.setOnClickListener {
            abrirGaleriaFotos.launch("image/*")
        }

    }

    private val abrirGaleriaFotos = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            binding.signupImageUser.setImageURI(uri)
            uriImagem = uri

            Glide.with(this).load(uri).fitCenter().circleCrop()
                .into(binding.signupImageUser)

        }
    }



    private fun createUser() = User(
        name = binding.signupName.text.toString(),
        email = binding.signupEmail.text.toString()
    )

    private fun isUserDataOk() : Boolean{
        return when {

            isInputEmpty(binding.signupEmail, true) -> false

            isPasswordInsecure() -> {
                toast(getString(R.string.signup__error_passwords_match))
                false
            }

            else -> true

        }
    }

    private fun isPasswordInsecure(): Boolean{

        return if (binding.signupPassword.text.toString().length <= 6){
            toast(getString(R.string.signup__error_password_insecure))
            true
        } else {
            binding.signupPassword.text.toString() != binding.signupConfPassword.text.toString()
        }
    }


    private fun manageRegisterErrorMessages(exception: String) {
        if (exception == EMAIL_ALREADY_EXISTS) {
            toast(getString(R.string.signup__error_email_already_registered))
        } else {
            toast(getString(R.string.signup__error_unknown_error))
        }
    }

}