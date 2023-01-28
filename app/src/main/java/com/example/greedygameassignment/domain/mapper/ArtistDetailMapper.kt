package com.example.greedygameassignment.domain.mapper

import com.example.greedygameassignment.common.base.Mapper
import com.example.greedygameassignment.data.model.AlbumDetail
import com.example.greedygameassignment.data.model.ArtistDetail
import javax.inject.Inject

class ArtistDetailMapper @Inject constructor() : Mapper<ArtistDetail, ArtistDetail.Artist?> {
    override fun fromMap(from: ArtistDetail): ArtistDetail.Artist? {
        return from.artist
    }
}