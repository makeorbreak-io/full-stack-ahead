package xyz.fullstackahead.where2go.ui.fragment

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_search.*
import xyz.fullstackahead.where2go.R
import xyz.fullstackahead.where2go.Where2GoApp
import xyz.fullstackahead.where2go.ui.viewmodel.LandingViewModel

class SearchDialogFragment : DialogFragment(), LifecycleRegistryOwner {

    companion object {
        const val TAG = "searchFragment"
    }

    lateinit var viewModel: LandingViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog)
        Where2GoApp.instance.component.inject(this)
        viewModel = ViewModelProviders.of(activity).get(LandingViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.categories.observe(this, Observer { onCategories(it) })
        btnRecommend.setOnClickListener {
            showProgress(true)
            val city = viewModel.getCity()
            val category = categoriesSpinner.selectedItem as? String
            Log.d(TAG, "Requesting recommendations, city:$city category:$category")
            viewModel.getRecommendations(city, category, {
                showProgress(false)
                if (it.isSuccessful) {
                    viewModel.recommendations.postValue(it.body())
                } else {
                    Toast.makeText(context, "Something went wrong, apologies", Toast.LENGTH_SHORT).show()
                }
                dismiss()
            })
        }
    }


    private fun onCategories(categories: List<String>?) {
        categoriesSpinner.adapter = ArrayAdapter<String>(categoriesSpinner.context, android.R.layout.simple_spinner_dropdown_item, categories)
    }


    private fun showProgress(inProgress: Boolean) {
        if (inProgress) {
            progressBar.visibility = VISIBLE
            btnRecommend.visibility = GONE
        } else {
            progressBar.visibility = GONE
            btnRecommend.visibility = VISIBLE
        }
    }


    // Lifecycle stuff
    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry? {
        return lifecycleRegistry
    }
}
