package com.angelstudios.followme.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun SimpleDialog(
    title : Int,
    successButtonText : Int,
    cancelButtonText: Int,
    body: Int,
    onDismiss: () -> Unit,
    onAction: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        content = {
            CompleteDialogContent(title, successButtonText, cancelButtonText , onDismiss, onAction, body)
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    )
}

@Composable
fun CompleteDialogContent(
    title: Int,
    successButtonText: Int,
    cancelButtonText: Int,
    onDismiss: () -> Unit,
    onAction: () -> Unit,
    body: Int,

    ) {
    Card(
        modifier = Modifier
            .fillMaxWidth(1f),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(1f),
            verticalArrangement = Arrangement.Center
        ) {
            TitleAndButton(title, onDismiss)
            AddBody(body)
            BottomButtons(successButtonText, cancelButtonText , onDismiss, onAction)
        }
    }
}

@Composable
private fun TitleAndButton(title: Int, onDismiss: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = title), fontSize = 24.sp)
            IconButton(modifier = Modifier.then(Modifier.size(24.dp)), onClick = onDismiss) {
                Icon(
                    Icons.Filled.Close,
                    "contentDescription"
                )
            }
        }
        Divider(color = Color.DarkGray, thickness = 1.dp)
    }
}

@Composable
private fun BottomButtons(successButtonText: Int,cancelButtonText :Int, onDismiss: () -> Unit, onAction: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxWidth(1f)
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onDismiss,
            modifier = Modifier
                .padding(end = 5.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = stringResource(id = cancelButtonText), fontSize = 20.sp)
        }
        Button(
            onClick = onAction,
            modifier = Modifier.padding(start = 5.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = stringResource(id = successButtonText), fontSize = 20.sp)
        }

    }
}

@Composable
private fun AddBody(content: Int) {
    Box(
        modifier = Modifier
            .padding(20.dp)
    ) {
        Text(
            text = stringResource(id = content)
        )
    }
}