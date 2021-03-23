package com.landside.support.scheduler

import com.landside.support.scheduler.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class ImmediateSchedulerProvider: SchedulerProvider {
  override fun computation(): Scheduler = Schedulers.trampoline()

  override fun io(): Scheduler = Schedulers.trampoline()

  override fun ui(): Scheduler = Schedulers.trampoline()
}