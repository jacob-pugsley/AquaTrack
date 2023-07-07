package com.example.aquatrack.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.inventory.ui.AppViewModelProvider


@Composable
fun TankDetailsView(navController: NavHostController, tankId: String?) {

    //todo: grab tank details from database
    val viewModel: TankDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val uiState = viewModel.uiState.collectAsState()
    val tank = uiState.value.itemDetails

    Column (
        modifier = Modifier.fillMaxWidth()
    ){
        Text(tank.name, style = MaterialTheme.typography.headlineLarge)
        Text("Volume: ${tank.gallons}G", style= MaterialTheme.typography.headlineMedium)

        Text("Dimensions: ${tank.length}\"L x ${tank.width}\"W x ${tank.height}\"H",
            style= MaterialTheme.typography.headlineMedium)
        //todo: implement water type
        //Text(tank.waterType, style= MaterialTheme.typography.headlineMedium)

        //todo: implement water records
//            Text("Water Parameters", style= MaterialTheme.typography.headlineMedium)
//            val parameters = listOf(
//                "Date", "Ammonia", "Nitrate", "Nitrite", "pH", "gH", "kH"
//            )
//            if( tank.waterRecords.isNotEmpty() ) {
//                Table(tank.water
    //
    //                Records, parameters)
//            }

//            Text(
//                "+ Record new data points",
//                style = MaterialTheme.typography.headlineSmall.merge(
//                    TextStyle(
//                    color = Color.Blue,
//                    textDecoration = TextDecoration.Underline
//                )
//                ),
//                modifier = Modifier.clickable(
//                    true,
//                    onClick = { navController.navigate("recordMeasurement/${tank.name}") })
//            )
    }
}



//@Composable
//fun Table(data: MutableList<WaterRecord>, columns: List<String>) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceAround
//    ){
//
//        columns.forEach {col ->
//            var i = 1
//            Column(
//                modifier = Modifier,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(" $col ",
//                    fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(horizontal = 4.dp).then(if( i % 2 == 0) Modifier.background(Color.LightGray) else Modifier))
//                i++
//                data.forEach { wr ->
//                    val textContent = when (col) {
//                        "Date" -> wr.getShortDate()
//                        "Ammonia" -> wr.ammonia
//                        "Nitrite" -> wr.nitrite
//                        "Nitrate" -> wr.nitrate
//                        "pH" -> wr.ph
//                        "gH" -> wr.gh
//                        "kH" -> wr.kh
//                        else -> {
//                            "errname"
//                        }
//                    }
//
//                    Text(" ${textContent.toString()} ", modifier = Modifier.padding(horizontal = 4.dp).then(if( i % 2 == 0) Modifier.background(Color.LightGray) else Modifier))
//                    i++
//                }
//            }
//
//        }
//    }
//}
