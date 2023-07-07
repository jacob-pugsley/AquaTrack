package com.example.aquatrack.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.inventory.ui.AppViewModelProvider
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewTank(navController: NavHostController) {
    var knowGallons by remember {
        mutableStateOf(true)
    }
    val waterTypes = listOf("Freshwater", "Saltwater")
    var selectedWaterType by remember {
        mutableStateOf("Freshwater")
    }

    val viewModel: TankEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val tankUiState = viewModel.itemUiState
    val itemDetails = tankUiState.itemDetails

    val onItemValueChange = viewModel::updateUiState

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Create a new tank", style= MaterialTheme.typography.headlineLarge)

        Text("Tank name", style= MaterialTheme.typography.headlineMedium)
        OutlinedTextField(
            value = itemDetails.name,
            onValueChange ={onItemValueChange(itemDetails.copy(name = it))}
        )

        Row {
            Switch(checked = knowGallons, onCheckedChange = { knowGallons = it })
            Text("I know how many gallons my tank is.", style= MaterialTheme.typography.headlineMedium)
        }

        if( knowGallons ) {
            Text("Gallons", style= MaterialTheme.typography.headlineSmall)
            OutlinedTextField(
                value = tankUiState.itemDetails.gallons.toString(),
                onValueChange = { onItemValueChange(itemDetails.copy(gallons = it.toInt()))},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }else {
            Text("Length (in)", style= MaterialTheme.typography.headlineSmall)
            OutlinedTextField(
                value = itemDetails.length.toString(),
                onValueChange = { onItemValueChange(itemDetails.copy(length = it.toInt()))},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Text("Width (in)", style= MaterialTheme.typography.headlineSmall)
            OutlinedTextField(
                value = itemDetails.width.toString(),
                onValueChange = { onItemValueChange(itemDetails.copy(width = it.toInt())) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Text("Height (in)", style= MaterialTheme.typography.headlineSmall)
            OutlinedTextField(
                value = itemDetails.height.toString(),
                onValueChange = { onItemValueChange(itemDetails.copy(height = it.toInt()))},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            val gallons = calculateGallons(itemDetails.length, itemDetails.width, itemDetails.height)
            onItemValueChange(itemDetails.copy(gallons = gallons))
            Text(
                "Gallons: $gallons", style= MaterialTheme.typography.headlineSmall
            )
        }

        Text("Water type", style= MaterialTheme.typography.headlineMedium)
        waterTypes.forEach {
            Row {
                RadioButton(selected = selectedWaterType == it , onClick = { selectedWaterType = it })
                Text(it, style = MaterialTheme.typography.headlineSmall)
            }
        }

        val coroutineScope = rememberCoroutineScope()

        Button(onClick = {
            coroutineScope.launch {
                viewModel.saveItem()
            }
            navController.navigate("tanklist")
        }) {
            Text("Create tank")
        }
    }
}

fun calculateGallons(length: Int, width: Int, height: Int): Int {
    return (length * width * height) / 231
}