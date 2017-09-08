package xyz.fullstackahead.where2go.ui.fragment.base

import android.arch.lifecycle.LifecycleFragment
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xyz.fullstackahead.where2go.Where2GoApp
import xyz.fullstackahead.where2go.ui.activity.MainActivity

abstract class BaseFragment: LifecycleFragment() {

    val application = Where2GoApp.instance

    val mainActivity : MainActivity
        get() = activity as MainActivity


    @LayoutRes
    abstract fun getLayoutId() : Int


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(getLayoutId(), container, false)
    }
}
