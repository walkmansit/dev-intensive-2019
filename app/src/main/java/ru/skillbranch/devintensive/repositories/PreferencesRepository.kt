package ru.skillbranch.devintensive.repositories

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import ru.skillbranch.devintensive.App
import ru.skillbranch.devintensive.models.Profile

object PreferencesRepository {


    private const val FIRST_NAME_KEY = "FIRST_NAME_KEY"
    private const val LAST_NAME_KEY = "LAST_NAME_KEY"
    private const val ABOUT_KEY = "ABOUT_KEY"
    private const val REPOSITORY_KEY = "REPOSITORY_KEY"
    private const val RATING_KEY = "RATING_KEY"
    private const val RESPECT_KEY = "RESPECT_KEY"
    private const val APP_THEME_KEY = "APP_THEME_KEY"

    private val prefs : SharedPreferences by lazy {
        val ctx = App.applicationContext()
        PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    fun saveAppTheme(theme: Int) {
        putValue(APP_THEME_KEY to theme)
    }

    fun getAppTheme(): Int = prefs.getInt(APP_THEME_KEY,AppCompatDelegate.MODE_NIGHT_NO)

    fun getProfile(): Profile  = Profile(
        prefs.getString(FIRST_NAME_KEY,"")!!,
        prefs.getString(LAST_NAME_KEY,"")!!,
        prefs.getString(ABOUT_KEY,"")!!,
        prefs.getString(REPOSITORY_KEY,"")!!,
        prefs.getInt(RATING_KEY,0),
        prefs.getInt(RESPECT_KEY,0)
    )

    fun saveProfile(profile: Profile) {
        with(profile){
            putValue(FIRST_NAME_KEY to firstName)
            putValue(LAST_NAME_KEY to lastName)
            putValue(ABOUT_KEY to about)
            putValue(REPOSITORY_KEY to repository)
            putValue(RATING_KEY to rating)
            putValue(RESPECT_KEY to respect)
        }
    }

    private fun putValue(pair : Pair<String, Any>) = with(prefs.edit()){
        val key = pair.first
        val value = pair.second

        when (value){
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            else -> error("Only primitives types can be stored")
        }

        apply()
    }
}