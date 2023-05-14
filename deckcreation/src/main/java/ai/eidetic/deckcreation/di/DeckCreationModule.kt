package ai.eidetic.deckcreation.di

import ai.eidetic.common.state.deckCreationState.DeckCreationViewStore
import ai.eidetic.domain.deckcreation.usecase.GetParagraphsFromImages
import ai.eidetic.domain.generatelessons.usecase.GenerateDeck
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val deckCreationModule = module {
    viewModel<DeckCreationViewStore> {
        DeckCreationViewStore(
            getParagraphsFromImages = get<GetParagraphsFromImages>(),
            generateDeck = get<GenerateDeck>()
        )
    }
}