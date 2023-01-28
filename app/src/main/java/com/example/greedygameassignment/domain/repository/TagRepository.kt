package com.example.greedygameassignment.domain.repository

import com.example.greedygameassignment.common.ApiState
import com.example.greedygameassignment.data.model.*
import kotlinx.coroutines.flow.Flow

interface TagRepository {

    suspend fun getTags(): Flow<ApiState<Tags>>

    suspend fun getTagDetail(tagName:String):Flow<ApiState<TagDetail>>

    suspend fun getTopAlbums(albumName:String):Flow<ApiState<Albums>>

    suspend fun getTopArtists(tagName: String):Flow<ApiState<Artists>>

    suspend fun getTopTracks(tagName: String):Flow<ApiState<Tracks>>

    suspend fun getTopTracksByArtist(artistName: String):Flow<ApiState<TopTracksByArtist>>

    suspend fun getTopAlbumsByArtist(artistName: String):Flow<ApiState<TopAlbumsByArtist>>

    suspend fun getAlbumDetail(artistName:String,albumName: String):Flow<ApiState<AlbumDetail>>

    suspend fun getArtistDetail(artistName:String):Flow<ApiState<ArtistDetail>>
}