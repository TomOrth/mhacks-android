package com.mhacks.app.ui.announcement.createannouncement.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import com.mhacks.app.R
import com.mhacks.app.data.models.CreateAnnouncement
import com.mhacks.app.di.common.BaseDialogFragment
import com.mhacks.app.ui.announcement.createannouncement.presenter.CreateAnnouncementPresenter
import kotlinx.android.synthetic.main.fragment_create_announcements.*
import javax.inject.Inject

/**
 * Fragment used to create and post a new announcement.
 */
class CreateAnnouncementDialogFragment: BaseDialogFragment(), CreateAnnouncementView {

    override var layoutResourceID = R.layout.fragment_create_announcements

    @Inject lateinit var createAnnouncementPresenter: CreateAnnouncementPresenter

    private var isTitleValid = false

    private var isCategoryValid = false

    private var isBodyValid = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setTitle("Post announcement")

        val countries = arrayOf("Food", "Emergency", "Event", "Logistics", "Sponsored")
        val adapter = ArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line, countries)

        fragment_create_announcements_category_edit_text.setAdapter(adapter)
        fragment_create_announcements_category_edit_text.keyListener = null

        fragment_create_announcements_category_edit_text.setOnFocusChangeListener({ _, _ ->
            showDropDown()
        })
        fragment_create_announcements_category_edit_text.setOnClickListener {
            showDropDown()
        }

        fragment_create_announcements_title_edit_text.addTextChangedListener(
                object : TextWatcher {

                    override fun afterTextChanged(p0: Editable?) {
                        isTitleValid = !p0.toString().isEmpty()
                        validate()
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                })
        fragment_create_announcements_body_edit_text.addTextChangedListener(
                object : TextWatcher {

                    override fun afterTextChanged(p0: Editable?) {
                        isBodyValid = !p0.toString().isEmpty()
                        validate()
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                })
        fragment_create_announcements_category_edit_text.setOnItemClickListener {
            _, _, _, _ ->
            isCategoryValid = true
            validate()
        }
        fragment_create_announcements_post_button.setOnClickListener {
            createAnnouncementPresenter.postAnnouncement(
                    fragment_create_announcements_title_edit_text.text.toString().trim(),
                    fragment_create_announcements_category_edit_text.text
                            .toString().toLowerCase().trim(),
                    fragment_create_announcements_body_edit_text.text.toString().trim()
            )
        }
    }

    private fun validate() {
        fragment_create_announcements_post_button.isEnabled =
                isTitleValid and isCategoryValid and isBodyValid
    }

    private fun showDropDown() {
        fragment_create_announcements_category_edit_text.showDropDown()
    }


    override fun onPostAnnouncementSuccess(createAnnouncement: CreateAnnouncement) {

    }

    override fun onPostAnnouncementFailure(error: Throwable) {

    }

    companion object {

        val instance
            get() = CreateAnnouncementDialogFragment()
    }
}