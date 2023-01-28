package com.example.greedygameassignment.domain.mapper

import com.example.greedygameassignment.common.base.Mapper
import com.example.greedygameassignment.data.model.Artists
import com.example.greedygameassignment.data.model.Tracks
import javax.inject.Inject

class TopTrackMapper @Inject constructor() : Mapper<Tracks?, List<Tracks.Track>?> {
    override fun fromMap(from: Tracks?): List<Tracks.Track>? {
        return from?.tracks?.track?.map {
            Tracks.Track(
                name = it.name,
                image = it.image,
                artist = it.artist
            )
        }
    }
}