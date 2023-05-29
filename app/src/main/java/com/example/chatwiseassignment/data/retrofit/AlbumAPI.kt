package com.example.chatwiseassignment.data.retrofit

import com.example.chatwiseassignment.data.models.Album
import retrofit2.Response
import retrofit2.http.GET

interface AlbumAPI {

    @GET("/photos")
    suspend fun getAlbum() : Response<Album>
}