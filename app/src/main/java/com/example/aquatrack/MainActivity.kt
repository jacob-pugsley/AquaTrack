package com.example.aquatrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aquatrack.ui.theme.AquaTrackTheme
import com.example.aquatrack.ui.views.CreateNewTank
import com.example.aquatrack.ui.views.RecordMeasurement
import com.example.aquatrack.ui.views.TankDetailsView
import com.example.aquatrack.ui.views.TankListView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AquaTrackTheme {
                // A surface container using the 'background' color from the theme
                //setting up navigation for the rest of the app
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "tanklist") {
                    composable("tanklist") {TankListView(navController)}

                    composable(
                        "tankdetails/{tankid}",
                        arguments = listOf(navArgument("tankid") { type = NavType.StringType })
                    ) { backStackEntry ->
                        TankDetailsView(navController, backStackEntry.arguments?.getString("tankid"))
                    }

                    composable("addtank") { CreateNewTank(navController) }

                    composable(
                        "addrecord/{tankid}",
                        arguments=listOf(navArgument("tankid"){type= NavType.StringType})
                    ) {
                        RecordMeasurement(navController, it.arguments?.getString("tankid") )
                    }
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}
