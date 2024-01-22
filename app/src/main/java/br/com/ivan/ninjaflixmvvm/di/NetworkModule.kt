package br.com.ivan.ninjaflixmvvm.di

import br.com.ivan.ninjaflixmvvm.data.remote.FilmeRemoteDataSource
import br.com.ivan.ninjaflixmvvm.data.remote.FilmeService
import br.com.ivan.ninjaflixmvvm.data.remote.GenresService
import br.com.ivan.ninjaflixmvvm.data.repository.GenresRepository
import br.com.ivan.ninjaflixmvvm.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideInterceptor()=  Interceptor{
        val originalRequest = it.request()
        val newHttpUrl = originalRequest.url.newBuilder()
            //.addQueryParameter("app_id", API_ID)
            .addQueryParameter("api_key", "774c61c65029ce18557375f751ca79b0")
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newHttpUrl)
            .build()
        it.proceed(newRequest)
    }

    @Singleton
    @Provides
    fun proviveHttpClient(
        interceptor: Interceptor
    ): OkHttpClient{
        return OkHttpClient.Builder()
            //.addNetworkInterceptor(interceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit{
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.URL_BASE_API)
            .build()
    }

    @Singleton
    @Provides
    fun provideFilmeService(retrofit: Retrofit) =
        retrofit.create(FilmeService::class.java)

    @Singleton
    @Provides
    fun provideGenresService(retrofit: Retrofit) =
        retrofit.create(GenresService::class.java)


    @Singleton
    @Provides
    fun provideFilmeRemoteDataSource(
        filmeService: FilmeService
    ): FilmeRemoteDataSource{
        return FilmeRemoteDataSource(filmeService)
    }




}