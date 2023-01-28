package com.example.greedygameassignment.domain.usecase

import com.example.greedygameassignment.common.ApiState
import com.example.greedygameassignment.common.map
import com.example.greedygameassignment.data.model.ArtistDetail
import com.example.greedygameassignment.domain.mapper.ArtistDetailMapper
import com.example.greedygameassignment.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtistDetailUseCase @Inject constructor(
    private val repo: Repository,
    private val mapper : ArtistDetailMapper
) {


    suspend fun getArtistDetail(artistName:String): Flow<ApiState<ArtistDetail.Artist?>> {
        return  repo.getArtistDetail(artistName).map{ it ->
            it.map {
                mapper.fromMap(it)
            }
        }
    }
}