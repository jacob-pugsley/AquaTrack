package com.example.aquatrack.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.inventory.ui.AppViewModelProvider


@Composable
fun TankListView(navController: NavHostController) {
    Column (
        verticalArrangement = Arrangement.spacedBy(50.dp)
    ){
        Text("AquaTrack", style = TextStyle(fontSize = 50.sp))

        //todo: grab tank list from database
        val viewModel: TankListViewModel = viewModel(factory = AppViewModelProvider.Factory)
        val tankListUiState by viewModel.tankListUiState.collectAsState()

        tankListUiState.itemList.map {
            Button(
                onClick = { navController.navigate("tankDetails/${it.id}") },
                shape = RectangleShape,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "${it.name} (${it.gallons}G) >", style = TextStyle(fontSize = 50.sp))
            }
        }


        Text("+ Add new tank", style= TextStyle(fontSize = 50.sp, color = Color.Blue, textDecoration = TextDecoration.Underline), modifier = Modifier.clickable(true, onClick = {navController.navigate("createNewTank")}))
    }



}
