package com.easycruise.recipes_compose.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easycruise.recipes_compose.presentation.ui.recipe_list.FoodCategory

@Composable
fun FoodCategoryCard(
    category: FoodCategory,
    isSelected: Boolean = false,
    onSelectedCategoryChanged: (String) -> Unit,
    onClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(6.dp)
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectedCategoryChanged(category.value)
                    onClick()
                }
            ),
        elevation = 8.dp,
        backgroundColor = if(isSelected) Color.LightGray else MaterialTheme.colors.primary
    ) {
        Text(
            text = category.value,
            style = MaterialTheme.typography.body2,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FoodCategoryCardPreview() {
    FoodCategoryCard(
        category = FoodCategory.PIZZA,
        onSelectedCategoryChanged = { },
        isSelected = false,
        onClick = { }
    )
}