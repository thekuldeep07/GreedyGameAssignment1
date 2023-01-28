package com.example.greedygameassignment.domain.usecase

import com.example.greedygameassignment.common.ApiState
import com.example.greedygameassignment.common.map
import com.example.greedygameassignment.data.model.Tracks
import com.example.greedygameassignment.domain.mapper.TopTracksByArtistMapper
import com.example.greedygameassignment.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TopTracksByArtistUseCase @Inject constructor(
    private val repo: Repository,
    private val mapper : TopTracksByArtistMapper
) {


    suspend fun getTopTrackByArtist(artistName:String): Flow<ApiState<List<Tracks.Track>?>> {
        return repo.getTopTracksByArtist(artistName = artistName).map { results ->
            results.map {
                mapper.fromMap(it)
            }
        }
    }

}