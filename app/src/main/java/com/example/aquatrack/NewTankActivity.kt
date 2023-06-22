package com.example.aquatrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.aquatrack.ui.theme.AquaTrackTheme

class NewTankActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AquaTrackTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CreateNewTank()
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    @Preview
    fun CreateNewTank() {
        val length = remember { mutableStateOf(0) }
        val width = remember { mutableStateOf(0) }
        val height = remember { mutableStateOf(0) }
        Column() {
            Text("Create a new tank")


            Text("Length (left to right)")
            TextField(value=TextFieldValue(length.value.toString()), onValueChange = { length.value = it.text.toInt()})
            Text("Width (front to back)")
            TextField(value=TextFieldValue(width.value.toString()), onValueChange = { width.value = it.text.toInt()})
            Text("Height (top to bottom)")
            TextField(value=TextFieldValue(height.value.toString()), onValueChange = { height.value = it.text.toInt()})
            Text("Gallons: ${
                (length.value * width.value * height.value) / 231
            }")
        }
    }
}