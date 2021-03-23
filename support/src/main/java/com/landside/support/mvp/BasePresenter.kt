package com.landside.support.mvp

interface BasePresenter<V : BaseView> {
  fun attachView(view: V)

  fun detachView()
}
