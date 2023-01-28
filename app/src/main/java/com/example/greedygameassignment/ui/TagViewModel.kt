package com.example.greedygameassignment.features.tags.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greedygameassignment.common.doOnFailure
import com.example.greedygameassignment.common.doOnLoading
import com.example.greedygameassignment.common.doOnSuccess
import com.example.greedygameassignment.data.model.*
import com.example.greedygameassignment.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(
    private val useCase: TagsUseCase,
    private val useCase2: TagDetailUseCase,
    private val topAlbumsUseCase: TopAlbumsUseCase,
    private val topArtistsUseCase: TopArtistsUseCase,
    private val topTrackUseCase: TopTrackUseCase,
    private val albumDetailUseCase: AlbumDetailUseCase,
    private val artistDetailUseCase: ArtistDetailUseCase,
    private val topTracksByArtistUseCase: TopTracksByArtistUseCase,
    private val topAlbumsByArtistUseCase: TopAlbumsByArtistUseCase
) : ViewModel(){

    private val _res:MutableState<TagState> = mutableStateOf(TagState())
    val res:State<TagState> = _res

    private val _tagDetail:MutableState<TagDetailState> = mutableStateOf(TagDetailState())
    val tagDetailResponse:State<TagDetailState> = _tagDetail

    private val _albumDetail:MutableState<AlbumDetailState> = mutableStateOf(AlbumDetailState())
    val albumDetailResponse:State<AlbumDetailState> = _albumDetail

    private val _artistDetail:MutableState<ArtistDetailState> = mutableStateOf(ArtistDetailState())
    val artistDetailResponse:State<ArtistDetailState> = _artistDetail

    private val _topAlbums:MutableState<TopAlbumState> = mutableStateOf(TopAlbumState())
    val topAlbumResponse:State<TopAlbumState> = _topAlbums


    private val _topArtists:MutableState<TopArtistState> = mutableStateOf(TopArtistState())
    val topArtistResponse:State<TopArtistState> = _topArtists

    private val _topTracks:MutableState<TopTrackState> = mutableStateOf(TopTrackState())
    val topTrackResponse:State<TopTrackState> = _topTracks


    private val _topTracksByArtist:MutableState<TopTracksByArtistState> = mutableStateOf(TopTracksByArtistState())
    val topTracksByArtistStateResponse:State<TopTracksByArtistState> = _topTracksByArtist

    private val _topAlbumsByArtist:MutableState<TopAlbumsByArtistState> = mutableStateOf(TopAlbumsByArtistState())
    val topAlbumsByArtistStateResponse:State<TopAlbumsByArtistState> = _topAlbumsByArtist

    fun getTopAlbumByArtist(artistName: String){
        viewModelScope.launch {
            topAlbumsByArtistUseCase.getTopAlbumByArtist(artistName = artistName)
                .doOnSuccess {
                    _topAlbumsByArtist.value = TopAlbumsByArtistState(
                        data = it!!
                    )
                }.doOnFailure {
                    _topAlbumsByArtist.value = TopAlbumsByArtistState(
                        error = it?.message!!
                    )
                }.doOnLoading {
                    _topAlbumsByArtist.value = TopAlbumsByArtistState(
                        isLoading = true
                    )
                }.collect()

        }

    }

    fun getTopTrackByArtist(artistName: String){
        viewModelScope.launch {
            topTracksByArtistUseCase.getTopTrackByArtist(artistName = artistName)
                .doOnSuccess {
                    _topTracksByArtist.value = TopTracksByArtistState(
                        data = it!!
                    )
                }.doOnFailure {
                    _topTracksByArtist.value = TopTracksByArtistState(
                        error = it?.message!!
                    )
                }.doOnLoading {
                    _topTracksByArtist.value = TopTracksByArtistState(
                        isLoading = true
                    )
                }.collect()

        }

    }

    fun getTopTrack(tagName:String){
        viewModelScope.launch {
            topTrackUseCase.getTopTrack(tagName = tagName)
                .doOnSuccess {
                    _topTracks.value = TopTrackState(
                        data = it!!
                    )
                }.doOnFailure {
                    _topTracks.value = TopTrackState(
                        error = it?.message!!
                    )
                }.doOnLoading {
                    _topTracks.value = TopTrackState(
                        isLoading = true
                    )
                }.collect()

        }
    }

    fun getTopArtist(tagName:String){
        viewModelScope.launch {
            topArtistsUseCase.getTopArtists(tagName = tagName)
                .doOnSuccess {
                    _topArtists.value = TopArtistState(
                        data = it!!
                    )
                }.doOnFailure {
                    _topArtists.value = TopArtistState(
                        error = it?.message!!
                    )
                }.doOnLoading {
                    _topArtists.value = TopArtistState(
                        isLoading = true
                    )
                }.collect()

        }
    }

    fun getTopAlbum(albumName:String){
        viewModelScope.launch {
            topAlbumsUseCase.getTopAlbums(albumName = albumName)
                .doOnSuccess {
                    _topAlbums.value = TopAlbumState(
                        data = it!!
                    )
                }.doOnFailure {
                    _topAlbums.value = TopAlbumState(
                        error = it?.message!!
                    )
                }.doOnLoading {
                    _topAlbums.value = TopAlbumState(
                        isLoading = true
                    )
                }.collect()

        }
    }

    fun getAlbumDetail(albumName: String,artistName:String){
        viewModelScope.launch {
            albumDetailUseCase.getAlbumDetail(artistName = artistName ,albumName =albumName)
                .doOnSuccess {
                    _albumDetail.value = AlbumDetailState(
                        data = it
                    )
                }.doOnFailure {
                    _albumDetail.value = AlbumDetailState(
                        error = it?.message!!
                    )
                }.doOnLoading {
                    _albumDetail.value = AlbumDetailState(
                        isLoading = true
                    )
                }.collect()
        }
    }

    fun getArtistDetail(artistName:String){
        viewModelScope.launch {
            artistDetailUseCase.getArtistDetail(artistName = artistName )
                .doOnSuccess {
                    _artistDetail.value = ArtistDetailState(
                        data = it
                    )
                }.doOnFailure {
                    _artistDetail.value = ArtistDetailState(
                        error = it?.message!!
                    )
                }.doOnLoading {
                    _artistDetail.value = ArtistDetailState(
                        isLoading = true
                    )
                }.collect()
        }
    }

    fun getTagDetail(tagName:String){
        viewModelScope.launch {
            useCase2.getTagDetail(tagName = tagName)
                .doOnSuccess {
                    _tagDetail.value = TagDetailState(
                        data = it
                    )
                }.doOnFailure {
                    _tagDetail.value = TagDetailState(
                        error = it?.message!!
                    )
                }.doOnLoading {
                    _tagDetail.value = TagDetailState(
                        isLoading = true
                    )
                }.collect()
        }
    }


    init {
        viewModelScope.launch {
            useCase.getTags()
                .doOnSuccess {
                    _res.value = TagState(
                        data = it!!
                    )
                }.doOnFailure {
                    _res.value = TagState(
                        error = it?.message!!
                    )
                }.doOnLoading {
                    _res.value = TagState(
                        isLoading = true
                    )
                }.collect()
        }

    }




}

data class  TagState(
    val data:List<Tags.Tag> = emptyList(),
    val error:String = "",
    val isLoading:Boolean = false
)

data class  TagDetailState(
    val data: TagDetail.Tag? = null,
    val error:String = "",
    val isLoading:Boolean = false
)

data class  AlbumDetailState(
    val data: AlbumDetail.Album? = null,
    val error:String = "",
    val isLoading:Boolean = false
)

data class TopAlbumState(
    val data : List<Albums.Album> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)

data class TopArtistState(
    val data : List<Artists.Artist> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)


data class TopTrackState(
    val data : List<Tracks.Track> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)

data class ArtistDetailState(
    val data : ArtistDetail.Artist? = null,
    val error: String = "",
    val isLoading: Boolean = false
)

data class TopTracksByArtistState(
    val data : List<Tracks.Track> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)

data class TopAlbumsByArtistState(
    val data : List<Albums.Album> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)