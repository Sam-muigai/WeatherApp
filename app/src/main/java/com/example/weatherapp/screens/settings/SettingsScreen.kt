package com.example.weatherapp.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.model.units.Units
import com.example.weatherapp.utils.widgets.WeatherAppBar

@Composable
fun SettingScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    var unitToggleState by remember {
        mutableStateOf(false)
    }
    val measurementUnits = listOf("Imperial (F),Metric (C)")

    val choiceByDb = settingsViewModel.unitList.collectAsState().value

    val defaultChoice =
        if (choiceByDb.isEmpty()) measurementUnits[0]
        else choiceByDb[0].unit

    var choiceState by remember {
        mutableStateOf(defaultChoice)
    }
    Scaffold(topBar = {
        WeatherAppBar(
            navController = navController,
            title = "Settings",
            isMainScreen = false,
            icon = Icons.Default.ArrowBack
        ) {
            navController.popBackStack()
        }
    }) {
        androidx.compose.material.Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Change Units of Measurement",
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                IconToggleButton(
                    checked = !unitToggleState,
                    onCheckedChange = {
                        unitToggleState = !it
                        if (unitToggleState) {
                            choiceState = "Imperial (F)"
                        } else {
                            choiceState = "Metric (C)"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(color = Color.Magenta)
                ) {
                    Text(text = if (unitToggleState) "Fahrenheit" else "Celsius °C")

                }
                Button(
                    onClick = {
                              settingsViewModel.deleteAllUnits()
                        settingsViewModel.insertUnit(Units(unit = choiceState))
                    },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(alignment = CenterHorizontally),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFEFBE42))
                ) {
                    Text(text = "Save",
                        fontSize = 17.sp,
                        modifier = Modifier.padding(4.dp))
                }
            }
        }
    }
}