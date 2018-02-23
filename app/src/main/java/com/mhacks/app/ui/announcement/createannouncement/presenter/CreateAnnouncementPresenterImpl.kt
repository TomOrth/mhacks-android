package com.mhacks.app.ui.announcement.createannouncement.presenter

import com.mhacks.app.data.models.CreateAnnouncement
import com.mhacks.app.data.network.services.MHacksService
import com.mhacks.app.data.room.MHacksDatabase
import com.mhacks.app.di.module.AuthModule
import com.mhacks.app.ui.announcement.createannouncement.view.CreateAnnouncementView
import com.mhacks.app.ui.common.BasePresenterImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Implementation of the create announcement presenter.
 */
class CreateAnnouncementPresenterImpl(private val createAnnouncementView: CreateAnnouncementView,
                                      private val mHacksService: MHacksService,
                                      private val mHacksDatabase: MHacksDatabase,
                                      private val authInterceptor: AuthModule.AuthInterceptor)
    : CreateAnnouncementPresenter, BasePresenterImpl() {

    override fun postAnnouncement(title: String, category: String, body: String) {

        mHacksDatabase.loginDao().getLogin()
                .flatMap { authInterceptor.token = it.token
                    mHacksService.postAnnouncement(title, category, body,
                            true, true, true)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { createAnnouncementView.onPostAnnouncementSuccess(it) },
                        { createAnnouncementView.onPostAnnouncementFailure(it) }
                )
    }
}