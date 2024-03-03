package id.nanz.yourfavoritegame.presentation.listgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.net.toUri
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController
import id.nanz.yourfavoritegame.core.data.Resource
import id.nanz.yourfavoritegame.core.domain.model.Game
import id.nanz.yourfavoritegame.core.presentation.GameAdapter
import id.nanz.yourfavoritegame.core.presentation.gone
import id.nanz.yourfavoritegame.core.presentation.visible
import id.nanz.yourfavoritegame.databinding.FragmentListGameBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import id.nanz.yourfavoritegame.R as AppRes
import id.nanz.yourfavoritegame.core.R as CoreRes

class ListGameFragment : Fragment(), GameAdapter.GameAdapterListener {

    private val listGameViewModel: ListGameViewModel by viewModel()
    private var _binding: FragmentListGameBinding? = null
    private val binding get() = _binding!!

    private val gameAdapter by lazy { GameAdapter(this) }
    private val searchResultAdapter by lazy { GameAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setup menu
        binding.searchBar.inflateMenu(AppRes.menu.main_menu)
        binding.searchBar.setOnMenuItemClickListener {
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
        binding.searchView.setupWithSearchBar(binding.searchBar)
        binding.rvGamesSearch.adapter = searchResultAdapter
        binding.searchView.editText.doAfterTextChanged { query ->
            if (query.toString().isEmpty()) {
                searchResultAdapter.clearList()
            }
            if (query.toString().length > 3) {
                listGameViewModel.searchGame(query.toString()).observe(viewLifecycleOwner) { games ->
                        games?.let {
                            when (games) {
                                is Resource.Loading -> {
                                    binding.progressBarSearch.visible()
                                    binding.rvGamesSearch.gone()
                                }
                                is Resource.Success -> {
                                    binding.progressBarSearch.gone()
                                    binding.rvGamesSearch.visible()
                                    searchResultAdapter.clearList()
                                    searchResultAdapter.submitList(games.data)
                                }
                                is Resource.Error -> {
                                    binding.progressBarSearch.gone()
                                    binding.rvGamesSearch.gone()
                                }
                            }
                        }
                    }
            }
        }

        // custom back pressed behaviour search view
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (binding.searchView.isShowing) {
                binding.searchView.hide()
            } else {
                requireActivity().finish()
            }
        }

        // fetch data
        binding.rvGamesMain.adapter = gameAdapter
        listGameViewModel.getListGame().observe(viewLifecycleOwner) { games ->
            games?.let {
                when (games) {
                    is Resource.Loading -> binding.progressBarMain.visible()
                    is Resource.Success -> {
                        binding.progressBarMain.gone()
                        gameAdapter.submitList(games.data)
                    }
                    is Resource.Error -> {
                        binding.progressBarMain.gone()
                        binding.viewError.root.visible()
                        binding.viewError.tvError.text =
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

    override fun onDestroyView() {
        super.onDestroyView()

        binding.searchView.setupWithSearchBar(null)
        binding.searchView.editText.addTextChangedListener(null)
        binding.rvGamesSearch.adapter = null

        binding.rvGamesMain.adapter = null

        _binding = null
    }

}