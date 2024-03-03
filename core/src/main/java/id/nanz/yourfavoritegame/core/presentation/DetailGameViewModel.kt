package id.nanz.yourfavoritegame.core.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.nanz.yourfavoritegame.core.domain.model.Game
import id.nanz.yourfavoritegame.core.domain.usecase.GameUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailGameViewModel(private val gameUseCase: GameUseCase): ViewModel() {

    val isSetFavorite: MutableLiveData<Boolean> = MutableLiveData()

    fun getDetailGame(gameId: Int) = gameUseCase.getDetailGame(gameId).asLiveData()

    fun setFavoriteGame(game: Game) {
        viewModelScope.launch {
            val newStatus = gameUseCase.getIsSetFavorite(game.gameId).first().not()
            isSetFavorite.postValue(newStatus)
            gameUseCase.setFavoriteGame(game, newStatus)
        }
    }

    fun getFavoriteById(gameId: Int) {
        viewModelScope.launch {
            val favoriteStatus = gameUseCase.getIsSetFavorite(gameId).first()
            isSetFavorite.postValue(favoriteStatus)
        }
    }

}