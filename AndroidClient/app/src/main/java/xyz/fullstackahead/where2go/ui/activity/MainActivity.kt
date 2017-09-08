package xyz.fullstackahead.where2go.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import xyz.fullstackahead.where2go.R
import xyz.fullstackahead.where2go.extensions.app
import xyz.fullstackahead.where2go.ui.fragment.base.BaseFragment
import xyz.fullstackahead.where2go.ui.fragment.LandingFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app.component.inject(this)

        val baseFragment = LandingFragment()
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, baseFragment)
                .commit()
    }


    fun replaceMainFragment(fragment: BaseFragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, "TAG").commit()
    }


    fun getMainFragment(): BaseFragment? {
        return supportFragmentManager.findFragmentById(R.id.fragment_container) as? BaseFragment
    }
}
