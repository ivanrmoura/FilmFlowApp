package br.com.ivan.ninjaflixmvvm.data.repository

import android.net.Uri
import br.com.ivan.ninjaflixmvvm.di.UsersCollection
import br.com.ivan.ninjaflixmvvm.domain.model.User
import br.com.ivan.ninjaflixmvvm.utils.NetworkResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    @UsersCollection private val userCollection: CollectionReference,
    private val firebaseStorage: FirebaseStorage
): AuthRepository{

    override suspend fun login(email: String, password: String): Flow<NetworkResult<Boolean>> {
        return flow {
            emit(NetworkResult.Loading())

            try {
               val userLogado =  firebaseAuth.signInWithEmailAndPassword(email, password).await()

                if (userLogado != null) {
                    emit(NetworkResult.Sucess(true))
                }else{
                    emit(NetworkResult.Error("Erro ao logar usuário!"))
                }
            }catch (e: Exception){
                emit(NetworkResult.Error(e.toString()))
            }

        }
    }

    override suspend fun signUp(user: User, password: String): Flow<NetworkResult<User>> {
        return flow {
            emit(NetworkResult.Loading())
            try {
                val result = firebaseAuth.createUserWithEmailAndPassword(user.email, password)
                    .await()
                user.id = result.user?.uid.toString()
                emit(NetworkResult.Sucess(user))
            }catch (e: Exception){
                emit(NetworkResult.Error(e.toString()))
            }
        }
    }

    override suspend fun logOut(): Flow<NetworkResult<Boolean>> {
        return flow {
            emit(NetworkResult.Loading())

            try {
                firebaseAuth.signOut()
                emit(NetworkResult.Sucess(true))
            }catch (e: Exception){
                emit(NetworkResult.Error(e.toString()))
            }

        }
    }

    override suspend fun saveUser(user: User, uri: Uri): Flow<NetworkResult<Boolean>> {
        return flow {
            emit(NetworkResult.Loading())
            lateinit var exp: Exception
            var uploadSucess = false
            try {

                if (uri != Uri.EMPTY) {
                    user.pathImage = uploadUserImage(uri)
                }else{
                    user.pathImage = ""
                }

                userCollection.document(user.id).set(user, SetOptions.merge())
                    .addOnSuccessListener { uploadSucess = true }
                    .addOnFailureListener { exp = it }
                    .await()

                if (uploadSucess){
                    emit(NetworkResult.Sucess(true))
                }else{
                    emit(NetworkResult.Error(exp.toString()))
                }

            }catch (e: Exception){
                emit(NetworkResult.Error(e.toString()))
            }

        }
    }


    private suspend fun uploadUserImage(uri: Uri): String{
        val storageRef = firebaseStorage.reference
        val fileName = UUID.randomUUID().toString()
        val userImagesRef = storageRef.child("images/$fileName")

        userImagesRef.putFile(uri).await()

        return userImagesRef.path
    }


    override suspend fun getUserData(): Flow<NetworkResult<User>> {
        return flow {
            var requestStatus = false
            val currentUser = firebaseAuth.currentUser
            var user = User()
            emit(NetworkResult.Loading())
            try {
                currentUser?.uid?.let {
                    userCollection.document(it)
                        .get()
                        .addOnSuccessListener { document ->
                            user = document.toObject(User::class.java)!!
                            requestStatus = true
                        }
                        .addOnFailureListener {
                            requestStatus = false
                        }
                        .await()
                }
                emit(NetworkResult.Sucess(user))
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message))
            }
        }
    }
}