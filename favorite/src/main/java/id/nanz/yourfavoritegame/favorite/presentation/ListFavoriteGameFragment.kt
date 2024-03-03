package id.nanz.yourfavoritegame.favorite.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController
import id.nanz.yourfavoritegame.core.domain.model.Game
import id.nanz.yourfavoritegame.core.presentation.GameAdapter
import id.nanz.yourfavoritegame.favorite.databinding.FragmentListFavoriteGameBinding
import id.nanz.yourfavoritegame.favorite.di.favoriteModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class ListFavoriteGameFragment : Fragment(), GameAdapter.GameAdapterListener {

    private val listFavoriteGameViewModel: ListFavoriteGameViewModel by viewModel()
    private lateinit var binding: FragmentListFavoriteGameBinding

    private val gameAdapter: GameAdapter by lazy { GameAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListFavoriteGameBinding.inflate(inflater, container, false)
        loadKoinModules(favoriteModule)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFavoriteGames.adapter = gameAdapter
        listFavoriteGameViewModel.favoriteGame.observe(viewLifecycleOwner) { games ->
            gameAdapter.submitList(games)
            binding.viewEmpty.root.isVisible = games.isEmpty()
        }

        binding.toolbarListFavorite.setNavigationOnClickListener {
            view.findNavController().navigateUp()
        }
    }

    override fun onClickItem(data: Game) {
        val toDetailGame = NavDeepLinkRequest.Builder
            .fromUri(
                "android-app://id.nanz.yourfavoritegame.core/detail_game_fragment/${data.gameId}".toUri()
            )
            .build()
        view?.findNavController()?.navigate(toDetailGame)
    }

}