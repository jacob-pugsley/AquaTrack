package com.example.aquatrack.ui.views

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.example.aquatrack.TankData
import com.example.aquatrack.WaterRecord
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoField

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun RecordMeasurement(navController: NavHostController, tankId: String?) {

    //todo: get tank details from database using the tankId parameter

//    if( tank == null ) {
//        Text("No tank specified.")
//        return
//    }

    val parameters = listOf(
        "Ammonia", "Nitrate", "Nitrite", "pH", "gH", "kH"
    )


    var nowChecked by remember {
        mutableStateOf(false)
    }

    val tmp = mutableStateMapOf<String, Boolean>()
    parameters.forEach {
        tmp[it] = true
    }

    var takingMeasurementChecked = remember {
        tmp
    }

    var waterRecord = remember {
        mutableStateOf(WaterRecord("", 0, 0, 0, 0, 0, 0))
    }

    val localDate = LocalDateTime.now().atZone(ZoneId.systemDefault())
    val hour = localDate.get(ChronoField.HOUR_OF_AMPM)
    val minute = localDate.minute
    val second = localDate.second
    val amPm = if (localDate.get(ChronoField.AMPM_OF_DAY) == 0) "AM" else "PM"

    val day = localDate.dayOfMonth
    val month = localDate.monthValue
    val year = localDate.year

    var mTime by remember { mutableStateOf("${hour}:${minute}:${second} $amPm") }


    var currentDate by remember {
        mutableStateOf("${month}/${day}/${year}")
    }

    Column {
        Text("Record water parameters")
        Text("Time of measurement: ")

        Column{
            /*
            Pick the time that the measurement was taken, defaulting to the current time.
             */
            var mTimePickerDialog: TimePickerDialog? = null
            var datePickerDialog: DatePickerDialog? = null


            //manual date and time entry
            datePickerDialog = DatePickerDialog(
                LocalContext.current,
                { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                    currentDate = "${mMonth}/${mDayOfMonth}/${mYear}"
                }, year, month-1, day
            )

            mTimePickerDialog = TimePickerDialog(
                LocalContext.current,
                { _, mHour: Int, mMinute: Int ->
                    mTime = "${mHour}:${mMinute}:${second} $amPm"
                }, hour, minute, false
            )

            Row {
                Button(onClick = { datePickerDialog.show() }) {
                    Text("Pick date")
                }

                Text("Selected date: $currentDate")
            }

            Row {
                Button(onClick = { mTimePickerDialog.show() }) {
                    Text("Pick time")
                }

                Text("Selected time: $mTime")
            }

            parameters.forEach {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(it)
                    val currentVal = when(it) {
                        "Ammonia" -> waterRecord.value.ammonia
                        "Nitrite" -> waterRecord.value.nitrite
                        "Nitrate" -> waterRecord.value.nitrate
                        "pH" -> waterRecord.value.ph
                        "gH" -> waterRecord.value.gh
                        "kH" -> waterRecord.value.kh

                        else -> {null}
                    }
                    OutlinedTextField(
                        value = currentVal.toString(),
                        onValueChange = {param ->
                            when(it) {
                                "Ammonia" -> waterRecord.value.ammonia = param.toInt()
                                "Nitrite" -> waterRecord.value.nitrite = param.toInt()
                                "Nitrate" -> waterRecord.value.nitrate = param.toInt()
                                "pH" -> waterRecord.value.ph = param.toInt()
                                "gH" -> waterRecord.value.gh = param.toInt()
                                "kH" -> waterRecord.value.kh = param.toInt()

                                else -> {null}
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    takingMeasurementChecked[it]?.let { it1 ->
                        Switch(checked = it1, onCheckedChange = { checked ->
                            takingMeasurementChecked[it] = checked
                            //if (!checked) parameterMap[it] = -1 else parameterMap[it] = parameterMap.getOrDefault(it, 0)
                            when(it) {
                                "Ammonia" -> if(!checked) waterRecord.value.ammonia = waterRecord.value.ammonia?.times(
                                    -1
                                )
                                "Nitrite" -> if(!checked) waterRecord.value.nitrite = waterRecord.value.nitrite?.times(
                                    -1
                                )
                                "Nitrate" -> if(!checked) waterRecord.value.nitrate = waterRecord.value.nitrate?.times(
                                    -1
                                )
                                "pH" -> if(!checked) waterRecord.value.ph = waterRecord.value.ph?.times(
                                    -1
                                )
                                "gH" -> if(!checked) waterRecord.value.gh = waterRecord.value.gh?.times(
                                    -1
                                )
                                "kH" -> if(!checked) waterRecord.value.kh = waterRecord.value.ammonia?.times(
                                    -1
                                )

                                else -> {null}
                            }
                        } )
                    }
                }
            }
        }
        //add the measurements to the tank, with the date and time of measurement as a key
//        Button(onClick = {
//            waterRecord.value.date = "$currentDate $mTime"
//            tank.waterRecords.add(waterRecord.value)
//            navController.popBackStack()
//        }
//
//        ) {
//            Text("Submit new record")
//        }
    }
}