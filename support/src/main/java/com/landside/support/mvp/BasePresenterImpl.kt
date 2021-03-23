package com.landside.support.mvp

import androidx.lifecycle.LifecycleOwner
import com.landside.shadowstate.ShadowState

open class BasePresenterImpl<V : BaseView> : RequestProvider(), BasePresenter<V> {

  protected var mView: V? = null
  override fun attachView(view: V) {
    mView = view
    if (view is LifecycleOwner) {
      init(view)
      ShadowState.injectDispatcher(this, view)
    }
  }

  override fun detachView() {
    mView = null
  }
}
