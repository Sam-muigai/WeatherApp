package com.example.weatherapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.weatherapp.R
import com.example.weatherapp.model.WeatherItem
import com.example.weatherapp.utils.formatDateRow

@Composable
fun HumidityWindPressureRow(weather: WeatherItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity",
                modifier = Modifier.size(20.dp))
            Text(text = "${weather.main.humidity}%",
                style = MaterialTheme.typography.caption)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure",
                modifier = Modifier.size(20.dp))
            Text(text = "${weather.main.pressure} psi",
                style = MaterialTheme.typography.caption)

        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind",
                modifier = Modifier.size(20.dp))
            Text(text = "${weather.wind.speed} m/h",
                style = MaterialTheme.typography.caption)

        }
    }
}
@Composable
fun MaxTempMinTemp(weather: WeatherItem,isImperial: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Row(modifier = Modifier.padding(4.dp))  {
            Icon(modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.high_temp),
                contentDescription = "Max Temp")
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = weather.main.temp_max.toString() + if (!isImperial) "°" else "F",
                style = MaterialTheme.typography.caption)
        }
        Row(modifier = Modifier.padding(4.dp))  {
            Icon(modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.low_temp),
                contentDescription = "Max Temp")
            Text(text = weather.main.temp_min.toString() +  if (!isImperial) "°" else "F",
                style = MaterialTheme.typography.caption)
        }
    }
}
@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(painter = rememberImagePainter(data = imageUrl),
        contentDescription = "Weather Icon",
        modifier = Modifier.size(80.dp))
}

@Composable
fun WeatherDetailRow(weather: WeatherItem,isImperial:Boolean){
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"
    Surface(
        Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(formatDateRow(weather.dt))
            WeatherStateImage(imageUrl = imageUrl)
            Surface(modifier = Modifier.padding(0.dp),
                shape = CircleShape,color = Color(0xFFFFC400)
            ) {
                Text(
                    text = weather.weather[0].description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption
                )
            }
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.Blue.copy(0.7f),
                    fontWeight = FontWeight.SemiBold
                )
                ){
                    append(weather.main.temp_max.toString() +if (!isImperial) "°" else "F")
                }
                withStyle(style = SpanStyle(
                    color = Color.LightGray,
                    fontWeight = FontWeight.SemiBold
                )
                ){
                    append(weather.main.temp_min.toString() + if (!isImperial) "°" else "F")
                }
            })
        }
    }
}
