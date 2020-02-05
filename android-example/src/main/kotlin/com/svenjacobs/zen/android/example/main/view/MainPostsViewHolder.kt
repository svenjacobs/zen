package com.svenjacobs.zen.android.example.main.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.svenjacobs.zen.android.example.R
import com.svenjacobs.zen.android.example.api.model.JsonPost
import kotlinx.android.synthetic.main.view_post_list_item.view.*

class MainPostsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(post: JsonPost) {
        with(itemView) {
            post_list_item_title.text = post.title
            post_list_item_body.text = post.body
        }
    }

    companion object {

        fun create(parent: ViewGroup) =
            MainPostsViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.view_post_list_item, parent, false)
            )
    }
}
