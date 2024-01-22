package br.com.ivan.ninjaflixmvvm.domain.model

import br.com.ivan.ninjaflixmvvm.utils.Constants
import br.com.ivan.ninjaflixmvvm.utils.Constants.Companion.INFO_NOT_SET

data class User(

    var id: String= INFO_NOT_SET,
    val name: String= INFO_NOT_SET,
    val email: String= INFO_NOT_SET,
    var pathImage: String = INFO_NOT_SET
)
