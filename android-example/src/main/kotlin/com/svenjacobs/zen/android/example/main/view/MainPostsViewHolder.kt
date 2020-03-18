package com.svenjacobs.zen.android.example.main.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.svenjacobs.zen.android.example.api.model.JsonPost
import com.svenjacobs.zen.android.example.databinding.ViewPostListItemBinding

class MainPostsViewHolder(
    private val binding: ViewPostListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        post: JsonPost,
        onItemClickListener: ((item: JsonPost) -> Unit)?
    ) {
        with(binding) {
            root.setOnClickListener { onItemClickListener?.invoke(post) }
            postListItemTitle.text = post.title
            postListItemBody.text = post.body
        }
    }

    companion object {

        fun create(parent: ViewGroup): MainPostsViewHolder {
            val binding = ViewPostListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return MainPostsViewHolder(binding)
        }
    }
}
