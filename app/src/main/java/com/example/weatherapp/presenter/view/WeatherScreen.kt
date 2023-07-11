package com.example.weatherapp.presenter.view



import android.graphics.Paint.Align
import android.webkit.WebSettings.TextSize
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.weatherapp.R
import com.example.weatherapp.data.model.Weather

@Composable
fun WeatherScreen(navController: NavController) {


    Scaffold(
        topBar = {
            TopAppBar(
              //  title = { Text(text = "Back", color = Color(0xff2F5061), TextAlign = CenterVertically) },
                backgroundColor = Color.White,
                contentColor = Color(0xff2F5061),
                elevation = 10.dp,
                modifier = Modifier.height(80.dp)
            ){
                //TopAppBar Content
                Box(modifier = Modifier.fillMaxWidth()) {

                    //Navigation Content
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .clickable {navController.navigate("opening_screen") }
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
                ListingOfWeatherByDays(listOfWeather)
            }
        it
    }
    
}


@Composable
fun WeatherDisplay(

) {
    val location : String ?= null
    val celcius : Int ?= null
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
        Text(text = "İstanbul, TR", fontSize = 24.sp, fontWeight = FontWeight.SemiBold,color= Color.DarkGray, modifier = Modifier.padding(8.dp))

        Image(
            painter = rememberImagePainter(R.drawable.ic_launcher_background),
            contentDescription = "", modifier = Modifier
                .size(70.dp)
                .padding(8.dp),
        )

        Text(text = "5"+"°", fontSize = 48.sp, fontWeight = FontWeight.Light,modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun EachRowOfWeather(
    weatherItems : Weather
) {

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        ) {

            Row(modifier = Modifier
                .padding(8.dp, 8.dp, 32.dp, 8.dp)
                .width(100.dp)) {
                Text(
                    text = weatherItems.day, fontSize = 18.sp, color = Color.DarkGray)
            }

            Spacer(modifier = Modifier.width(32.dp))
            Box(
                contentAlignment = Alignment.CenterStart, modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
            ) {
                Image(
                    painter = rememberImagePainter(R.drawable.ic_launcher_background),
                    contentDescription = "",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(horizontal = 8.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = weatherItems.celciusDay.toString() + "°",
                    fontSize = 18.sp,
                    color= Color.DarkGray,
                    modifier = Modifier.padding(4.dp)
                )

                Text(
                    text = weatherItems.celciusDay.toString() + "°",
                    fontSize = 18.sp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

}

var listOfWeather = listOf<Weather>(
    Weather("Friday", 6, 1),
    Weather("Saturday", 8, 4),
    Weather("Sunday", 18, 11),
    Weather("Monday", 16, 10)
)
@Composable
fun ListingOfWeatherByDays(
    listOfWeathers : List<Weather>
) {

    LazyColumn(contentPadding = PaddingValues(5.dp)){
        items(listOfWeathers){weather ->
            EachRowOfWeather(weatherItems = weather)
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier
                .width(400.dp)
                .padding(horizontal = 16.dp))
        }
    }
}

