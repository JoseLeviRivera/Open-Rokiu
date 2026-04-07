package com.levi.rokiu.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.levi.rokiu.R
import com.levi.rokiu.databinding.ItemChannelBinding
import com.levi.rokiu.domain.model.Channel

class ChannelAdapter(
    private val baseUrl: String,
    private val onChannelClick: (Channel) -> Unit
) : ListAdapter<Channel, ChannelAdapter.ChannelViewHolder>(ChannelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val binding = ItemChannelBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChannelViewHolder(binding, baseUrl, onChannelClick)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ChannelViewHolder(
        private val binding: ItemChannelBinding,
        private val baseUrl: String,
        private val onChannelClick: (Channel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(channel: Channel) = with(binding) {
            val iconUrl = "$baseUrl/query/icon/${channel.id}"

            Glide.with(ivChannelLogo.context)
                .load(iconUrl)
                .placeholder(R.drawable.tv_24px)
                .error(R.drawable.tv_24px)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(ivChannelLogo)

            root.setOnClickListener {
                onChannelClick(channel)
            }
        }
    }

    private class ChannelDiffCallback : DiffUtil.ItemCallback<Channel>() {
        override fun areItemsTheSame(oldItem: Channel, newItem: Channel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Channel, newItem: Channel) =
            oldItem == newItem
    }
}