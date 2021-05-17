package com.landside.support.helper

object GlobalErrHandler {
  private val handlers = mutableListOf<ErrHandler>()

  fun install(vararg errHandlers: ErrHandler){
    handlers.addAll(errHandlers)
  }

  fun handle(throwable: Throwable) {
    handlers.forEach { it.handle(throwable) }
  }

  interface ErrHandler{
    fun handle(throwable: Throwable)
  }
}