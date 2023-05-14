package ai.eidetic.recall.navigation

sealed class Screen(val route: String){
    object SignUp: Screen(route = "sing-up-screen")
    object DeckCreation: Screen(route = "deck-creation-screen")
    object Camera: Screen(route = "camera-screen")
    object FinalizeDeck: Screen(route = "finalize-deck-screen")
    object DeckSelection: Screen(route = "deck-selection-screen")
    object Learning: Screen(route = "learning-screen")
    object Level: Screen(route = "level-screen")
}
