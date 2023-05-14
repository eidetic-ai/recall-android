package ai.eidetic.data.di

import ai.eidetic.data.deckcreation.repository.LocalDeckCreationRepository
import ai.eidetic.data.generatelessons.api.EideticApi
import ai.eidetic.data.generatelessons.mapper.GenerateLessonsMapper
import ai.eidetic.data.generatelessons.repository.RemoteGenerateLessonsRepository
import ai.eidetic.domain.deckcreation.repository.DeckCreationRepository
import ai.eidetic.domain.generatelessons.repository.GenerateLessonsRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val dataModule = module {
    single<DeckCreationRepository> {
        LocalDeckCreationRepository(
            textDetectionService = get<ai.eidetic.service.service.TextDetectionService>(),
        )
    }

    single<EideticApi> {
        val retrofit = get<Retrofit.Builder>()
            .client(get<OkHttpClient>())
            .baseUrl("http://api.eidetic.ai/")
            .build()

        retrofit.create(EideticApi::class.java)
    }

    single<OkHttpClient>{
        OkHttpClient
            .Builder()
            .connectTimeout(300, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
            .build()
    }
    single<Retrofit.Builder> {
        Retrofit.Builder()
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
    }

    single<Json> {
        Json {
            ignoreUnknownKeys = true
        }
    }

    single<GenerateLessonsRepository> { RemoteGenerateLessonsRepository(
        eideticApi = get<EideticApi>(),
        mapper = get<GenerateLessonsMapper>()
    ) }

    single<GenerateLessonsMapper> { GenerateLessonsMapper() }
}