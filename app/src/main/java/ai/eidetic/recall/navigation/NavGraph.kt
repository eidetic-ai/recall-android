package ai.eidetic.recall.navigation

import ai.eidetic.appaccess.ui.SignUpScreen
import ai.eidetic.common.state.deckCreationState.DeckCreationViewStore
import ai.eidetic.common.state.learning.LearningViewStore
import ai.eidetic.common.utils.loadDataFromJson
import ai.eidetic.deckcreation.ui.CameraScreen
import ai.eidetic.deckcreation.ui.DeckCreationScreen
import ai.eidetic.deckcreation.ui.FinalizeDeckScreen
import ai.eidetic.learning.ui.DeckSelectionScreen
import ai.eidetic.learning.ui.LearningScreen
import ai.eidetic.learning.ui.LevelScreen
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

    val sharedLearningViewModel: LearningViewStore = getViewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.SignUp.route
    ) {
        composable(Screen.SignUp.route) {
            SignUpScreen(onLogIn = {
                if (loadDataFromJson(File(outputDirectory.path + "/decks")).isEmpty()) {
                    navController.navigate(Screen.DeckCreation.route)
                } else {
                    navController.navigate(Screen.DeckSelection.route)
                }
            })
        }
        composable(Screen.DeckCreation.route) {
            DeckCreationScreen(
                navController = navController,
                onTakePhoto = { navController.navigate(Screen.Camera.route) },
                goToFinalizeDeck = { navController.navigate(Screen.FinalizeDeck.route) },
                viewStore = sharedViewModel
            )
        }
        composable(Screen.Camera.route) { CameraScreen(navController = navController, file = outputDirectory, viewStore = sharedViewModel) }
        composable(Screen.FinalizeDeck.route) {
            FinalizeDeckScreen(
                navController = navController,
                onFinishClicked = { navController.navigate(Screen.DeckSelection.route) },
                viewStore = sharedViewModel,
                file = File(outputDirectory.path + "/decks")
            )
        }
        composable(Screen.DeckSelection.route) {
            DeckSelectionScreen(
                navController = navController,
                onDeckSelected = { navController.navigate(Screen.Learning.route) },
                onAddMore = { navController.navigate(Screen.DeckCreation.route) },
                file = File(outputDirectory.path + "/decks"),
                viewStore = sharedLearningViewModel
            )
        }
        composable(Screen.Learning.route) {
            LearningScreen(
                navController = navController,
                viewStore = sharedLearningViewModel,
                onStartLevelClicked = { navController.navigate(Screen.Level.route) }
            )
        }
        composable(Screen.Level.route) {
            LevelScreen(
                navController = navController,
                viewStore = sharedLearningViewModel,
            )
        }
    }
}