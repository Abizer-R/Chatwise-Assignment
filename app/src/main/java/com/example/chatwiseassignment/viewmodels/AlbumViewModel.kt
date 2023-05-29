package com.example.chatwiseassignment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatwiseassignment.data.models.Album
import com.example.chatwiseassignment.data.models.AlbumItem
import com.example.chatwiseassignment.data.repository.AlbumRepository
import com.example.chatwiseassignment.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel
@Inject constructor(
    private val repository: AlbumRepository
) : ViewModel(){

    private val _response: MutableLiveData<NetworkResult<Album>> = MutableLiveData()
    val response: LiveData<NetworkResult<Album>> = _response

    init {
        getAlbum()
    }

    fun getAlbum() = viewModelScope.launch {
        repository.getAlbum().collect { album ->
            _response.value = album
        }
    }

}