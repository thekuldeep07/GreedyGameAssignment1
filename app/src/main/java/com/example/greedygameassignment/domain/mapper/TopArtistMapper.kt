package com.example.greedygameassignment.domain.mapper

import com.example.greedygameassignment.common.base.Mapper
import com.example.greedygameassignment.data.model.Albums
import com.example.greedygameassignment.data.model.Artists
import javax.inject.Inject

class TopArtistMapper @Inject constructor() : Mapper<Artists?, List<Artists.Artist>?> {
    override fun fromMap(from: Artists?): List<Artists.Artist>? {
        return from?.topArtists?.artist?.map {
            Artists.Artist(
                name = it.name,
                image = it.image,
            )
        }
    }
}