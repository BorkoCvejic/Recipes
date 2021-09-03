package com.easycruise.recipes_compose.presentation.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun MyBottomBar(

) {
    BottomNavigation(
        elevation = 12.dp
    ) {
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
            selected = false,
            onClick = {

            },
        )
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
            selected = false,
            onClick = {

            }
        )
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null) },
            selected = false,
            onClick = {

            }
        )
    }
}