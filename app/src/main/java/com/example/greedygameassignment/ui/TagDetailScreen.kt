package com.example.greedygameassignment.features.tags.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.greedygameassignment.R
import com.example.greedygameassignment.data.model.TagDetail
import com.example.greedygameassignment.navigation.NavRoutes
import com.example.greedygameassignment.util.GeneralIcon
import com.example.greedygameassignment.util.TabRowItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun TagDetailScreen(
    navController: NavHostController,
    tagName: String,
    viewModel: TagViewModel = hiltViewModel()
) {

    val flag = remember { mutableStateOf(false) }
    if (!flag.value) {
        viewModel.getTagDetail(tagName)
    }
    val res = viewModel.tagDetailResponse.value

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


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainUI(
    data: TagDetail.Tag,
    navController: NavHostController,
    viewModel: TagViewModel
) {

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val tabRowItems = listOf(
        TabRowItem(
            title = "ALBUMS",
            screen = { TopAlbumScreen(navController,viewModel, data.name.toString()) }
        ),
        TabRowItem(
            title = "ARTISTS",
            screen = { TopArtistScreen(navController,viewModel,data.name.toString()) }
        ),
        TabRowItem(
            title = "TRACKS",
            screen = { TopTrackScreen(viewModel,data.name.toString()) }
        )

    )

    Box(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize(),
    ) {
        GeneralIcon(
            modifier = Modifier
                .zIndex(4f)
                .padding(16.dp),
            imageVector = Icons.Filled.ArrowBack,
            onClick = { navController.navigateUp() }
        )

        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.size(50.dp))

            Text(
                text = data.name.toString(),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 60.sp
                )
            )

            Spacer(modifier = Modifier.size(30.dp))

            Text(
                text = data.wiki.summary.toString().substringBefore("<a"),
                textAlign = TextAlign.Left,
                style = TextStyle(color = Color.Black, fontSize = 18.sp)
            )

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                backgroundColor = Color.Transparent,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                        color = MaterialTheme.colors.primary,
                        height = 2.dp

                    )
                },
            ) {
                tabRowItems.forEachIndexed { index, item ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                        icon = {},
                        text = {
                            Text(
                                text = item.title,
                                color = if (pagerState.currentPage == index) Color.Black else Color.LightGray
                            )
                        }
                    )
                }

            }
            HorizontalPager(
                count = tabRowItems.size,
                state = pagerState,
            ) {
                tabRowItems[pagerState.currentPage].screen()
            }
        }
    }
}

@Composable
fun TopTrackScreen(viewModel: TagViewModel, tag: String) {


        val flag = remember { mutableStateOf(false) }
        if (!flag.value) {
            viewModel.getTopTrack(tag)
        }
        val res = viewModel.topTrackResponse.value

        if (res.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        if (res.error.isNotEmpty()) {
            flag.value = true
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = res.error)
            }
        }
        if (res.data.isNotEmpty()) {
            val data = res.data
            flag.value = true
            Box(
                modifier = Modifier.padding(0.dp, 20.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(120.dp),
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
                                        .fillMaxSize()
                                        .align(Alignment.Center),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(
                                        text = data[index].name,
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center
                                        ),
                                        fontSize = 20.sp,
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




@Composable
fun TopArtistScreen(navController: NavHostController, viewModel: TagViewModel, tag: String)
{

    val flag = remember { mutableStateOf(false) }
    if (!flag.value) {
        viewModel.getTopArtist(tag)
    }
    val res = viewModel.topArtistResponse.value

    if (res.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    if (res.error.isNotEmpty()) {
        flag.value = true
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = res.error)
        }
    }
    if (res.data.isNotEmpty()) {
        val data = res.data
        flag.value = true
        Box(
            modifier = Modifier.padding(0.dp, 20.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                content = {
                    items(data.size) { index ->
                        Box(modifier = Modifier.clickable {
                                navController.navigate(NavRoutes.ArtistDetail.getRoute(
                                    data[index].name))
                        }) {
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
                                    .fillMaxSize()
                                    .align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text = data[index].name,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    ),
                                    fontSize = 20.sp,
                                )


                            }


                        }
                    }
                })
        }

    }
}

@Composable
fun TopAlbumScreen(
    navController: NavHostController,
    viewModel: TagViewModel,
    tag: String
) {

    val flag = remember { mutableStateOf(false) }
    if (!flag.value) {
        viewModel.getTopAlbum(tag)
    }
    val res = viewModel.topAlbumResponse.value

    if (res.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    if (res.error.isNotEmpty()) {
        flag.value = true
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = res.error)
        }
    }
    if (res.data.isNotEmpty()) {
        val data = res.data
        flag.value = true
        Box(
            modifier = Modifier.padding(0.dp, 20.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                content = {
                    items(data.size) { index ->
                        Box(
                            modifier = Modifier.clickable {
                                navController.navigate(NavRoutes.AlbumDetail.getRoute(
                                    data[index].artist.name,data[index].name))}
                        ) {
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
                                    .fillMaxSize()
                                    .align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text = data[index].name,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    ),
                                    fontSize = 20.sp,
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


@Composable
fun TabScreen(
    text: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = text)
    }

}


