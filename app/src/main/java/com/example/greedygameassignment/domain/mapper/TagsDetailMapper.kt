package com.example.greedygameassignment.domain.mapper

import com.example.greedygameassignment.common.base.Mapper
import com.example.greedygameassignment.data.model.TagDetail
import javax.inject.Inject

class TagsDetailMapper @Inject constructor() : Mapper<TagDetail, TagDetail.Tag?> {
    override fun fromMap(from: TagDetail): TagDetail.Tag? {
        return from?.tag
    }
}
