package com.landside.support.mvp

import androidx.lifecycle.*
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.Lifecycle.Event.ON_ANY
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import com.uber.autodispose.lifecycle.CorrespondingEventsFunction
import com.uber.autodispose.lifecycle.LifecycleEndedException
import com.uber.autodispose.lifecycle.LifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.jetbrains.annotations.NotNull

open class AutoDisposeProvider : LifecycleScopeProvider<Event>,LifecycleObserver {

  fun init(owner: LifecycleOwner){
    owner.lifecycle.addObserver(this)
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  fun onCreate(@NotNull owner: LifecycleOwner) {
    lifecycleEvents.onNext(Lifecycle.Event.ON_CREATE)
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  fun onDestroy(@NotNull owner: LifecycleOwner) {
    lifecycleEvents.onNext(Lifecycle.Event.ON_DESTROY)
  }

  val lifecycleEvents =
    BehaviorSubject.createDefault(ON_ANY)

  override fun lifecycle(): Observable<Event> {
    return lifecycleEvents.hide()
  }

  override fun correspondingEvents(): CorrespondingEventsFunction<Event> {
    return CORRESPONDING_EVENTS
  }

  override fun peekLifecycle(): Event? {
    return lifecycleEvents.value
  }

  companion object {
    private val CORRESPONDING_EVENTS = CorrespondingEventsFunction<Event> { event ->
      when (event) {
        ON_CREATE -> ON_DESTROY
        ON_ANY -> ON_DESTROY
        else -> throw LifecycleEndedException(
            "Cannot bind to ViewModel lifecycle after onCleared."
        )
      }
    }
  }
}