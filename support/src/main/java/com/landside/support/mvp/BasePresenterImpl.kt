package com.landside.support.mvp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.landside.shadowstate.ShadowState
import com.landside.support.helper.GlobalErrHandler
import com.landside.support.subcribers.CoroutineObserver
import kotlinx.coroutines.launch

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

  fun launch(block: suspend CoroutineObserver.()->Unit) {
    (mView as? LifecycleOwner)?.let {
      it.lifecycleScope.launch {
        CoroutineObserver().apply {
          try {
            block()
          } catch (e: Exception) {
            try {
              errInvoker(e)
            } catch (gException: Exception) {
              GlobalErrHandler.handle(e)
            }
          }finally {
            doneInvoker()
          }
        }
      }
    }
  }
}
