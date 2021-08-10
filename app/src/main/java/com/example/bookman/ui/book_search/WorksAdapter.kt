package com.example.bookman.ui.book_search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.bookman.R
import com.example.bookman.data.Work
import com.example.bookman.utils.CoverSize
import com.example.bookman.utils.loadCover

class WorksAdapter(
    private val glide: RequestManager,
    var itemClickListener: ((Work) -> Unit)? = null
) : PagedListAdapter<Work, WorksAdapter.ViewHolder>(WORK_COMPARATOR) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cover: ImageView = view.findViewById(R.id.ivCover)
        val title: TextView = view.findViewById(R.id.tvBookName)
        val author: TextView = view.findViewById(R.id.tvAuthorName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorksAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_work, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WorksAdapter.ViewHolder, position: Int) {
        val item = getItem(position)

        if (item?.coverId != null) {
            glide.loadCover(item.coverId, CoverSize.M)
                .error(glide.load(R.drawable.book_cover_missing))
                .into(holder.cover)
        } else {
            glide.load(R.drawable.book_cover_missing)
                .into(holder.cover)
        }

        holder.title.text = getItem(position)?.title
        holder.author.text = getItem(position)?.authorName?.reduce { acc, s -> "$acc, $s" }

        holder.itemView.setOnClickListener {
            if (item != null) itemClickListener?.invoke(item)
        }
    }

    companion object {
        val WORK_COMPARATOR = object : DiffUtil.ItemCallback<Work>() {
            override fun areContentsTheSame(oldItem: Work, newItem: Work): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Work, newItem: Work): Boolean =
                oldItem.coverId == newItem.coverId
        }
    }
}
