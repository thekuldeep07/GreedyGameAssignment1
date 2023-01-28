package com.example.greedygameassignment.domain.mapper

import com.example.greedygameassignment.common.base.Mapper
import com.example.greedygameassignment.data.model.Albums
import com.example.greedygameassignment.data.model.TagDetail
import com.example.greedygameassignment.data.model.Tags
import javax.inject.Inject

class TopAlbumMapper @Inject constructor() : Mapper<Albums?,List<Albums.Album>?>{
    override fun fromMap(from: Albums?): List<Albums.Album>? {
        return from?.albums?.album?.map {
            Albums.Album(
                name = it.name,
                image = it.image,
                artist = it.artist
            )
        }
    }
}