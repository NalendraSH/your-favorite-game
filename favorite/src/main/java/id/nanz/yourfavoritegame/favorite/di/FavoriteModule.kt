package id.nanz.yourfavoritegame.favorite.di

import id.nanz.yourfavoritegame.favorite.presentation.ListFavoriteGameViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { ListFavoriteGameViewModel(get()) }
}