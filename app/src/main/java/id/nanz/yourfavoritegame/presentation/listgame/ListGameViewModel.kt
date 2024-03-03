package id.nanz.yourfavoritegame.presentation.listgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.nanz.yourfavoritegame.core.domain.usecase.GameUseCase

class ListGameViewModel(private val gameUseCase: GameUseCase): ViewModel() {

    fun getListGame() = gameUseCase.getGames().asLiveData()

    fun searchGame(query: String) = gameUseCase.searchGame(query).asLiveData()

}