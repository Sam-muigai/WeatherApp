package com.example.weatherapp.screens.favourite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.model.favourite.Favourite
import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.utils.widgets.WeatherAppBar

@Composable
fun FavouriteScreen(
    navController: NavController,
    favouriteViewModel: FavouriteViewModel = hiltViewModel()
) {
    val list = favouriteViewModel.favList.collectAsState().value
    Scaffold(topBar = {
        WeatherAppBar(
            navController = navController,
            isMainScreen = false,
            title = "Favourite City",
            icon = Icons.Default.ArrowBack
        ) { navController.popBackStack() }
    }) {
        if (list.isNotEmpty()){
            Surface(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyColumn() {
                        items(list) {
                            CityRow(
                                it, navController = navController,
                                favouriteViewModel
                            )
                        }
                    }
                }
            }

        }else Box{}
    }
}

@Composable
fun CityRow(
    favourite: Favourite,
    navController: NavController,
    favouriteViewModel: FavouriteViewModel
) {
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navController.popBackStack()
                navController.navigate(
                    WeatherScreens.MainScreen.name + "/${favourite.city}"
                )
            }
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(8.dp),
        color = Color.Gray
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = favourite.city,
                modifier = Modifier.padding(start = 4.dp)
            )
            Surface(
                modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color.LightGray
            ) {
                Text(
                    text = favourite.country,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption
                )
            }
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "delete",
                modifier = Modifier.clickable {
                    favouriteViewModel.deleteFavourite(favourite)
                },
                tint = Color.Red.copy(alpha = 0.6f)
            )
        }
    }
}
