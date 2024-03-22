package id.nanz.yourfavoritegame.presentation.listgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.net.toUri
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController
import id.nanz.yourfavoritegame.core.data.Resource
import id.nanz.yourfavoritegame.core.domain.model.Game
import id.nanz.yourfavoritegame.core.presentation.BaseFragment
import id.nanz.yourfavoritegame.core.presentation.GameAdapter
import id.nanz.yourfavoritegame.core.presentation.gone
import id.nanz.yourfavoritegame.core.presentation.visible
import id.nanz.yourfavoritegame.databinding.FragmentListGameBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import id.nanz.yourfavoritegame.R as AppRes
import id.nanz.yourfavoritegame.core.R as CoreRes

class ListGameFragment : BaseFragment<FragmentListGameBinding>(), GameAdapter.GameAdapterListener {

    private lateinit var bindingImpl: FragmentListGameBinding
    private val listGameViewModel: ListGameViewModel by viewModel()
    private val gameAdapter by lazy { GameAdapter(this) }
    private val searchResultAdapter by lazy { GameAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentListGameBinding.inflate(inflater, container, false), this) {
        bindingImpl = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setup menu
        bindingImpl.searchBar.inflateMenu(AppRes.menu.main_menu)
        bindingImpl.searchBar.setOnMenuItemClickListener {
            when (it.itemId) {
                AppRes.id.menu_favorite -> {
                    val toFavorite = ListGameFragmentDirections
                        .actionListGameFragmentToListFavoriteGameFragment()
                    view.findNavController().navigate(toFavorite)
                }
            }
            true
        }

        // setup search view
        bindingImpl.searchView.setupWithSearchBar(bindingImpl.searchBar)
        bindingImpl.rvGamesSearch.adapter = searchResultAdapter
        bindingImpl.searchView.editText.doAfterTextChanged { query ->
            if (query.toString().isEmpty()) {
                searchResultAdapter.clearList()
            }
            if (query.toString().length > 3) {
                listGameViewModel.searchGame(query.toString()).observe(viewLifecycleOwner) { games ->
                    games?.let {
                        when (games) {
                            is Resource.Loading -> {
                                bindingImpl.progressBarSearch.visible()
                                bindingImpl.rvGamesSearch.gone()
                            }
                            is Resource.Success -> {
                                bindingImpl.progressBarSearch.gone()
                                bindingImpl.rvGamesSearch.visible()
                                searchResultAdapter.clearList()
                                searchResultAdapter.submitList(games.data)
                            }
                            is Resource.Error -> {
                                bindingImpl.progressBarSearch.gone()
                                bindingImpl.rvGamesSearch.gone()
                            }
                        }
                    }
                }
            }
        }

        // custom back pressed behaviour search view
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (bindingImpl.searchView.isShowing) {
                bindingImpl.searchView.hide()
            } else {
                requireActivity().finish()
            }
        }

        // fetch data
        bindingImpl.rvGamesMain.adapter = gameAdapter
        listGameViewModel.getListGame().observe(viewLifecycleOwner) { games ->
            games?.let {
                when (games) {
                    is Resource.Loading -> bindingImpl.progressBarMain.visible()
                    is Resource.Success -> {
                        bindingImpl.progressBarMain.gone()
                        gameAdapter.submitList(games.data)
                    }
                    is Resource.Error -> {
                        bindingImpl.progressBarMain.gone()
                        bindingImpl.viewError.root.visible()
                        bindingImpl.viewError.tvError.text =
                            games.message ?: getString(CoreRes.string.text_error_list)
                    }
                }
            }
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
        bindingImpl.searchView.setupWithSearchBar(null)
        bindingImpl.searchView.editText.addTextChangedListener(null)
        bindingImpl.rvGamesSearch.adapter = null

        bindingImpl.rvGamesMain.adapter = null
    }

}