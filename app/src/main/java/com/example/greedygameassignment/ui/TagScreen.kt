package com.example.greedygameassignment.features.tags.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.greedygameassignment.R
import com.example.greedygameassignment.data.model.Tags
import com.example.greedygameassignment.navigation.NavRoutes

@Composable
fun TagScreen(
    navController: NavHostController,
    viewModel: TagViewModel = hiltViewModel()
) {
    val res = viewModel.res.value
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
        MainUI(res.data,navController)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainUI(res: List<Tags.Tag>, navController: NavHostController) {

    val list = remember {
        mutableStateListOf<Tags.Tag>()
    }
    val arrowState = remember {
        mutableStateOf(false)
    }
    val iconValueState = remember {
        mutableStateOf(R.drawable.arrow_down)
    }
    if (!arrowState.value){
        iconValueState.value = R.drawable.arrow_down
        list.clear()
        list.addAll(res.subList(0,10))
    }
    else{
        iconValueState.value = R.drawable.arrow_up
        list.clear()
        list.addAll(res)
    }

    Column(
        modifier = Modifier
            .systemBarsPadding()
            .padding(24.dp, 24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(24.dp, 34.dp),
            text = "musicwiki",
            textAlign = TextAlign.Center,
            style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 40.sp)
        )
        Text(
            modifier = Modifier.padding(24.dp, 14.dp),
            text = "Welcome!",
            textAlign = TextAlign.Center,
            style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 20.sp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Choose a genre to start with",
                textAlign = TextAlign.Center,
                style = TextStyle(color = Color.Black, fontSize = 20.sp)
            )

            Spacer(modifier = Modifier.size(10.dp))

            OutlinedButton(
                onClick = {

                    arrowState.value = arrowState.value==false

                },
                modifier = Modifier.size(20.dp),  //avoid the oval shape
                shape = CircleShape,
                border = BorderStroke(1.dp, Color.Black),
                contentPadding = PaddingValues(0.dp),  //avoid the little icon
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = iconValueState.value),
                    contentDescription = null
                )
            }


        }
        
        LazyVerticalGrid(
            modifier = Modifier.padding(0.dp,24.dp),
            columns = GridCells.Adaptive(110.dp),
            contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp),
            content ={
                items(list.size) { index ->
                    Card(
                        modifier = Modifier
                            .padding(18.dp),
                        elevation = 8.dp,
                        border = BorderStroke(1.dp,Color.Black),
                        shape = RoundedCornerShape(20.dp),
                        onClick = {
                            navController.navigate(NavRoutes.TagDetail.getRoute(list[index].name.toString()))
                        }
                    ) {
                        Text(
                            text = list[index].name.toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }} )

    }
}