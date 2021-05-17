package com.landside.support.subcribers

class CoroutineObserver {
  var doneInvoker: () -> Unit = { /*nothing*/ }
  fun done(doneInvoker: () -> Unit) {
    this.doneInvoker = doneInvoker
  }

  var errInvoker: (Throwable) -> Unit = {}
  fun error(errInvoker: (Throwable) -> Unit) {
    this.errInvoker = errInvoker
  }
}