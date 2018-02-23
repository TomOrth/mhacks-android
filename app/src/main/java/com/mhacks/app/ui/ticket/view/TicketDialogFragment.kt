package com.mhacks.app.ui.ticket.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mhacks.app.R
import com.mhacks.app.di.common.BaseDialogFragment
import com.mhacks.app.data.models.User
import com.mhacks.app.di.module.AuthModule
import com.mhacks.app.ui.ticket.presenter.TicketDialogPresenter
import kotlinx.android.synthetic.main.fragment_ticket_dialog.*
import net.glxn.qrgen.android.QRCode
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Fragment to display user information and show their QR code
 */
class TicketDialogFragment: BaseDialogFragment(), TicketDialogView {

    override var layoutResourceID = R.layout.fragment_ticket_dialog

    @Inject lateinit var ticketDialogPresenter: TicketDialogPresenter

    @Inject lateinit var authInterceptor: AuthModule.AuthInterceptor

    private var callback: Callback? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = activity as? Callback
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(true)
            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ticket_bottom_bar_done_button.setOnClickListener { dismiss() }
        ticketDialogPresenter.getUser()

    }

    override fun onGetUserSuccess(user: User) {
        val qr = QRCode.from(user.email)
                .withSize(500, 500)
                .withColor(0xFF43384D.toInt(), 0x00FFFFFF)
                .bitmap()
        ticket_qr_code_image_view.setImageBitmap(qr)
        ticket_full_name_text_view.text = user.fullName
        if (user.university == null)
            ticket_school_text_view.text = getString(R.string.no_school)
        else {
            if (user.university?.isEmpty()!!)
                ticket_school_text_view.text = getString(R.string.no_school)
            else
                ticket_school_text_view.text = user.university
        }
        showMainContent()
    }

    override fun onGetUserFailure(error: Throwable) {
        if (error is UnknownHostException) {
            showErrorView(R.string.loading_ticket, {
                showProgressBar(getString(R.string.loading_ticket))
                ticketDialogPresenter.getUser()
            })
        }
        else callback?.startLoginActivity()
    }

    interface Callback {
        fun startLoginActivity()
    }

    companion object {
        fun newInstance(): TicketDialogFragment {
            val fragment = TicketDialogFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}


