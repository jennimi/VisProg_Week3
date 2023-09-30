package com.example.visprog_week3.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TriangleView() {

    var base by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("")}
    var area by remember { mutableStateOf(0.0) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image (
            imageVector = Icons.Outlined.PlayArrow,
            contentDescription = "Triangle",
            modifier = Modifier
                .size(150.dp)
                .rotate(270f)
        )
        CustomTextField(
            value = base,
            onValueChange = {base = it},
            text = "Base",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        CustomTextField(
            value = height,
            onValueChange = {height = it},
            text = "Height",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Text (
            text = "The Triangle Area is:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp)
        )
        Text (
            text = "$area",
            fontSize = 45.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(0.dp)
        )
        Spacer(modifier = Modifier.height(100.dp))
    }

    DisposableEffect(base, height) {
        val baseValue = base.toDoubleOrNull()
        val heightValue = height.toDoubleOrNull()

        if (baseValue != null && heightValue != null) {
            val calculatedArea = (baseValue * heightValue) / 2
            area = calculatedArea
        }

        onDispose { }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    text: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
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
        colors = colors,
        shape = shape,
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TrianglePreview() {
    TriangleView()
}

