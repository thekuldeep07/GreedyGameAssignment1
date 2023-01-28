package com.example.greedygameassignment.domain.mapper

import com.example.greedygameassignment.common.base.Mapper
import com.example.greedygameassignment.data.model.TopTracksByArtist
import com.example.greedygameassignment.data.model.Tracks
import javax.inject.Inject

class TopTracksByArtistMapper @Inject constructor() : Mapper<TopTracksByArtist?, List<Tracks.Track>?> {
    override fun fromMap(from: TopTracksByArtist?): List<Tracks.Track>? {
        return from?.toptracks?.track?.map {
            Tracks.Track(
                name = it.name,
                image = it.image,
                artist = it.artist
            )
        }
    }
}