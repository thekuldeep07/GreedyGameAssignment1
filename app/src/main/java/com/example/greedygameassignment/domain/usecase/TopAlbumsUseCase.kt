package com.example.greedygameassignment.domain.usecase

import com.example.greedygameassignment.common.ApiState
import com.example.greedygameassignment.common.map
import com.example.greedygameassignment.data.model.Albums
import com.example.greedygameassignment.data.model.TagDetail
import com.example.greedygameassignment.data.model.Tags
import com.example.greedygameassignment.domain.mapper.TagsDetailMapper
import com.example.greedygameassignment.domain.mapper.TopAlbumMapper
import com.example.greedygameassignment.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TopAlbumsUseCase @Inject constructor(
    private val repo: TagRepository,
    private val mapper : TopAlbumMapper
) {


    suspend fun getTopAlbums(albumName:String): Flow<ApiState<List<Albums.Album>?>> {
        return repo.getTopAlbums(albumName = albumName).map { results ->
            results.map {
                mapper.fromMap(it)
            }
        }
    }

}