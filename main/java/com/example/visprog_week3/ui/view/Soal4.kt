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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun studentScoreView() {

    var shawnscore by rememberSaveable { mutableStateOf("") }
    var petescore by rememberSaveable { mutableStateOf("") }
    var ardhitoscore by rememberSaveable { mutableStateOf("") }

    var average by rememberSaveable { mutableStateOf("") }
    var statussiswa by rememberSaveable { mutableStateOf("") }

    var calculateyes by rememberSaveable { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold (
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFB19CD9))
            ) {
                Text(
                    text = "StudentScore",
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
                    painter = painterResource(id = R.drawable.universitasciputra),
                    contentDescription = "Logo UC",
                    modifier = Modifier
                        .size(120.dp)
                )
                CustomTextFieldPurple(
                    value = shawnscore,
                    onValueChange = { shawnscore = it },
                    text = "Shawn's Score",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                CustomTextFieldPurple(
                    value = petescore,
                    onValueChange = { petescore = it },
                    text = "Pete's Score",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                CustomTextFieldPurple(
                    value = ardhitoscore,
                    onValueChange = { ardhitoscore = it },
                    text = "Ardhito's Score",
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
                        val shawnscoreValue = shawnscore.toDoubleOrNull()
                        val petescoreValue = petescore.toDoubleOrNull()
                        val ardhitoscoreValue = ardhitoscore.toDoubleOrNull()


                        if (shawnscoreValue != null && petescoreValue != null && ardhitoscoreValue != null) {

                            if (shawnscoreValue > 100 || petescoreValue > 100 || ardhitoscoreValue > 100) {
                                calculateyes = false
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Score tidak bisa diatas 100"
                                    )
                                }
                            } else if (shawnscoreValue < 0 || petescoreValue < 0 || ardhitoscoreValue < 0) {
                                calculateyes = false
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Score tidak bisa dibawah 0"
                                    )
                                }
                            } else {
                                calculateyes = true

                                val averageValue = (shawnscoreValue + petescoreValue + ardhitoscoreValue)/3

                                average = averageValue.toString()

                                if (averageValue >= 70) {
                                    statussiswa = "Siswa mengerti pembelajaran."
                                } else {
                                    statussiswa = "Siswa perlu diberi soal tambahan."
                                }

                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "$statussiswa"
                                    )
                                }
                            }
                        } else {
                            calculateyes = false
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "Pastikan semua score diisi"
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = Color(0xFFCBCBCB),
                        containerColor = Color(0xFFB19CD9),
                        disabledContentColor = Color(0xFF040404),
                        contentColor = Color(0xFF040404),
                    ),
                ) {
                    Text (
                        text = "CALCULATE AVERAGE",
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
                            text = "Average Score: $average",
                            color = Color(0xFFB19CD9),
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
fun CustomTextFieldPurple (
    value: String,
    onValueChange: (String) -> Unit,
    text: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(26.dp)
    val colors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color(0xFFB19CD9),
        unfocusedBorderColor = Color(0xFFB19CD9),
        cursorColor = Color(0xFFB19CD9),
        textColor = Color(0xFFB19CD9)
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
fun studentScorePreview() {
    studentScoreView()
}