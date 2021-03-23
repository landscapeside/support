package com.landside.support.extensions

import androidx.lifecycle.Lifecycle.Event
import com.uber.autodispose.autoDisposable
import com.uber.autodispose.lifecycle.LifecycleScopeProvider
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun <T> Observable<T>.asyncAssign(): Observable<T> =
  this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Flowable<T>.asyncAssign(): Flowable<T> =
  this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun LifecycleScopeProvider<Event>.countDown(
  delay: Long,
  invoker: () -> Unit
):Disposable {
  return Observable.just(1)
      .delay(delay, TimeUnit.SECONDS)
      .asyncAssign()
      .autoDisposable(this)
      .subscribe(
          {
            invoker()
          },
          {

          }
      )
}