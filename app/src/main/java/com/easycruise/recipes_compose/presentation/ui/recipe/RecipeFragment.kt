package com.easycruise.recipes_compose.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.easycruise.recipes_compose.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color(ContextCompat.getColor(LocalContext.current, R.color.blue_700))
                        )
                        .verticalScroll(
                            state = rememberScrollState()
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_ice_cream),
                        contentDescription = null,
                        Modifier.height(300.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Happy meal",
//                color = Color.White,
                                fontSize = 26.sp
                            )
                            Text(text = "$5.99",
                                style = TextStyle(
                                    color = Color(ContextCompat.getColor(LocalContext.current, R.color.blue_500)),
                                    fontSize = 17.sp
                                ),
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                        Spacer(modifier = Modifier.padding(top = 10.dp))
                        Text(
                            text = "800 Calories",
                            fontSize = 17.sp
                        )
                        Spacer(modifier = Modifier.padding(top = 10.dp))
                        Button(
                            onClick = {},
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                        ) {
                            Text(text = LocalContext.current.getString(R.string.label_order_now))
                        }
                    }
                }
            }
        }
    }
}