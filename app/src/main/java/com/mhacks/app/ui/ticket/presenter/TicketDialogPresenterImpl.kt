package com.mhacks.app.ui.ticket.presenter

import com.mhacks.app.data.network.services.MHacksService
import com.mhacks.app.data.room.MHacksDatabase
import com.mhacks.app.di.module.AuthModule
import com.mhacks.app.ui.ticket.view.TicketDialogView
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by jeffreychang on 2/16/18.
 */

class TicketDialogPresenterImpl @Inject constructor(
        private var ticketDialogView: TicketDialogView,
        private val mHacksService: MHacksService,
        private val mHacksDatabase: MHacksDatabase) : TicketDialogPresenter {

    override fun getUser(authInterceptor: AuthModule.AuthInterceptor) {
        mHacksDatabase.userDao()
                .getUser()
                .onErrorResumeNext {
                    mHacksDatabase.loginDao().getLogin()
                            .flatMap {
                                authInterceptor.token = it.token
                                mHacksService.getUserResponse()
                            }
                            .flatMap {
                                Single.just(it.user) }
                }
                .doOnSuccess {
                    Observable.fromCallable {
                        mHacksDatabase.userDao().insertUser(it)
                    }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { ticketDialogView.onGetUserSuccess(it) },
                        { ticketDialogView.onGetUserFailure(it) }
                )
    }
}