package com.example.bookman.ui.work_details

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
import com.example.bookman.data.Book

class BooksAdapter(
    private val glide: RequestManager,
    var itemClickListener: ((Book) -> Unit)? = null
) : PagedListAdapter<Book, BooksAdapter.ViewHolder>(BOOK_COMPARATOR) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cover: ImageView = view.findViewById(R.id.ivCover)
        val title: TextView = view.findViewById(R.id.tvTitle)
        val published: TextView = view.findViewById(R.id.tvPublishedYear)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_book, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        glide.load(item?.cover?.medium)
            .error(glide.load(R.drawable.book_cover_missing))
            .into(holder.cover)

        holder.title.text = getItem(position)?.title
        holder.published.text =
            holder.itemView.context.getString(R.string.published_year, getItem(position)?.publishDate)

        holder.itemView.setOnClickListener {
            if (item != null) itemClickListener?.invoke(item)
        }
    }

    companion object {
        val BOOK_COMPARATOR = object : DiffUtil.ItemCallback<Book>() {
            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean =
                oldItem.url == newItem.url
        }
    }
}