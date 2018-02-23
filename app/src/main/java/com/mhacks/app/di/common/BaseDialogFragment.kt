package com.mhacks.app.di.common

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.mhacks.app.R
import com.mhacks.app.ui.common.ErrorableView
import com.mhacks.app.ui.common.ProgressBarView
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * A dialog fragment that supports dependency injection
 */
abstract class BaseDialogFragment : AppCompatDialogFragment(), HasSupportFragmentInjector {

    @Inject lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    abstract var layoutResourceID: Int

    private val parent by lazy {
        FrameLayout(context)
    }

    private val progressBarView by lazy {
        ProgressBarView(context!!)
    }

    private var mainView: View? = null

    private val errorView by lazy {
        ErrorableView(context!!, null)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.TitleDialog)
        parent.layoutParams = layoutParams
        progressBarView.layoutParams = layoutParams
        errorView.layoutParams = layoutParams
        progressBarView.visibility = View.GONE
        errorView.visibility = View.GONE
        mainView = inflater.inflate(layoutResourceID, container, false)
        parent.addView(mainView)
        parent.addView(progressBarView)
        parent.addView(errorView)
        return parent
    }


    override fun onResume() {
        super.onResume()
        val width = (resources.displayMetrics.widthPixels * .85).toInt()
        val height = (resources.displayMetrics.heightPixels * .7).toInt()
        dialog.window.setLayout(width, height)
    }

    fun showProgressBar(loadingText: String) {
        mainView?.visibility = View.GONE
        progressBarView.loadingText = loadingText
        progressBarView.visibility = View.VISIBLE
        errorView.visibility = View.GONE
    }

    fun showMainContent() {
        progressBarView.visibility = View.GONE
        mainView?.visibility = View.VISIBLE
        errorView.visibility = View.GONE
    }

    fun showErrorView(error: Int, callback: () -> Unit) {
        progressBarView.visibility = View.GONE
        mainView?.visibility = View.GONE
        errorView.visibility = View.VISIBLE
        errorView.titleText = error
        errorView.iconDrawable = R.drawable.ic_cloud_off_black_24dp
        errorView.tryAgainCallback = callback
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? = childFragmentInjector
}


