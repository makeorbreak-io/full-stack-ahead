package xyz.fullstackahead.where2go.ui.fragment

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_loading.*
import xyz.fullstackahead.where2go.R

class LoadingDialogFragment: DialogFragment() {

    companion object {

        const private val KEY_MESSAGE = "message"
        private var instance: LoadingDialogFragment? = null

        fun show(message: String, fragmentManager: FragmentManager) {
            val bundle = Bundle()
            bundle.putString(KEY_MESSAGE, message)
            instance = LoadingDialogFragment()
            instance?.arguments = bundle
            instance?.show(fragmentManager, "loadingFragment")
        }


        fun hide() {
            instance?.dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog)
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_loading, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments.containsKey(KEY_MESSAGE)) {
            message?.text = arguments.getString(KEY_MESSAGE)
        }
    }
}
