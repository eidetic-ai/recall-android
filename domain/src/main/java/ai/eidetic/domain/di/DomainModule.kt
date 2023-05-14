package ai.eidetic.domain.di

import ai.eidetic.domain.deckcreation.repository.DeckCreationRepository
import ai.eidetic.domain.deckcreation.usecase.GetParagraphsFromImages
import ai.eidetic.domain.generatelessons.repository.GenerateLessonsRepository
import ai.eidetic.domain.generatelessons.usecase.GenerateDeck
import org.koin.dsl.module

val domainModule = module {
    single<GetParagraphsFromImages> {
        GetParagraphsFromImages.Default(
            repository = get<DeckCreationRepository>()
        )
    }

    single<GenerateDeck> {
        GenerateDeck.Default(
            repository = get<GenerateLessonsRepository>()
        )
    }
}