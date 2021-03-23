package com.landside.support.mvp

import androidx.annotation.StringRes

interface BaseView {
  fun showProgress()
  fun dismissProgress()
  fun showRefreshing()
  fun hideRefreshing()
  fun showLoadMore()
  fun hideLoadMore()
  fun showToast(value: String)
  fun showToast(@StringRes resId: Int)
  fun back()

}
