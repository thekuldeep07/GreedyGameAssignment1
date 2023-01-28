package com.example.greedygameassignment.domain.usecase

import com.example.greedygameassignment.common.ApiState
import com.example.greedygameassignment.common.map
import com.example.greedygameassignment.data.model.Albums
import com.example.greedygameassignment.data.model.Artists
import com.example.greedygameassignment.domain.mapper.TopAlbumMapper
import com.example.greedygameassignment.domain.mapper.TopArtistMapper
import com.example.greedygameassignment.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TopArtistsUseCase @Inject constructor(
    private val repo: TagRepository,
    private val mapper : TopArtistMapper
) {


    suspend fun getTopArtists(tagName:String): Flow<ApiState<List<Artists.Artist>?>> {
        return repo.getTopArtists(tagName = tagName).map { results ->
            results.map {
                mapper.fromMap(it)
            }
        }
    }

}