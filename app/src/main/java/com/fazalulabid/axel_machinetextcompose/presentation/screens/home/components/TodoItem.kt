package com.fazalulabid.axel_machinetextcompose.presentation.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fazalulabid.axel_machinetextcompose.domain.model.Todo
import com.fazalulabid.axel_machinetextcompose.presentation.ui.theme.SpaceMedium

@Composable
fun TodoItem(
    modifier: Modifier = Modifier,
    todo: Todo
) {
    val completedColor = Color(0xFF61C0BF)
    val incompleteColor = Color(0xFFF38181)

    val textColor = if (todo.completed == true) {
        completedColor
    } else {
        incompleteColor
    }

    val textDecoration = if (todo.completed == true) {
        TextDecoration.LineThrough
    } else {
        TextDecoration.None
    }

    Column {
        Text(
            text = todo.title ?: "",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textDecoration = textDecoration,
            color = textColor,
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceMedium)
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.5.dp,
            color = Color.LightGray
        )
    }
}