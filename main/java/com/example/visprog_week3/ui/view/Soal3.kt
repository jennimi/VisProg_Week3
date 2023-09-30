@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.visprog_week3.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.visprog_week3.R
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgeCalculatorView() {

    var name by rememberSaveable { mutableStateOf("") }
    var year by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("")}

    var calculateyes by rememberSaveable { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold (
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE981FF))
            ) {
                Text(
                    text = "Age Calculator",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.face),
                    contentDescription = "Haa",
                    modifier = Modifier
                        .size(120.dp)
                )
                CustomTextFieldPink(
                    value = name,
                    onValueChange = { name = it },
                    text = "Enter your name",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                CustomTextFieldPink(
                    value = year,
                    onValueChange = { year = it },
                    text = "Enter your birth year",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Button(
                    onClick = {
                        val yearValue = year.toDoubleOrNull()


                        if (name != "" && yearValue != null) {

                            if (yearValue > 2023 || yearValue < 0) {
                                calculateyes = false
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Wait, that doesn't make sense."
                                    )
                                }
                            } else {
                                val yearInt = yearValue.toInt()
                                age = ((2023 - yearInt).toString())
                                calculateyes = true
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Hi, $name! Your Age is $age years"
                                    )
                                }
                            }
                        } else {
                            calculateyes = false
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "Make sure to enter all the datas"
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = Color(0xFFCBCBCB),
                        containerColor = Color(0xFFE981FF),
                        disabledContentColor = Color(0xFF040404),
                        contentColor = Color(0xFF040404),
                    ),
                ) {
                    Text (
                        text = "CALCULATE YOUR AGE",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(vertical = 15.dp)
                    )
                }
                if (calculateyes) {
                    Button(
                        onClick = {
                            calculateyes = false
                        },
                        modifier = Modifier
                            .padding()
                            .border(1.dp, Color(0xFFE981FF), shape = RoundedCornerShape(30.dp)),
                        colors = ButtonDefaults.buttonColors(
                            disabledContainerColor = Color(0xFFCBCBCB),
                            containerColor = Color.White,
                            disabledContentColor = Color(0xFF040404),
                            contentColor = Color(0xFF040404),
                        ),
                    ) {
                        Text (
                            text = "Hi, $name! Your Age is $age years",
                            color = Color(0xFFE981FF),
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                        )
                    }


                }
            }

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextFieldPink (
    value: String,
    onValueChange: (String) -> Unit,
    text: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(26.dp)
    val colors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color(0xFFE981FF),
        unfocusedBorderColor = Color(0xFFE981FF),
        cursorColor = Color(0xFFE981FF),
        textColor = Color(0xFFE981FF)
    )

    OutlinedTextField (
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = text) },
        keyboardOptions = keyboardOptions,
        colors = colors,
        shape = shape,
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AgeCalculatorPreview() {
    AgeCalculatorView()
}