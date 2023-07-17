package com.example.weatherapp.presention.view

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.domain.util.Screens
import com.example.weatherapp.presention.viewModel.OpeningViewModel

@Composable
fun OpeningScreen(
    navController : NavController
) {

    var apiKey by remember { mutableStateOf("") }
    val context: Context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        TextField(
            value = apiKey,
            onValueChange = {apiKey = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
                .shadow(1.dp)
                .border(1.dp, Color.DarkGray)
                .focusRequester(focusRequester = FocusRequester()),

            label = { Text(text = "API KEY", color = Color.DarkGray)},
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent),
            singleLine = true,
            shape =   RoundedCornerShape(8.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                //Toast.makeText(context, "lat: $lat lon: $lon", Toast.LENGTH_SHORT).show()
                navController.navigate(Screens.WeatherScreen.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
                .shadow(2.dp, RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(
                Color(0xFF4E4F50),
                contentColor = Color(0xFFE2DED0)
            )
        ) {
            Text(text = "Enter", modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}