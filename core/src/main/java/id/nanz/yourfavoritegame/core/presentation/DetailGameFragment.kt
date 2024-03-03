package id.nanz.yourfavoritegame.core.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
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

class DetailGameFragment : Fragment() {

    private val detailGameViewModel: DetailGameViewModel by viewModel()
    private var _binding: FragmentDetailGameBinding? = null
    private val binding get() = _binding!!

    private val args: DetailGameFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailGameBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gameId = args.gameId
        detailGameViewModel.getDetailGame(gameId).observe(viewLifecycleOwner) { detailGame ->
            detailGame?.let {
                when (detailGame) {
                    is Resource.Loading -> {
                        binding.progressBarDetail.visible()
                        binding.fabFavorite.gone()
                    }
                    is Resource.Success -> {
                        binding.progressBarDetail.gone()
                        binding.fabFavorite.visible()
                        setupUi(detailGame.data)
                    }
                    is Resource.Error -> {
                        binding.progressBarDetail.gone()
                        binding.fabFavorite.gone()
                        binding.tvDetailDescription.textAlignment = View.TEXT_ALIGNMENT_CENTER
                        binding.tvDetailDescription.text = detailGame.message ?: getString(R.string.text_error_list)
                    }
                }
            }
        }
        detailGameViewModel.isSetFavorite.observe(viewLifecycleOwner) { isSetFavorite ->
            if (isSetFavorite) {
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_white))
            } else {
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_not_favorite_white))
            }
        }
        detailGameViewModel.getFavoriteById(gameId)
    }

    private fun setupUi(data: DetailGame?) {
        data?.let { detailGame ->
            binding.collapsingToolbar.title = detailGame.name
            binding.ivDetail.load(detailGame.backgroundImage)
            binding.tvDetailReleasedDate.isVisible = detailGame.released.isNotEmpty()
            binding.tvDetailReleasedDate.text = getString(R.string.text_released_date, formatDate(detailGame.released))
            binding.tvDetailDescription.text = detailGame.descriptionRaw
            binding.toolbarDetail.setNavigationOnClickListener { v ->
                v.findNavController().navigateUp()
            }
            binding.fabFavorite.setOnClickListener {
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

    override fun onDestroy() {
        super.onDestroy()

        binding.fabFavorite.setOnClickListener(null)

        _binding = null
    }

}