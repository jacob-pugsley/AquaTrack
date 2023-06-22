package com.example.aquatrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aquatrack.ui.theme.AquaTrackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AquaTrackTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") { Main(navController = navController) }
                        composable("createNewTank") { CreateNewTank() }
                        /*...*/
                    }
                }
            }


        }
    }
}

@Composable
fun Main(navController: NavHostController) {


    val tankList = remember { mutableListOf("Tank 1", "Tank 2", "Tank 3") }

    Column (
        verticalArrangement = Arrangement.spacedBy(50.dp)
    ){
        Text("AquaTrack", style = TextStyle(fontSize = 50.sp))

        tankList.map {
            Button(onClick = { navController.navigate("createNewTank") }) {
                Text(text = "$it >", style = TextStyle(fontSize = 50.sp))
            }
        }


        Text("+ Add new tank", style= TextStyle(fontSize = 50.sp), modifier = Modifier.clickable(true, onClick = {navController.navigate("createNewTank")}))
    }



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewTank() {
    var length by remember { mutableStateOf("") }
    var width by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var gallons by remember {
        mutableStateOf("")
    }
    var knowGallons by remember {
        mutableStateOf(true)
    }
    Column {
        Text("Create a new tank")

        Row {
            Switch(checked = knowGallons, onCheckedChange = { knowGallons = it })
            Text("I know how many gallons my tank is.")
        }

        if( knowGallons ) {
            Text("Gallons")
            OutlinedTextField(
                value = gallons,
                onValueChange = { gallons = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }else {
            Text("Length (left to right)")
            OutlinedTextField(
                value = length,
                onValueChange = { length = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Text("Width (front to back)")
            OutlinedTextField(
                value = width,
                onValueChange = { width = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Text("Height (top to bottom)")
            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            gallons = calculateGallons(length, width, height).toString()
            Text(
                "Gallons: $gallons"
            )
        }
    }
}

fun calculateGallons(length: String?, width: String?, height: String?): Int {
    if( length == null || width == null || height == null ) {
        return 0
    }

    val lengthInt: Int
    val widthInt: Int
    val heightInt: Int

    return try {
        lengthInt = length.toInt()
        widthInt = width.toInt()
        heightInt = height.toInt()

        (lengthInt * widthInt * heightInt) / 231
    } catch (ex: NumberFormatException) {
        0
    }
}
