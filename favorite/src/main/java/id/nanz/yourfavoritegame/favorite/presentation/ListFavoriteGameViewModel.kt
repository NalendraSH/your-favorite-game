package id.nanz.yourfavoritegame.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.nanz.yourfavoritegame.core.domain.usecase.GameUseCase

class ListFavoriteGameViewModel(gameUseCase: GameUseCase): ViewModel() {

    val favoriteGame = gameUseCase.getFavoriteGame().asLiveData()

}