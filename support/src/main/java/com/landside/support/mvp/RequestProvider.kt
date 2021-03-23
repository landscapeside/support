package com.landside.support.mvp

import com.landside.support.scheduler.DefSchedulerProvider
import com.landside.support.scheduler.SchedulerProvider
import com.landside.support.subcribers.CompletionObserver
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable

open class RequestProvider : AutoDisposeProvider() {

  var schedulerProvider: SchedulerProvider = DefSchedulerProvider.instance

  fun <T> withRequest(
    observable: Observable<T>,
    scope: CompletionObserver<T>.() -> Unit
  ) {
    CompletionObserver<T>().apply {
      scope()
      observable
          .subscribeOn(schedulerProvider.io())
          .observeOn(schedulerProvider.ui())
          .autoDisposable(this@RequestProvider)
          .subscribe(this)
    }
  }

  fun <T> withSyncRequest(
    observable: Observable<T>,
    scope: CompletionObserver<T>.() -> Unit
  ) {
    CompletionObserver<T>().apply {
      scope()
      observable
          .autoDisposable(this@RequestProvider)
          .subscribe(this)
    }
  }
}