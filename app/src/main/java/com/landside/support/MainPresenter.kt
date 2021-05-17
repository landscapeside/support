package com.landside.support

import com.landside.support.mvp.BasePresenterImpl

class MainPresenter:BasePresenterImpl<MainView>() {
  val repo = TestRepo()

  fun load(){
    launch {
      repo.getData()
    }
  }
}