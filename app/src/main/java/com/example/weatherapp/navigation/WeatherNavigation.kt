package com.example.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherapp.screens.about.AboutScreen
import com.example.weatherapp.screens.favourite.FavouriteScreen
import com.example.weatherapp.screens.favourite.FavouriteViewModel
import com.example.weatherapp.screens.mainscreen.MainScreen
import com.example.weatherapp.screens.mainscreen.MainViewModel
import com.example.weatherapp.screens.search.SearchScreen
import com.example.weatherapp.screens.settings.SettingScreen
import com.example.weatherapp.screens.settings.SettingsViewModel
import com.example.weatherapp.screens.splashscreen.SplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = WeatherScreens.SplashScreen.name){
        composable(WeatherScreens.SplashScreen.name){
            SplashScreen(navController = navController)
        }
        composable(WeatherScreens.MainScreen.name + "/{city}",
            arguments = listOf(navArgument("city"){
            type = NavType.StringType
        })){ navBack ->
            navBack.arguments?.getString("city").let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                val settingsViewModel = hiltViewModel<SettingsViewModel>()
                MainScreen(navController = navController,mainViewModel,
                    city = city,
                    settingsViewModel = settingsViewModel)
            }
        }
        composable(WeatherScreens.SearchScreen.name){
            SearchScreen(navController = navController)
        }
        composable(WeatherScreens.AboutScreen.name){
            AboutScreen(navController = navController)
        }
        composable(WeatherScreens.SettingScreen.name){
            val settingsViewModel = hiltViewModel<SettingsViewModel>()
            SettingScreen(navController = navController, settingsViewModel = settingsViewModel)
        }
        composable(WeatherScreens.FavouriteScreen.name){
            val favouriteViewModel = hiltViewModel<FavouriteViewModel>()
            FavouriteScreen(navController = navController,favouriteViewModel)
        }
    }
}