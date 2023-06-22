package com.example.aquatrack

import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aquatrack.ui.theme.AquaTrackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AquaTrackTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val tankList = remember { mutableListOf<TankData>() }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") { Main(navController, tankList) }
                        composable("createNewTank") { CreateNewTank(navController, tankList) }
                        composable("tankDetails/{tankname}", arguments =
                        listOf(navArgument("tankname") {type = NavType.StringType})) {
                            ViewTankDetails(navController = navController, tank = getTankByName( tankList, it.arguments?.getString("tankname")))
                        }
                    }
                }
            }
        }
    }
}

fun getTankByName(tankList: MutableList<TankData>, name: String?) : TankData? {
    return tankList.find { td -> td.name == name }
}

class TankData(
    val name: String,
    val length: Int?,
    val width: Int?,
    val height: Int?,
    val gallons: Int,
    val waterType: String
)

@Composable
fun Main(navController: NavHostController, tankList: MutableList<TankData>) {

    Column (
        verticalArrangement = Arrangement.spacedBy(50.dp)
    ){
        Text("AquaTrack", style = TextStyle(fontSize = 50.sp))

        tankList.map {
            Button(
                onClick = { navController.navigate("tankDetails/${it.name}") },
                shape = RectangleShape,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "${it.name} (${it.gallons}G) >", style = TextStyle(fontSize = 50.sp))
            }
        }


        Text("+ Add new tank", style= TextStyle(fontSize = 50.sp, color = Color.Blue, textDecoration = TextDecoration.Underline), modifier = Modifier.clickable(true, onClick = {navController.navigate("createNewTank")}))
    }



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewTank(navController: NavHostController, tankList: MutableList<TankData>) {
    var length by remember { mutableStateOf("") }
    var width by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var gallons by remember { mutableStateOf("") }
    var knowGallons by remember {
        mutableStateOf(true)
    }
    var name by remember {
        mutableStateOf("")
    }
    val waterTypes = listOf("Freshwater", "Saltwater")
    var selectedWaterType by remember {
        mutableStateOf("Freshwater")
    }
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Create a new tank", style=MaterialTheme.typography.headlineLarge)

        Text("Tank name", style=MaterialTheme.typography.headlineMedium)
        OutlinedTextField(value = name, onValueChange = {name = it})

        Row {
            Switch(checked = knowGallons, onCheckedChange = { knowGallons = it })
            Text("I know how many gallons my tank is.", style=MaterialTheme.typography.headlineMedium)
        }

        if( knowGallons ) {
            Text("Gallons", style=MaterialTheme.typography.headlineSmall)
            OutlinedTextField(
                value = gallons,
                onValueChange = { gallons = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }else {
            Text("Length (in)", style=MaterialTheme.typography.headlineSmall)
            OutlinedTextField(
                value = length,
                onValueChange = { length = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Text("Width (in)", style=MaterialTheme.typography.headlineSmall)
            OutlinedTextField(
                value = width,
                onValueChange = { width = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Text("Height (in)", style=MaterialTheme.typography.headlineSmall)
            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            gallons = calculateGallons(length, width, height).toString()
            Text(
                "Gallons: $gallons", style=MaterialTheme.typography.headlineSmall
            )
        }

        Text("Water type", style=MaterialTheme.typography.headlineMedium)
        waterTypes.forEach {
            Row {
                RadioButton(selected = selectedWaterType == it , onClick = { selectedWaterType = it })
                Text(it, style = MaterialTheme.typography.headlineSmall)
            }
        }

        Button(onClick = {
            tankList.add(
                TankData(
                    name,
                    length.toIntOrNull(),
                    width.toIntOrNull(),
                    height.toIntOrNull(),
                    gallons.toInt(),
                    selectedWaterType
                )
            )
            navController.navigate("main")
        }) {
            Text("Create tank")
        }
    }
}

@Composable
fun ViewTankDetails(navController: NavHostController, tank: TankData?) {
    if( tank == null ) {
        Text("No tank specified.")
    } else {
        Column {
            Text(tank.name, style = MaterialTheme.typography.headlineLarge)
            Text("Volume: ${tank.gallons}G", style=MaterialTheme.typography.headlineMedium)
            if (tank.length != null && tank.width != null && tank.height != null) {
                Text("Dimensions: ${tank.length}\"L x ${tank.width}\"W x ${tank.height}\"H",
                    style=MaterialTheme.typography.headlineMedium)
            } else {
                Text("Dimensions not specified.",
                    style = MaterialTheme.typography.headlineMedium
                        .merge(TextStyle(fontStyle = FontStyle.Italic)))
            }
            Text(tank.waterType, style=MaterialTheme.typography.headlineMedium)
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
