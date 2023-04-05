package com.example.marvelapp.network

import com.example.marvelapp.model.character.CharacterDataWrapper
import com.example.marvelapp.model.comic.ComicDataWrapper
import com.example.marvelapp.model.series.SeriesDataWrapper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.math.BigInteger
import java.security.MessageDigest

const val PRIVATE_KEY = "26626637ce3749efaf68b2660caff23a30172453"
const val PUBLIC_KEY = "0e3152e38ac56c6bbca565de5aff9fdb"

interface MarvelAPI {
    companion object {
        private const val BASE_URL = "https://gateway.marvel.com:443/v1/public/"
        private var marvelAPI: MarvelAPI? = null

        fun getInstance(): MarvelAPI {
            marvelAPI = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MarvelAPI::class.java)

            return marvelAPI!!
        }
    }

    @GET("characters")
    suspend fun getCharacters(
        @Query("apikey") apikey: String = PUBLIC_KEY,
        @Query("ts") ts: String,
        @Query("hash") hash: String = md5(ts + PRIVATE_KEY + PUBLIC_KEY)
    ): CharacterDataWrapper

    @GET("comics")
    suspend fun getComics(
        @Query("apikey") apikey: String = PUBLIC_KEY,
        @Query("ts") ts: String,
        @Query("hash") hash: String = md5(ts + PRIVATE_KEY + PUBLIC_KEY)
    ): ComicDataWrapper

    @GET("series")
    suspend fun getSeries(
        @Query("apikey") apikey: String = PUBLIC_KEY,
        @Query("ts") ts: String,
        @Query("hash") hash: String = md5(ts + PRIVATE_KEY + PUBLIC_KEY)
    ): SeriesDataWrapper


}

//md5 global logic used to convert url data to hash
fun md5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}