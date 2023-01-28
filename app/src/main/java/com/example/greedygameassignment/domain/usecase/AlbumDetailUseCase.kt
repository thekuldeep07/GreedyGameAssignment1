package com.example.greedygameassignment.domain.usecase

import com.example.greedygameassignment.common.ApiState
import com.example.greedygameassignment.common.map
import com.example.greedygameassignment.data.model.AlbumDetail
import com.example.greedygameassignment.domain.mapper.AlbumDetailMapper
import com.example.greedygameassignment.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlbumDetailUseCase @Inject constructor(
    private val repo: Repository,
    private val mapper : AlbumDetailMapper
) {


    suspend fun getAlbumDetail(artistName:String,albumName:String): Flow<ApiState<AlbumDetail.Album?>> {
        return  repo.getAlbumDetail(artistName,albumName).map{ it ->
            it.map {
                mapper.fromMap(it)
            }
        }
    }
}