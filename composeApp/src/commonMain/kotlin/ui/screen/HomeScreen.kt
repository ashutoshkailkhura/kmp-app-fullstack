package ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import entity.Animal
import network.Resource
import ui.screen.component.AnimalItem


class HomeScreen(
    private var dataState: MutableState<Resource<List<Animal>>>,
    private var refreshData: () -> Unit
) : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navigator.push(AddAnimalScreen())
                    },
                ) {
                    Icon(Icons.Filled.Add, "Floating action button.")
                }
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (val resource = dataState.value) {

                    is Resource.Success -> {
                        if (resource.data.isEmpty()) {
                            Text(text = "No Data found")
                        } else {
                            Button(
                                onClick = refreshData
                            ) {
                                Text("Refresh")
                            }
                            AnimalList(resource.data)
                        }
                    }

                    is Resource.Error -> {
                        Text(
                            text = "${resource.exception.message}",
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Icon(
                            Icons.Rounded.Warning,
                            contentDescription = "no network",
                            tint = Color.Yellow,
                        )
                    }

                    is Resource.Loading -> {
                        CircularProgressIndicator()
                    }

                }
            }
        }
    }
}

@Composable
fun AnimalList(animals: List<Animal>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(animals) { animal ->
            AnimalItem(animal = animal)
        }
    }
}
