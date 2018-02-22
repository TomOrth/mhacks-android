package com.mhacks.app.ui.announcement.createannouncement.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mhacks.app.R
import com.mhacks.app.di.common.BaseDialogFragment

/**
 * Fragment used to create and post a new announcement.
 */
class CreateAnnouncementDialogFragment : BaseDialogFragment(), CreateAnnouncementView {

    override var layoutResourceID = R.layout.fragment_create_announcements

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
    }

    override fun onCreateAnnouncementSuccess() {

    }

    override fun onCreateAnnouncementFailure() {

    }

    companion object {

        val instance
            get() = CreateAnnouncementDialogFragment()
    }
}