package screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import entity.Animals
import network.Resource
import screen.component.AnimalItem


class HomeScreen(
    private var dataState: MutableState<Resource<List<Animals>>>,
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
                        Text(text = "Error occurred: ${resource.exception.message}")
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
fun AnimalList(animals: List<Animals>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(animals) { animal ->
            AnimalItem(animal = animal)
        }
    }
}
