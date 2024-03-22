package id.nanz.yourfavoritegame.core.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import id.nanz.yourfavoritegame.core.R
import id.nanz.yourfavoritegame.core.data.Resource
import id.nanz.yourfavoritegame.core.databinding.FragmentDetailGameBinding
import id.nanz.yourfavoritegame.core.domain.model.DetailGame
import id.nanz.yourfavoritegame.core.utils.DataMapper
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailGameFragment : BaseFragment<FragmentDetailGameBinding>() {

    private lateinit var bindingImpl: FragmentDetailGameBinding
    private val detailGameViewModel: DetailGameViewModel by viewModel()
    private val args: DetailGameFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentDetailGameBinding.inflate(inflater, container, false), this) {
        bindingImpl = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gameId = args.gameId
        detailGameViewModel.getDetailGame(gameId).observe(viewLifecycleOwner) { detailGame ->
            detailGame?.let {
                when (detailGame) {
                    is Resource.Loading -> {
                        bindingImpl.progressBarDetail.visible()
                        bindingImpl.fabFavorite.gone()
                    }
                    is Resource.Success -> {
                        bindingImpl.progressBarDetail.gone()
                        bindingImpl.fabFavorite.visible()
                        setupUi(detailGame.data)
                    }
                    is Resource.Error -> {
                        bindingImpl.progressBarDetail.gone()
                        bindingImpl.fabFavorite.gone()
                        bindingImpl.tvDetailDescription.textAlignment = View.TEXT_ALIGNMENT_CENTER
                        bindingImpl.tvDetailDescription.text = detailGame.message ?: getString(R.string.text_error_list)
                    }
                }
            }
        }
        detailGameViewModel.isSetFavorite.observe(viewLifecycleOwner) { isSetFavorite ->
            if (isSetFavorite) {
                bindingImpl.fabFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_white))
            } else {
                bindingImpl.fabFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_not_favorite_white))
            }
        }
        detailGameViewModel.getFavoriteById(gameId)
    }

    private fun setupUi(data: DetailGame?) {
        data?.let { detailGame ->
            bindingImpl.collapsingToolbar.title = detailGame.name
            bindingImpl.ivDetail.load(detailGame.backgroundImage)
            bindingImpl.tvDetailReleasedDate.isVisible = detailGame.released.isNotEmpty()
            bindingImpl.tvDetailReleasedDate.text = getString(R.string.text_released_date, formatDate(detailGame.released))
            bindingImpl.tvDetailDescription.text = detailGame.descriptionRaw
            bindingImpl.toolbarDetail.setNavigationOnClickListener { v ->
                v.findNavController().navigateUp()
            }
            bindingImpl.fabFavorite.setOnClickListener {
                detailGameViewModel.setFavoriteGame(DataMapper.mapDetailGameToGame(detailGame))
            }
        }
    }

    private fun formatDate(raw: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = sdf.parse(raw)
        val sdfHour = SimpleDateFormat("d MMM, yyyy", Locale.getDefault())
        return sdfHour.format(date ?: Date())
    }

    override fun onFragmentDestroy() {
        bindingImpl.fabFavorite.setOnClickListener(null)
    }

}