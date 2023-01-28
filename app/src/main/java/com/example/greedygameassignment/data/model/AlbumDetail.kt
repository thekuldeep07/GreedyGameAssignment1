package com.example.greedygameassignment.data.model

import com.example.greedygameassignment.navigation.NavRoutes

data class AlbumDetail(
    val album:AlbumDetail.Album
){
    data class Album(
        val name:String?,
        val artist:String?,
        val tags:Tags.TopTags?,
        val image: List<Albums.ImageApi>?,
        val wiki: TagDetail.Wiki?
    )
}