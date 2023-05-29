package com.example.chatwiseassignment.data.repository

import com.example.chatwiseassignment.data.models.Album
import com.example.chatwiseassignment.data.retrofit.AlbumAPI
import com.example.chatwiseassignment.util.NetworkResult
import com.example.chatwiseassignment.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AlbumRepository
@Inject constructor(
    private val albumAPI: AlbumAPI
){

    suspend fun getAlbum(): Flow<NetworkResult<Album>> {
        return flow {
            emit( safeApiCall { albumAPI.getAlbum() })
        }.flowOn(Dispatchers.IO)
    }

}