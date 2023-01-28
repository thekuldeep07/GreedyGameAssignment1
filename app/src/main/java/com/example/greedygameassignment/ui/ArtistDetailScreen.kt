package com.example.greedygameassignment.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.greedygameassignment.R
import com.example.greedygameassignment.data.model.ArtistDetail
import com.example.greedygameassignment.features.tags.ui.TagViewModel
import com.example.greedygameassignment.navigation.NavRoutes
import com.example.greedygameassignment.util.GeneralIcon

@Composable
fun ArtistDetailScreen(
    navController: NavHostController,
    artistName: String,
    viewModel: TagViewModel = hiltViewModel())
{
    val flag = remember { mutableStateOf(false) }
    if (!flag.value) {
        viewModel.getArtistDetail(artistName = artistName)
    }
    val res = viewModel.artistDetailResponse.value

    if (res.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    if (res.error.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = res.error)
        }
    }
    if (res.data != null) {
        flag.value = true
        MainUI(res.data, navController, viewModel)
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainUI(
    data: ArtistDetail.Artist,
    navController: NavHostController,
    viewModel: TagViewModel)
{
    Column(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ){
            GeneralIcon(
                modifier = Modifier
                    .zIndex(4f)
                    .padding(16.dp),
                imageVector = Icons.Filled.ArrowBack,
                onClick = { navController.navigateUp() }
            )
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data = data.image[3]?.text)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .crossfade(true)
                        .build()
                ),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.6f)

            )
            Column(
                modifier = Modifier
                    .padding(10.dp, 0.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = data.name.toString(),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ),
                    fontSize = 60.sp,
                )
                Spacer(modifier = Modifier.size(30.dp))
                Row(
                    modifier = Modifier.padding(48.dp,0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ){

                    val playCount = (data.stats?.playcount?.toInt())?.div(1000)
                    val followers = (data.stats?.listeners?.toInt())?.div(1000)
                    Text(
                        text = "${playCount.toString()}K",
                        style = TextStyle(fontWeight = FontWeight.Bold, textAlign = TextAlign.Center),
                        fontSize = 20.sp,
                    )
                    Spacer(modifier =Modifier.weight(1f))
                    Text(
                        text = "${followers.toString()}K",
                        style = TextStyle(fontWeight = FontWeight.Bold, textAlign = TextAlign.Center),
                        fontSize = 20.sp,
                    )
                }

                Spacer(modifier = Modifier.size(3.dp))

                Row(
                    modifier = Modifier.padding(48.dp,0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ){

                    Text(
                        text = "PlayCount",
                        style = TextStyle(fontWeight = FontWeight.Bold, textAlign = TextAlign.Center),
                        fontSize = 15.sp,
                    )
                    Spacer(modifier =Modifier.weight(1f))
                    Text(
                        text = "Followers",
                        style = TextStyle(fontWeight = FontWeight.Bold, textAlign = TextAlign.Center),
                        fontSize = 15.sp,
                    )
                }


            }

        }

        LazyRow(modifier = Modifier.padding(10.dp,10.dp),
            content = {
                val list = data.tags?.tag!!
                items(list.size) { index ->
                    Card(
                        modifier = Modifier
                            .padding(2.dp, 0.dp)
                            .width(130.dp),
                        elevation = 8.dp,
                        border = BorderStroke(1.dp, Color.Black),
                        shape = RoundedCornerShape(20.dp),
                        onClick = {
                            navController.navigate(NavRoutes.TagDetail.getRoute(list[index].name.toString())){
                                popUpTo(navController.graph.findStartDestination().id)
                            }
                        }
                    ) {
                        Text(
                            text = list[index].name!!,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(10.dp)
                        )
                    }

                }
            }


        )

        Text(
            modifier = Modifier.padding(10.dp,10.dp),
            text = if (data.bio!=null) data.bio.summary.toString().substringBefore("<a") else "Sorry No Details Found",
            textAlign = TextAlign.Left,
            style = TextStyle(color = Color.Black, fontSize = 18.sp)
        )

        TopTrackArtistScreen(viewModel,data.name.toString())

        TopAlbumArtistScreen(viewModel,data.name.toString())






    }
}

@Composable
fun TopAlbumArtistScreen(viewModel: TagViewModel, artistName: String)
{

    val flag = remember { mutableStateOf(false) }
    if (!flag.value) {
        viewModel.getTopAlbumByArtist(artistName = artistName)
    }
    val res = viewModel.topAlbumsByArtistStateResponse.value

    if (res.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    if (res.error.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = res.error)
        }
    }
    if (res.data.isNotEmpty()) {
        val data = res.data
        flag.value = true
        Column(modifier = Modifier.padding(12.dp,2.dp)) {
            Text(text = "Top Albums" , style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 18.sp )
            Box(
                modifier = Modifier.padding(0.dp, 0.dp)
            ) {
                LazyRow(
                    content = {
                        items(data.size) { index ->
                            Box() {
                                val imageData =
                                    if (data[index].image.isNotEmpty()) data[index].image[3].text else R.drawable.broken_image_24
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(imageData)
                                            .placeholder(R.drawable.ic_launcher_foreground)
                                            .crossfade(true)
                                            .build()
                                    ),
                                    contentScale = ContentScale.FillHeight,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(200.dp)
                                        .alpha(0.6f)

                                )
                                Column(
                                    modifier = Modifier
                                        .padding(1.dp,2.dp)
                                        .align(Alignment.Center),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(
                                        modifier = Modifier.width(200.dp),
                                        text = data[index].name,
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center
                                        ),
                                        fontSize = 16.sp,
                                    )
                                    Text(
                                        text = data[index].artist.name,
                                        style = TextStyle(textAlign = TextAlign.Center),
                                        fontSize = 16.sp,
                                    )

                                }


                            }
                        }
                    })
            }

        }


    }

}

@Composable
fun TopTrackArtistScreen(
    viewModel: TagViewModel,
    artistName: String
) {

    val flag = remember { mutableStateOf(false) }
    if (!flag.value) {
        viewModel.getTopTrackByArtist(artistName = artistName)
    }
    val res = viewModel.topTracksByArtistStateResponse.value

    if (res.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    if (res.error.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = res.error)
        }
    }
    if (res.data.isNotEmpty()) {
        val data = res.data
        flag.value = true
        Column(modifier = Modifier.padding(12.dp,2.dp)) {
            Text(text = "Top Tracks" , style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 18.sp )
            Box(
                modifier = Modifier.padding(0.dp, 0.dp)
            ) {
                LazyRow(
                    content = {
                        items(data.size) { index ->
                            Box() {
                                val imageData =
                                    if (data[index].image.isNotEmpty()) data[index].image[3].text else R.drawable.broken_image_24
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(imageData)
                                            .placeholder(R.drawable.ic_launcher_foreground)
                                            .crossfade(true)
                                            .build()
                                    ),
                                    contentScale = ContentScale.FillHeight,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(200.dp)
                                        .alpha(0.6f)

                                )
                                Column(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .align(Alignment.Center),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(
                                        modifier = Modifier.width(200.dp),
                                        text = data[index].name,
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center
                                        ),
                                        fontSize = 16.sp,
                                    )
                                    Text(
                                        text = data[index].artist.name,
                                        style = TextStyle(textAlign = TextAlign.Center),
                                        fontSize = 16.sp,
                                    )

                                }


                            }
                        }
                    })
            }

        }
        

    }

}
