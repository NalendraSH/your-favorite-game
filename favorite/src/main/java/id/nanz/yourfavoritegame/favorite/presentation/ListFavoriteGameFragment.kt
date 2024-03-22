package id.nanz.yourfavoritegame.favorite.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController
import id.nanz.yourfavoritegame.core.domain.model.Game
import id.nanz.yourfavoritegame.core.presentation.BaseFragment
import id.nanz.yourfavoritegame.core.presentation.GameAdapter
import id.nanz.yourfavoritegame.favorite.databinding.FragmentListFavoriteGameBinding
import id.nanz.yourfavoritegame.favorite.di.favoriteModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class ListFavoriteGameFragment : BaseFragment<FragmentListFavoriteGameBinding>(), GameAdapter.GameAdapterListener {

    private lateinit var bindingImpl: FragmentListFavoriteGameBinding
    private val listFavoriteGameViewModel: ListFavoriteGameViewModel by viewModel()
    private val gameAdapter: GameAdapter by lazy { GameAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentListFavoriteGameBinding.inflate(inflater, container, false), this) {
        bindingImpl = this
        loadKoinModules(favoriteModule)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingImpl.rvFavoriteGames.adapter = gameAdapter
        listFavoriteGameViewModel.favoriteGame.observe(viewLifecycleOwner) { games ->
            gameAdapter.submitList(games)
            bindingImpl.viewEmpty.root.isVisible = games.isEmpty()
        }

        bindingImpl.toolbarListFavorite.setNavigationOnClickListener { v ->
            v.findNavController().navigateUp()
        }
    }

    override fun onClickItem(data: Game, view: View) {
        val toDetailGame = NavDeepLinkRequest.Builder
            .fromUri(
                "android-app://id.nanz.yourfavoritegame.core/detail_game_fragment/${data.gameId}".toUri()
            )
            .build()
        view.findNavController().navigate(toDetailGame)
    }

    override fun destroyView() {
        bindingImpl.rvFavoriteGames.adapter = null
    }

}