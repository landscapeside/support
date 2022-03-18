package com.landside.support.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.landside.shadowstate.ShadowState
import com.landside.support.helper.GlobalErrHandler
import com.landside.support.subcribers.CoroutineObserver
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
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

  fun launch(block: suspend CoroutineObserver.() -> Unit): Job? {
    var job: Job? = null
    (mView as? LifecycleOwner)?.let {
      job = it.lifecycleScope.launch {
        CoroutineObserver().apply {
          try {
            block()
          } catch (e: Exception) {
            try {
              errInvoker(e)
            } catch (gException: Exception) {
              GlobalErrHandler.handle(gException)
            }
          }
          if (it.lifecycle.currentState >= Lifecycle.State.STARTED) {
            doneInvoker()
          }
        }
      }
    }
    return job
  }

  fun <T> async(block: suspend () -> T): Deferred<T>? {
    var deferred: Deferred<T>? = null
    (mView as? LifecycleOwner)?.let {
      deferred = it.lifecycleScope.async {
        return@async block()
      }
    }
    return deferred
  }
}
