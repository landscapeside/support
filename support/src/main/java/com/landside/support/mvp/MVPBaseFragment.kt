package com.landside.support.mvp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.landside.shadowstate.ShadowState
import com.landside.support.extensions.toast
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class MVPBaseFragment<V : BaseView, T : BasePresenterImpl<V>> : Fragment(),
    HasSupportFragmentInjector, BaseView {
  @Inject
  lateinit var presenter: T
  @Inject
  lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

  @get:LayoutRes
  abstract val layoutId: Int

  val autoDisposeProvider = AutoDisposeProvider()

  private var mRootView: View? = null

  private var isInit = false
  var isLoad = false
    private set

  override fun onCreate(savedInstanceState: Bundle?) {
    ShadowState.bind(this)
    presenter.attachView(this as V)
    super.onCreate(savedInstanceState)
    autoDisposeProvider.init(this)
    isInit = true
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    initViews()
    tryLazyLoad()
  }

  override fun setUserVisibleHint(isVisibleToUser: Boolean) {
    super.setUserVisibleHint(isVisibleToUser)
    tryLazyLoad()
  }

  override fun onHiddenChanged(hidden: Boolean) {
    super.onHiddenChanged(hidden)
    if (hidden) {
      stopLoad()
    } else {
      tryLazyLoad()
    }
  }

  protected open fun tryLazyLoad() {
    if (!isInit) {
      return
    }
    if (!isLoad && userVisibleHint) {
      lazyLoad()
      isLoad = true
    } else {
      if (isLoad) {
        stopLoad()
      }
    }
  }

  abstract fun lazyLoad()

  protected open fun stopLoad() {}

  abstract fun initViews()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    mRootView = inflater.inflate(layoutId, container, false)
    return mRootView
  }

  override fun onAttach(context: Context) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    mRootView = null
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.detachView()
  }

  override fun supportFragmentInjector(): AndroidInjector<Fragment> {
    return childFragmentInjector
  }

  override fun showToast(msg: String) {
    toast { msg }
  }

  override fun showToast(resId: Int) {
    toast(resId)
  }

  override fun showRefreshing() {}

  override fun hideRefreshing() {}

  override fun showLoadMore() {}

  override fun hideLoadMore() {}

  override fun back() {}
}
