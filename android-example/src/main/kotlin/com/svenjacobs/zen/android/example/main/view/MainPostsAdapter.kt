package com.svenjacobs.zen.android.example.main.view

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.svenjacobs.zen.android.example.api.model.JsonPost

class MainPostsAdapter : ListAdapter<JsonPost, MainPostsViewHolder>(ITEM_CALLBACK) {

    var posts: List<JsonPost>
        get() = currentList
        set(value) {
            submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainPostsViewHolder.create(parent)

    override fun onBindViewHolder(holder: MainPostsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {

        private val ITEM_CALLBACK = object : DiffUtil.ItemCallback<JsonPost>() {
            override fun areItemsTheSame(oldItem: JsonPost, newItem: JsonPost) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: JsonPost, newItem: JsonPost) =
                oldItem == newItem
        }
    }
}
