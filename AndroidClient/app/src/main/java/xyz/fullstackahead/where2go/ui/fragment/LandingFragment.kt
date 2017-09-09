package xyz.fullstackahead.where2go.ui.fragment

import ai.api.android.AIConfiguration
import ai.api.ui.AIDialog
import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.BasePermissionListener
import kotlinx.android.synthetic.main.fragment_landing.*
import xyz.fullstackahead.where2go.R
import xyz.fullstackahead.where2go.Recommendation
import xyz.fullstackahead.where2go.ui.adapter.RecommendationsAdapter
import xyz.fullstackahead.where2go.ui.fragment.base.BaseFragment
import xyz.fullstackahead.where2go.ui.viewmodel.LandingViewModel
import xyz.fullstackahead.where2go.utils.getAddressFromLocation
import xyz.fullstackahead.where2go.utils.showDialog
import xyz.fullstackahead.where2go.utils.tintMenuItem
import java.util.*


class LandingFragment : BaseFragment() {

    companion object {
        const val TAG = "LandingFragment"
    }

    private lateinit var viewModel: LandingViewModel
    private lateinit var ttsEngine: TextToSpeech
    private var menu: Menu? = null

    private var recommendationsAdapter: RecommendationsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(this).get(LandingViewModel::class.java)
        viewModel.init(mainActivity)
        ttsEngine = TextToSpeech(activity, {
            if (it == TextToSpeech.SUCCESS) {
                Log.d("TTS", "TTS engine initialized")
                ttsEngine.language = Locale.US
            } else {
                Log.d("TTS", "TTS engine initialization failed")
            }
        })

        // Setup liveData observers
        viewModel.apiResponse.observe(this, Observer { onAIResponse(it) })
        viewModel.recommendations.observe(this, Observer { onRecommendations(it) })
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAppBarLayout()
        setupPermissions()
        setupSearchButton()
        setupRecyclerView()
        viewModel.getRecommendations()

        viewModel.getLocation()
    }

    private fun setupAppBarLayout() {
        mainActivity.setSupportActionBar(toolbar)
        appBarLayout?.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout!!.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true
                    showOption(R.id.action_search)
                } else if (isShow) {
                    isShow = false
                    hideOption(R.id.action_search)
                }
            }

        })
    }


    private fun setupPermissions() {
        Dexter.withActivity(activity).withPermissions(Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION).withListener(object : BaseMultiplePermissionsListener() {

        }).check()
    }


    private fun setupSearchButton() {
        searchButton.setOnClickListener {
            val dialog = AIDialog(mainActivity, mainActivity.configAI)
            dialog.setResultsListener(viewModel)
            dialog.showAndListen()
        }

        searchButton.setOnLongClickListener {
            // TODO text-based search
            true
        }
    }


    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recommendationsAdapter = RecommendationsAdapter()
        recyclerView.adapter = recommendationsAdapter
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        this.menu = menu
        mainActivity.menuInflater.inflate(R.menu.menu_scrolling, menu)

        // Fix search menu icon tint
        tintMenuItem(menu?.findItem(R.id.action_search))
        tintMenuItem(menu?.findItem(R.id.action_test_location))

        hideOption(R.id.action_search)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search -> performSearch()
            R.id.action_settings -> Log.d(TAG, "actionSettings")
            R.id.action_test_location -> {
                val location = viewModel.getLocation()
                val latitude = location?.latitude
                val longitude = location?.longitude
                val address = getAddressFromLocation(latitude!!, longitude!!, activity)?.getAddressLine(0)
                showDialog(getString(R.string.action_test_location), "$address \n($latitude | $longitude)", activity)
            }
        }
        return true
    }


    private fun hideOption(id: Int) {
        val item = menu?.findItem(id)
        item?.isVisible = false
    }

    private fun showOption(id: Int) {
        val item = menu?.findItem(id)
        item?.isVisible = true
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_landing
    }


    private fun performSearch() {
        // TODO
        val location = viewModel.getLocation()
        Log.d(TAG, getAddressFromLocation(location?.latitude!!, location.longitude, mainActivity).toString())
        viewModel.getRecommendations()
    }


    private fun onAIResponse(response: String?) {
        if (response == null) return

        // TODO do we need this?
        ttsEngine.speak(response, TextToSpeech.QUEUE_FLUSH, null, null)

        performSearch()
    }


    private fun onRecommendations(recommendations: List<Recommendation>?) {
        if (recommendations == null) return

        recommendationsAdapter?.update(recommendations)
    }

}
