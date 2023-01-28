package com.example.greedygameassignment.domain.mapper

import com.example.greedygameassignment.common.base.Mapper
import com.example.greedygameassignment.data.model.Albums
import com.example.greedygameassignment.data.model.TopAlbumsByArtist
import javax.inject.Inject

class TopAlbumsByArtistMapper  @Inject constructor() : Mapper<TopAlbumsByArtist?, List<Albums.Album>?> {
    override fun fromMap(from: TopAlbumsByArtist?): List<Albums.Album>? {
        return from?.topalbums?.album?.map {
            Albums.Album(
                name = it.name,
                image = it.image,
                artist = it.artist
            )
        }
    }
}