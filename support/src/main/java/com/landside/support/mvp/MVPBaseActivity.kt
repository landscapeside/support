package com.landside.support.mvp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.landside.shadowstate.ShadowState
import com.landside.shadowstate.ShadowState.rebind
import com.landside.slicerouter.SliceRouter
import com.landside.support.extensions.toast
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class MVPBaseActivity<V : BaseView, P : BasePresenterImpl<V>> :
    AppCompatActivity(),
    BaseView,
    HasFragmentInjector,
    HasSupportFragmentInjector {
    @Inject
    lateinit var presenter: P

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

    val autoDisposeProvider = AutoDisposeProvider()

    @get:LayoutRes
    abstract val layoutId: Int

    val context: Context
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        ShadowState.bind(this)
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        autoDisposeProvider.init(this)
        presenter.attachView(this as V)
        setContentView(layoutId)
        initViews()
        onLoad()
    }

    override fun onNewIntent(intent: Intent?) {
        setIntent(intent)
        rebind(this)
        super.onNewIntent(intent)
        onLoad()
    }

    protected abstract fun initViews()

    protected abstract fun onLoad()

    override fun setTitle(@StringRes resId: Int) {
        supportActionBar?.setTitle(resId)
    }

    override fun setTitle(title: CharSequence) {
        supportActionBar?.title = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            back()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun back() {
        SliceRouter.of(this).pop()
    }

    override fun onBackPressed() = back()

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showToast(msg: String) {
        toast { msg }
    }

    override fun showToast(resId: Int) {
        toast(resId)
    }

    override fun showRefreshing() {

    }

    override fun hideRefreshing() {

    }

    override fun showLoadMore() {

    }

    override fun hideLoadMore() {

    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment> {
        return supportFragmentInjector
    }
}
