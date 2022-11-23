package com.example.weatherapp.utils.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.model.favourite.Favourite
import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.screens.favourite.FavouriteViewModel
import kotlinx.coroutines.flow.collect


@Composable
fun WeatherAppBar(
    title: String = "Seattle, US",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favouriteViewModel: FavouriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val showDialog = remember {
        mutableStateOf(false)
    }
    val showIt = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    if (showDialog.value) {
        ShowDropDownMenu(showDialog = showDialog, navController = navController)
    }
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(start = 30.dp),
                text = title, fontWeight = FontWeight.Bold,
                fontSize = 19.sp
            )
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = { onAddActionClicked.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                }
                IconButton(onClick = {
                    showDialog.value = true
                }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "More Icon"
                    )
                }
            } else Box {}
        },
        navigationIcon = {
            if (icon != null) {
                Icon(imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.clickable { onButtonClicked.invoke() })
            }
            if (isMainScreen) {
                val inFavList = favouriteViewModel.favList.collectAsState().value.filter { item ->
                    item.city == title.split(",")[0]
                }
                if (inFavList.isEmpty()) {
                    Icon(imageVector = Icons.Default.Favorite,
                        contentDescription = "Add to Favourite",
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                val split = title.split(",")
                                favouriteViewModel.insertFavourite(
                                    Favourite(
                                        city = split[0],
                                        country = split[1]
                                    )
                                )
                                showIt.value = true
                            }, tint = Color.Red.copy(alpha = 0.6f))
                }else{
                    showIt.value = false
                    Box {}}
                ShowToast(context = context,showIt = showIt)
            }
        },
        elevation = elevation,
        backgroundColor = Color.Transparent
    )
}

@Composable
fun ShowToast(context: Context, showIt:MutableState<Boolean>) {
    if (showIt.value){
        Toast.makeText(context,"Added to Favourite",Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ShowDropDownMenu(
    showDialog: MutableState<Boolean>,
    navController: NavController
) {
    var expanded by remember {
        mutableStateOf(true)
    }
    val items = listOf("Favourites", "About", "Settings")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                showDialog.value = false
                expanded = false
            },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDialog.value = false
                }) {
                    Icon(
                        imageVector = when (text) {
                            "About" -> Icons.Default.Info
                            "Favourite" -> Icons.Default.FavoriteBorder
                            else -> Icons.Default.Settings
                        },
                        contentDescription = text,
                        tint = Color.LightGray
                    )
                    Text(
                        text = text,
                        modifier = Modifier
                            .clickable {
                                navController.navigate(
                                    when (text) {
                                        "About" -> WeatherScreens.AboutScreen.name
                                        "Settings" -> WeatherScreens.SettingScreen.name
                                        else -> WeatherScreens.FavouriteScreen.name
                                    }
                                )
                            }, fontWeight = FontWeight.W300
                    )
                }
            }
        }
    }
}
