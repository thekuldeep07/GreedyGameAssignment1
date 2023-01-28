package com.example.greedygameassignment.domain.usecase

import com.example.greedygameassignment.common.ApiState
import com.example.greedygameassignment.common.map
import com.example.greedygameassignment.data.model.Albums
import com.example.greedygameassignment.domain.mapper.TopAlbumsByArtistMapper
import com.example.greedygameassignment.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TopAlbumsByArtistUseCase @Inject constructor(
    private val repo: TagRepository,
    private val mapper : TopAlbumsByArtistMapper
) {


    suspend fun getTopAlbumByArtist(artistName:String): Flow<ApiState<List<Albums.Album>?>> {
        return repo.getTopAlbumsByArtist(artistName = artistName).map { results ->
            results.map {
                mapper.fromMap(it)
            }
        }
    }

}