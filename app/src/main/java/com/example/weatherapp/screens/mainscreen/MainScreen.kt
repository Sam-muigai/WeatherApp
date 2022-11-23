package com.example.weatherapp.screens.mainscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.components.HumidityWindPressureRow
import com.example.weatherapp.components.MaxTempMinTemp
import com.example.weatherapp.components.WeatherDetailRow
import com.example.weatherapp.components.WeatherStateImage
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherItem
import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.screens.settings.SettingsViewModel
import com.example.weatherapp.utils.formatDate
import com.example.weatherapp.utils.widgets.WeatherAppBar

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
) {
    val curCity: String = if (city!!.isBlank()) "Seattle" else city
    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }
    var isImperial by remember {
        mutableStateOf(false)
    }
    if (!unitFromDb.isNullOrEmpty()) {
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"
        val weatherData =
            produceState<DataOrException<Weather, Boolean, Exception>>(
                initialValue = DataOrException(
                    loading = true
                )
            ) {
                value = mainViewModel.getAllWeather(
                    city = curCity,
                    units = unit)
            }.value
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (weatherData.loading == true) {
                CircularProgressIndicator()
            } else if (weatherData.data != null) {
                MainScaffold(weather = weatherData.data, navController, isImperial = isImperial)
            }
        }
    }
}

@Composable
fun MainScaffold(
    weather: Weather,
    navController: NavController,
    isImperial: Boolean
) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = weather.city.name + ", ${weather.city.country}",
                elevation = 3.dp,
                navController = navController,
                onAddActionClicked = {
                    navController.popBackStack()
                    navController.navigate(WeatherScreens.SearchScreen.name)
                }
            )
        }
    ) {
        MainContent(weather, isImperial = isImperial)
    }
}

@Composable
fun MainContent(data: Weather,isImperial:Boolean) {
    val imageUrl = "https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png"
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatDate(data.list[0].dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = data.list[0].main.temp.toString() + if (!isImperial)"°" else "F",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = data.list[0].weather[0].main,
                    fontStyle = FontStyle.Italic
                )
            }
        }
        HumidityWindPressureRow(weather = data.list[0])
        Divider(color = Color.LightGray)
        MaxTempMinTemp(weather = data.list[0], isImperial = isImperial)
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = "This Week",
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold
        )
        Surface(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth(),
            color = Color.LightGray,
            shape = RoundedCornerShape(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(2.dp)
            ) {
                items(data.list) { item: WeatherItem ->
                    WeatherDetailRow(weather = item,isImperial)
                }
            }
        }
    }
}




