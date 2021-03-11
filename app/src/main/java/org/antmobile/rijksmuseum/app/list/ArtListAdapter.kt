package org.antmobile.rijksmuseum.app.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import org.antmobile.rijksmuseum.R
import org.antmobile.rijksmuseum.databinding.ArtListItemBinding
import org.antmobile.rijksmuseum.databinding.ArtListSectionItemBinding
import org.antmobile.rijksmuseum.domain.models.Art

class ArtsListAdapter(
    private val onItemClicked: (Art) -> Unit
) : ListAdapter<ArtListUiItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is ArtListUiItem.Item -> TYPE_ITEM
        is ArtListUiItem.Section -> TYPE_SECTION
        is ArtListUiItem.LoadMore -> TYPE_LOAD_MORE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_ITEM -> {
                val binding =
                    ArtListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ArtViewHolder(binding) { position -> onItemClicked((getItem(position) as ArtListUiItem.Item).art) }
            }
            TYPE_SECTION -> {
                val binding =
                    ArtListSectionItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return SectionViewHolder(binding)
            }
            TYPE_LOAD_MORE -> {
                return LoadingMoreViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.art_list_infinite_loading_item, parent, false)
                )
            }
            else -> throw Exception("Unsupported view type. viewType=$viewType")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_ITEM -> (holder as ArtViewHolder).bindView((getItem(position) as ArtListUiItem.Item).art)
            TYPE_SECTION -> (holder as SectionViewHolder).bindView((getItem(position) as ArtListUiItem.Section).title)
        }
    }

    // region ArtViewHolder
    ///////////////////////////////////////////////////////////////////////////

    class ArtViewHolder(
        private val binding: ArtListItemBinding,
        private val onItemClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        fun bindView(item: Art) {
            binding.title.text = item.title
            binding.longTitle.text = item.longTitle
            binding.image.contentDescription = item.longTitle
            Glide.with(itemView)
                .load(item.imageUrl)
                .transform(FitCenter())
                .into(binding.image)
        }
    }

    // endregion

    // region SectionViewHolder
    ///////////////////////////////////////////////////////////////////////////

    private class SectionViewHolder(
        private val binding: ArtListSectionItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(title: String) {
            binding.root.text = title
        }
    }

    // endregion

    // region LoadingMoreViewHolder
    ///////////////////////////////////////////////////////////////////////////

    class LoadingMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // endregion

    private companion object {
        const val TYPE_SECTION = 0
        const val TYPE_ITEM = 1
        const val TYPE_LOAD_MORE = 2

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArtListUiItem>() {
            override fun areItemsTheSame(
                oldItem: ArtListUiItem,
                newItem: ArtListUiItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ArtListUiItem,
                newItem: ArtListUiItem
            ): Boolean {
                return when {
                    oldItem is ArtListUiItem.Item && newItem is ArtListUiItem.Item -> {
                        oldItem.art.id == newItem.art.id
                    }
                    oldItem is ArtListUiItem.Section && newItem is ArtListUiItem.Section -> {
                        oldItem.title == newItem.title
                    }
                    else -> {
                        oldItem == newItem
                    }
                }
            }
        }
    }

}
