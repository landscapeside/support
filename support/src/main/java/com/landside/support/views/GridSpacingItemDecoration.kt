package com.landside.support.views

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.State

class GridSpacingItemDecoration(
    //列数
  private val spanCount: Int,
    //间隔
  private val spacing: Int = 0,

  private var horizontalSpacing: Int = 0,
  private var verticalSpacing: Int = 0,
    //是否包含边缘
  private val includeEdge: Boolean
) : RecyclerView.ItemDecoration() {

  override fun getItemOffsets(
    outRect: Rect,
    view: View,
    parent: RecyclerView,
    state: State
  ) {
    if (horizontalSpacing == 0){
      horizontalSpacing = spacing
    }
    if (verticalSpacing == 0){
      verticalSpacing = spacing
    }
    //这里是关键，需要根据你有几列来判断
    val position: Int = parent.getChildAdapterPosition(view) // item position
    val column = position % spanCount // item column
    if (includeEdge) {
      outRect.left =
        horizontalSpacing - column * horizontalSpacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
      outRect.right =
        (column + 1) * horizontalSpacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
      if (position < spanCount) { // top edge
        outRect.top = verticalSpacing
      }
      outRect.bottom = verticalSpacing // item bottom
    } else {
      outRect.left = column * horizontalSpacing / spanCount // column * ((1f / spanCount) * spacing)
      outRect.right =
        horizontalSpacing - (column + 1) * horizontalSpacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
      if (position >= spanCount) {
        outRect.top = verticalSpacing // item top
      }
    }
  }
}