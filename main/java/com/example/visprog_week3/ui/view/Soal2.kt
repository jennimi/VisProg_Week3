package com.example.visprog_week3.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.visprog_week3.R


@Composable
fun BMIView() {

    var weight by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }
    var bmicategory by rememberSaveable { mutableStateOf("") }
    var bmiscore by remember { mutableStateOf(0.0) }

    var isWeightValid by rememberSaveable { mutableStateOf(true) }
    var isHeightValid by rememberSaveable { mutableStateOf(true) }

    var showPopup by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.head),
            contentDescription = "Icon",
            modifier = Modifier
                .size(120.dp)
        )
        CustomWeightField(
            value = weight,
            onValueChange = { weight = it },
            text = "Weight in kg",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            isWeightValid = isWeightValid
        )
        CustomHeightField(
            value = height,
            onValueChange = { height = it },
            text = "Height in cm",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            isHeightValid = isHeightValid
        )

        DisposableEffect(weight, height) {

            val weightNull = weight.toDoubleOrNull()
            val heightNull = height.toDoubleOrNull()

            if (weightNull!= null && heightNull != null) {
                val weightValue = weight.toDouble()
                val heightValue = height.toDouble()

                isWeightValid = isValidNumber(weightValue)
                isHeightValid = isValidNumber(heightValue)
            } else if (weightNull == null && heightNull == null) {
                isWeightValid = false
                isHeightValid = false
            } else if (weightNull != null) {
                val weightValue = weight.toDouble()
                isWeightValid = isValidNumber(weightValue)
            } else if (heightNull != null) {
                val heightValue = height.toDouble()
                isHeightValid = isValidNumber(heightValue)
            }

            onDispose { }
        }

        Button(
            onClick = {
                val weightValue = weight.toDouble()
                val heightValueCm = height.toDouble()

                val heightValueM = heightValueCm/100.0

                val calculatebmi = (weightValue/(heightValueM*heightValueM))
                bmiscore = calculatebmi

                if (bmiscore < 18.5) {
                    bmicategory = "Underweight"
                } else if (bmiscore < 24.9) {
                    bmicategory = "Normal weight"
                } else if (bmiscore < 29.9) {
                    bmicategory = "Overweight"
                } else if (bmiscore < 34.9) {
                    bmicategory = "Obese"
                } else if (bmiscore > 35) {
                    bmicategory = "Extremely Obese"
                }

                if (isWeightValid && isHeightValid) {
                    showPopup = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = Color(0xFFCBCBCB),
                containerColor = Color(0xFF75FAFC),
                disabledContentColor = Color(0xFF040404),
                contentColor = Color(0xFF040404)
            ),
            enabled = isWeightValid && isHeightValid,
        ) {
            Text(text = "Submit")
        }

        if (showPopup) {
            AlertDialog (
                onDismissRequest = {
                    showPopup = false
                },
                title = {
                    Text(text = "Your BMI Analysis")
                },
                text = {
                    Text(text = "Your Height: $height\nYour Weight: $weight\nYour BMI Score: $bmiscore\nYou are $bmicategory")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showPopup = false
                        }
                    ) {
                        Text(text = "OK")
                    }
                }
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomWeightField(
    value: String,
    onValueChange: (String) -> Unit,
    text: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    isWeightValid: Boolean
) {
    val shape = RoundedCornerShape(26.dp)
    val colors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color.Blue,
        unfocusedBorderColor = Color.Blue,
        cursorColor = Color.Blue,
    )

    OutlinedTextField (
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = text) },
        keyboardOptions = keyboardOptions,
        textStyle = TextStyle(color = Color.Blue),
        colors = colors,
        shape = shape,
        modifier = modifier,
        isError = !isWeightValid
    )

    if (!isWeightValid) {
        Text (
            text = "Please enter a valid weight greater than 0.",
            color = Color.Red,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomHeightField(
    value: String,
    onValueChange: (String) -> Unit,
    text: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    isHeightValid: Boolean
) {
    val shape = RoundedCornerShape(26.dp)
    val colors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color.Blue,
        unfocusedBorderColor = Color.Blue,
        cursorColor = Color.Blue,
    )

    OutlinedTextField (
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = text) },
        keyboardOptions = keyboardOptions,
        textStyle = TextStyle(color = Color.Blue),
        colors = colors,
        shape = shape,
        modifier = modifier,
        isError = !isHeightValid
    )

    if (!isHeightValid) {
        Text (
            text = "Please enter a valid height greater than 0.",
            color = Color.Red,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

fun isValidNumber(number: Double): Boolean {
    return number > 0
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BMIPreview() {
    BMIView()
}