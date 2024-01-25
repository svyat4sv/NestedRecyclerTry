package com.example.nestedrecyclertry

import android.os.Parcelable
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AnimalSectionAdapter(
    items: List<AnimalSection> = emptyList(),
) : BaseAdapter<AnimalSection>(
    R.layout.item_animal_section,
    items,
) {
    private val scrollStates: MutableMap<String, Parcelable?> = mutableMapOf()

    private val viewPool = RecyclerView.RecycledViewPool()

    private fun getSectionID(position: Int): String {
        return items[position].id
    }

    override fun onViewRecycled(holder: BaseViewHolder<AnimalSection>) {
        super.onViewRecycled(holder)

        //save horizontal scroll state
        val key = getSectionID(holder.layoutPosition)
        scrollStates[key] =
            holder.itemView.findViewById<RecyclerView>(R.id.titledSectionRecycler).layoutManager?.onSaveInstanceState()
    }

    override fun bind(
        itemView: View,
        item: AnimalSection,
        position: Int,
        viewHolder: BaseViewHolderImp
    ) {
        itemView.findViewById<TextView>(R.id.sectionTitleTextView)?.text = item.title
        val layoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

        layoutManager.initialPrefetchItemCount = 4

        val titledSectionRecycler = itemView.findViewById<RecyclerView>(R.id.titledSectionRecycler)
        titledSectionRecycler?.run {
            this.setRecycledViewPool(viewPool)
            this.layoutManager = layoutManager
            this.adapter = AnimalAdapter(item.animals)
        }

        //restore horizontal scroll state
        val key = getSectionID(viewHolder.layoutPosition)
        val state = scrollStates[key]
        if (state != null) {
            titledSectionRecycler.layoutManager?.onRestoreInstanceState(state)
        } else {
            titledSectionRecycler.layoutManager?.scrollToPosition(0)
        }
    }
}