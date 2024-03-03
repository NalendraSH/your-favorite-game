package id.nanz.yourfavoritegame.core.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.nanz.yourfavoritegame.core.databinding.ItemGameBinding
import id.nanz.yourfavoritegame.core.domain.model.Game

class GameAdapter(private val callback: GameAdapterListener): RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    interface GameAdapterListener {
        fun onClickItem(data: Game)
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game):
                Boolean {
            return oldItem.gameId == newItem.gameId
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game):
                Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun clearList() {
        asyncListDiffer.submitList(null)
    }

    fun submitList(dataResponse: List<Game>?){
        asyncListDiffer.submitList(dataResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentData = asyncListDiffer.currentList[position]
        holder.bindItem(currentData)
        holder.itemView.setOnClickListener {
            callback.onClickItem(currentData)
        }
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    class ViewHolder(private val binding: ItemGameBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindItem(data: Game) {
            binding.apply {
                ivImage.load(data.backgroundImage)
                tvTitle.text = data.name
                tvRating.text = data.rating.toString()
            }
        }

    }

}