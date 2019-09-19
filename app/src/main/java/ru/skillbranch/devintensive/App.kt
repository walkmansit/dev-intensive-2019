package ru.skillbranch.devintensive

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import ru.skillbranch.devintensive.repositories.PreferencesRepository

class App : Application() {

    companion object{
        private lateinit var instance : App

        fun applicationContext() : Context{
            return  instance.applicationContext
        }
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        //setTheme(R.style.SplashTheme)
    }
}