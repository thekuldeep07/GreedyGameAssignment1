package com.example.greedygameassignment.domain.mapper

import com.example.greedygameassignment.common.base.Mapper
import com.example.greedygameassignment.data.model.AlbumDetail
import com.example.greedygameassignment.data.model.TagDetail
import javax.inject.Inject

class AlbumDetailMapper @Inject constructor() : Mapper<AlbumDetail, AlbumDetail.Album?> {
    override fun fromMap(from: AlbumDetail): AlbumDetail.Album? {
        return from?.album
    }
}