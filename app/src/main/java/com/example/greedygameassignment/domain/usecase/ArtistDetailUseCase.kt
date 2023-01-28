package com.example.greedygameassignment.domain.usecase

import com.example.greedygameassignment.common.ApiState
import com.example.greedygameassignment.common.map
import com.example.greedygameassignment.data.model.AlbumDetail
import com.example.greedygameassignment.data.model.ArtistDetail
import com.example.greedygameassignment.domain.mapper.AlbumDetailMapper
import com.example.greedygameassignment.domain.mapper.ArtistDetailMapper
import com.example.greedygameassignment.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtistDetailUseCase @Inject constructor(
    private val repo: TagRepository,
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