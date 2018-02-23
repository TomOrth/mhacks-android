package com.mhacks.app.ui.announcement.createannouncement.view

import com.mhacks.app.data.models.CreateAnnouncement

/**
 * View contract of Create Announcement Fragment.
 */
interface CreateAnnouncementView {

    fun onPostAnnouncementSuccess(createAnnouncement: CreateAnnouncement)

    fun onPostAnnouncementFailure(error: Throwable)
}