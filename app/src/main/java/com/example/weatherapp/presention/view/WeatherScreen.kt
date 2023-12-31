package com.example.weatherapp.presention.view



import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.weatherapp.R
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.domain.util.LocationSingleton
import com.example.weatherapp.domain.util.Resource
import com.example.weatherapp.presention.viewModel.WeatherViewModel
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun WeatherScreen(
    navController: NavController,
) {

    /*
    val latitude by remember{
        weaViewModel.lat
    }
    val longitude by remember{
        weaViewModel.lon
    }*/

    Scaffold(
        topBar = {
            TopAppBar(
              //  title = { Text(text = "Back", color = Color(0xff2F5061), TextAlign = CenterVertically) },
                backgroundColor = Color.White,
                contentColor = Color(0xff2F5061),
                elevation = 10.dp,
                modifier = Modifier.height(60.dp)
            ){
                //TopAppBar Content
                Box(modifier = Modifier.fillMaxWidth()) {

                    //Navigation Content
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .clickable {navController.navigate("home_screen") }
                        ){
                            Icon(
                                painter = painterResource(id = R.drawable.back_arrow),
                                contentDescription = "",
                            )
                            //Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Back", fontSize = 18.sp)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                            .fillMaxSize()
                            .padding(68.dp, 0.dp, 0.dp, 0.dp)){

                            Text(text = "Weather App", fontSize = 20.sp, fontWeight = FontWeight.SemiBold,  maxLines= 1, textAlign = TextAlign.Center)
                        }
                    }

                }

            }
        }
    ) {

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp)) {
                WeatherDisplay()
                Spacer(modifier = Modifier.height(16.dp))
                EachRowOfWeather()
            }
        it
    }
    
}

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalCoilApi::class)
@Composable
fun WeatherDisplay(
    weatherViewModel : WeatherViewModel = hiltViewModel()
) {
    val locationSingleton = LocationSingleton.getLocation().locationName
    val weatherTopInfo = produceState<Resource<Weather>>(initialValue = Resource.Loading()){
        value = weatherViewModel.getWeatherInfo()
    }.value
    when(weatherTopInfo){
        is Resource.Success->{
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                Text(text = locationSingleton, fontSize = 24.sp, fontWeight = FontWeight.SemiBold,color= Color.DarkGray, modifier = Modifier.padding(8.dp))

                val icon : List<String> = weatherTopInfo.data!!.current.weather.mapIndexed { index, weatherX ->
                    weatherX.icon
                }
                Image(
                    painter = rememberImagePainter("https://openweathermap.org/img/wn/${icon[0]}@2x.png"),
                    contentDescription = "", modifier = Modifier
                        .size(70.dp)
                        .padding(8.dp),
                )
                Text(text = convertFahToCel(weatherTopInfo.data!!.current.temp)+"°", fontSize = 48.sp, fontWeight = FontWeight.Light,modifier = Modifier.padding(8.dp))
            }
        }
        else->Unit
    }
}


@SuppressLint("SuspiciousIndentation", "CoroutineCreationDuringComposition")
@Composable
fun EachRowOfWeather(
    weatherViewModel: WeatherViewModel = hiltViewModel()
) {

        val weatherItem = produceState<Resource<Weather>>(initialValue = Resource.Loading()){
            value = weatherViewModel.getWeatherInfo()
           // value = weatherViewModel.getWeatherInfo(41.015137,28.979530)
        }.value

        when(weatherItem){
                is Resource.Success ->{
                    LazyColumn(contentPadding = PaddingValues(5.dp)){
                        items(weatherItem.data!!.daily) { weather ->
                            Row(
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(4.dp, 8.dp, 16.dp, 8.dp)
                                        .width(110.dp)
                                ) {
                                    Text(
                                        text = convertToDay(weather.dt.toDouble()),
                                        fontSize = 18.sp,
                                        color = Color.DarkGray
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))

                                //Icon
                                val iconDaily : List<String> = weather.weather.map {  weatherX ->
                                    weatherX.icon
                                }
                                        Box(
                                            contentAlignment = Alignment.CenterStart, modifier = Modifier
                                                .width(50.dp)
                                                .height(50.dp)
                                                .fillMaxWidth()
                                        ) {
                                            Image(
                                                painter = rememberImagePainter("https://openweathermap.org/img/wn/${iconDaily[0]}@2x.png"),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(50.dp)
                                                    .padding(horizontal = 8.dp)
                                            )
                                        }
                                Row(
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = convertFahToCel(weather.temp.day) + "°",
                                        fontSize = 18.sp,
                                        color = Color.DarkGray,
                                        modifier = Modifier.padding(4.dp)
                                    )

                                    Text(
                                        text = convertFahToCel(weather.temp.eve) + "°",
                                        fontSize = 18.sp,
                                        color = Color.LightGray,
                                        modifier = Modifier.padding(4.dp)
                                    )
                                }
                                Divider(
                                    color = Color.LightGray, thickness = 1.dp, modifier = Modifier
                                        .width(400.dp)
                                        .padding(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }
                is Resource.Error->{}

                else -> Unit
            }
}

private fun convertToDay(myDate : Double) : String{
    val secondApiFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    val timestamp = myDate.toLong() // timestamp in Long


    val timestampAsDateString = DateTimeFormatter.ISO_INSTANT
        .format(java.time.Instant.ofEpochSecond(timestamp))


    val date = LocalDate.parse(timestampAsDateString, secondApiFormat)

    return date.dayOfWeek.toString()
}

fun convertFahToCel(kelvin: Double): String {
    val celsius = kelvin - 273.15
    return String.format("%.0f", celsius)
}
