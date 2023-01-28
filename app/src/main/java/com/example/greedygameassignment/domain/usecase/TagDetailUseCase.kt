package com.example.greedygameassignment.domain.usecase

import com.example.greedygameassignment.common.ApiState
import com.example.greedygameassignment.common.map
import com.example.greedygameassignment.data.model.TagDetail
import com.example.greedygameassignment.domain.mapper.TagsDetailMapper
import com.example.greedygameassignment.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TagDetailUseCase@Inject constructor(
    private val repo: Repository,
    private val mapper : TagsDetailMapper
) {


       suspend fun getTagDetail(tagName:String): Flow<ApiState<TagDetail.Tag?>> {
       return  repo.getTagDetail(tagName = tagName).map{ it ->
           it.map {
               mapper.fromMap(it)
           }
       }
       }
}
