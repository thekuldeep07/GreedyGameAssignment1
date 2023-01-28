package com.example.greedygameassignment.domain.mapper

import com.example.greedygameassignment.common.base.Mapper
import com.example.greedygameassignment.data.model.Albums
import com.example.greedygameassignment.data.model.Tags
import javax.inject.Inject

class TagsMapper @Inject constructor() : Mapper<Tags?,List<Tags.Tag>?>{
    override fun fromMap(from: Tags?): List<Tags.Tag>? {
        return from?.toptags?.tag?.map {
            Tags.Tag(
                name = it.name,
                count = it.count,
                reach = it.reach
            )
        }
    }
}