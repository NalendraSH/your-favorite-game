package id.nanz.yourfavoritegame.di

import id.nanz.yourfavoritegame.core.domain.usecase.GameUseCase
import id.nanz.yourfavoritegame.core.domain.usecase.GameUseCaseImpl
import id.nanz.yourfavoritegame.core.presentation.DetailGameViewModel
import id.nanz.yourfavoritegame.presentation.listgame.ListGameViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<GameUseCase> { GameUseCaseImpl(get()) }
}

val viewModelModule = module {
    viewModel { ListGameViewModel(get()) }
    viewModel { DetailGameViewModel(get()) }
}