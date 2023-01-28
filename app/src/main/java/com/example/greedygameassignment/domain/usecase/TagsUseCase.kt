package com.example.greedygameassignment.domain.usecase

import com.example.greedygameassignment.common.ApiState
import com.example.greedygameassignment.common.map
import com.example.greedygameassignment.data.model.Tags
import com.example.greedygameassignment.domain.mapper.TagsMapper
import com.example.greedygameassignment.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class TagsUseCase @Inject constructor(
    private val repo: Repository,
    private val mapper : TagsMapper
) {

    suspend fun getTags(): Flow<ApiState<List<Tags.Tag>?>> {
        return repo.getTags().map { results ->
            results.map {
                mapper.fromMap(it)
            }

        }
    }



}