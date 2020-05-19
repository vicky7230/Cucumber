package com.vicky7230.cucumber.ui.home

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.View


/**
 * Created by vicky on 1/1/18.
 */
class ItemOffsetDecoration(private val offset: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position != 0)
            outRect.top = offset / 2
        outRect.bottom = offset / 2
    }
}