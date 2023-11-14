import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.navigator.Navigator
import entity.Animal
import kotlinx.coroutines.launch
import network.APIService
import network.Resource
import screen.HomeScreen

@Composable
fun App() {
    MaterialTheme {
        val scope = rememberCoroutineScope()
        val dataState = remember { mutableStateOf<Resource<List<Animal>>>(Resource.Loading) }
        val refreshFlag = remember { mutableStateOf(true) }
        LaunchedEffect(refreshFlag.value) {
            scope.launch {
                dataState.value = APIService().getAnimals()
            }
        }

        Navigator(HomeScreen(dataState, refreshData = {
            println("refreshing ...")
            refreshFlag.value = !refreshFlag.value
        }))
    }
}