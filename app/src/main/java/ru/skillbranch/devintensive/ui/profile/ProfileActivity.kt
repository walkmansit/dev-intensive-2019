package ru.skillbranch.devintensive.ui.profile


import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile_constraint.*
import kotlinx.android.synthetic.main.activity_profile_constraint.tv_rank
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.extensions.isKeyboardOpen
import ru.skillbranch.devintensive.models.Bender
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel

class ProfileActivity : AppCompatActivity() {

    companion object{
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }

    var isEditMode = false

    lateinit var viewFields : Map<String, TextView>
    private  lateinit var profileViewModel : ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO set theme
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_constraint)

        initViews(savedInstanceState)
        initViewModel()

        Log.d("ProfileActivity","onCreate")
    }

    private fun initViews(savedInstanceState: Bundle?) {

        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE, false) ?: false

        viewFields = mapOf(
            "nickName" to tv_nick_name,
            "rank" to tv_rank,
            "firstName" to et_first_name,
            "lastName" to et_last_name,
            "about" to et_about,
            "repository" to et_repository,
            "rating" to tv_rating,
            "respect" to tv_respect
        )

        btn_edit.setOnClickListener(View.OnClickListener {
            if (isEditMode) saveProfileInfo()

            isEditMode = !isEditMode
            showCurrentMode(isEditMode)
        } )

        btn_switch_theme.setOnClickListener(View.OnClickListener {
            profileViewModel.switchTheme()
        })

    }

    private fun showCurrentMode(editMode: Boolean) {
        val info = viewFields.filter { setOf("firstName","lastName","about","repository").contains(it.key) }
        for ((_,v) in info)
        {
            v as EditText
            v.isFocusable = editMode
            v.isFocusableInTouchMode = editMode
            v.isEnabled = editMode
            v.background.alpha = if (editMode) 255 else 0
        }
        ic_eye.visibility = if (editMode) View.GONE else View.VISIBLE

        wr_about.isCounterEnabled = editMode

        with(btn_edit){
            val filter: ColorFilter? = if (editMode){
                PorterDuffColorFilter (
                    resources.getColor(R.color.color_accent,theme),
                    PorterDuff.Mode.SRC_IN
                )
            }
            else null

            val icon  = if(editMode) resources.getDrawable(R.drawable.ic_save_black_24dp, theme)
            else resources.getDrawable(R.drawable.ic_edit_black_24dp, theme)

            background.colorFilter = filter

            setImageDrawable(icon)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(IS_EDIT_MODE,isEditMode)
    }

    private fun initViewModel(){
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        profileViewModel.getProfileDate().observe(this, Observer { updateUI(it) })
        profileViewModel.getTheme().observe(this, Observer { updateTheme(it) })
    }

    private fun updateTheme(theme: Int) {
        delegate.setLocalNightMode(theme)
    }

    private fun updateUI( profile: Profile) {
        profile.toMap().also {
            for ((k,v) in viewFields){
                if (it.containsKey(k))
                    v.text = it[k].toString()
            }
        }
    }

    private fun saveProfileInfo(){
        Profile(
            firstName =  et_first_name.text.toString(),
            lastName =  et_last_name.text.toString(),
            about =  et_about.text.toString(),
            repository =  et_repository.text.toString()
        ).apply {
            profileViewModel.saveProfileDate(this)
        }

    }
}
