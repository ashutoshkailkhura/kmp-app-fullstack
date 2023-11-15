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
import ui.screen.HomeScreen
import ui.theme.ReplyTheme

@Composable
fun App() {
    ReplyTheme(

    ) {
        val scope = rememberCoroutineScope()
        val dataState = remember { mutableStateOf<Resource<List<Animal>>>(Resource.Loading) }
        val refreshFlag = remember { mutableStateOf(true) }

        LaunchedEffect(refreshFlag.value) {
            scope.launch {
                dataState.value = Resource.Loading
                try {
                    dataState.value = APIService().getAnimals()
                } catch (ex: Exception) {
                    dataState.value = Resource.Error(ex)
                }
            }
        }

        Navigator(HomeScreen(dataState, refreshData = {
            refreshFlag.value = !refreshFlag.value
        }))
    }
}