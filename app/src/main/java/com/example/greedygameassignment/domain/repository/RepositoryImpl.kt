package com.example.greedygameassignment.domain.repository

import com.example.greedygameassignment.common.ApiState
import com.example.greedygameassignment.common.base.BaseRepository
import com.example.greedygameassignment.data.model.*
import com.example.greedygameassignment.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : Repository, BaseRepository() {
    override suspend fun getTags(): Flow<ApiState<Tags>> = safeApiCall {
        apiService.getTags()
    }

    override suspend fun getTagDetail(tagName:String): Flow<ApiState<TagDetail>> = safeApiCall {
        apiService.getTagDetail(tagName =tagName)
    }

    override  suspend fun getTopAlbums(albumName:String):Flow<ApiState<Albums>> = safeApiCall {
        apiService.getTopAlbums(albumName = albumName)
    }

    override  suspend fun getTopArtists(tagName:String):Flow<ApiState<Artists>> = safeApiCall {
        apiService.getTopArtists(tagName = tagName)
    }


    override  suspend fun getTopTracks(tagName:String):Flow<ApiState<Tracks>> = safeApiCall {
        apiService.getTopTracks(tagName = tagName)
    }

    override  suspend fun getTopTracksByArtist(artistName:String):Flow<ApiState<TopTracksByArtist>> = safeApiCall {
        apiService.getTopTracksByArtist(artistName = artistName)
    }

    override  suspend fun getTopAlbumsByArtist(artistName:String):Flow<ApiState<TopAlbumsByArtist>> = safeApiCall {
        apiService.getTopAlbumsByArtist(artistName = artistName)
    }

    override suspend fun getAlbumDetail(artistName:String,albumName: String):Flow<ApiState<AlbumDetail>> = safeApiCall {
        apiService.getAlbumDetail(artistName = artistName, albumName = albumName)
    }

    override suspend fun getArtistDetail(artistName:String):Flow<ApiState<ArtistDetail>> = safeApiCall {
        apiService.getArtistDetail(artistName = artistName)
    }
}