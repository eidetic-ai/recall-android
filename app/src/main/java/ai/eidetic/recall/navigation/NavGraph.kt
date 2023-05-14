package ai.eidetic.recall.navigation

import ai.eidetic.appaccess.ui.SignUpScreen
import ai.eidetic.common.state.deckCreationState.DeckCreationViewStore
import ai.eidetic.deckcreation.ui.CameraScreen
import ai.eidetic.deckcreation.ui.DeckCreationScreen
import ai.eidetic.deckcreation.ui.FinalizeDeckScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.koin.androidx.compose.getViewModel
import java.io.File

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    outputDirectory: File
) {
    val sharedViewModel: DeckCreationViewStore = getViewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.SignUp.route
    ) {
        composable(Screen.SignUp.route) { SignUpScreen(onLogIn = { navController.navigate(Screen.DeckCreation.route) }) }
        composable(Screen.DeckCreation.route) {
            DeckCreationScreen(
                navController = navController,
                onTakePhoto = { navController.navigate(Screen.Camera.route) },
                goToFinalizeDeck = { navController.navigate(Screen.FinalizeDeck.route) },
                viewStore = sharedViewModel
            )
        }
        composable(Screen.Camera.route) { CameraScreen(navController = navController, file = outputDirectory, viewStore = sharedViewModel) }
        composable(Screen.FinalizeDeck.route) { FinalizeDeckScreen(navController = navController,  viewStore = sharedViewModel, file = File(outputDirectory.path + "/decks")) }
    }
}