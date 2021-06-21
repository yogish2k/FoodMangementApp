package com.example.thefoodapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences

@SuppressLint("CommitPrefEdits")
class SessionManager(var con: Context) {

    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    var PRIVATE_MODE: Int = 0

    init {
        pref = con.getSharedPreferences(PREF_NAME,PRIVATE_MODE)
        editor = pref.edit()
    }

    companion object {
        val PREF_NAME: String = "TheFoodApp"
        val IS_LOGIN: String = "isLoggedIn"
        val USER_ID: String = "0"
    }

    fun createLoginSession( userId: String) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(USER_ID, userId)
        editor.commit()
    }
    fun checkLogin() {

        if(!this.isLoggedIn())
        {
            var intent: Intent = Intent(con, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            con.startActivity(intent)
        }
    }

    fun getUserDetails() : String? {
        var userId: String? = pref.getString(USER_ID, null)
        return userId
    }

    fun logoutUser() {
        editor.clear()
        editor.commit()

        val intent: Intent = Intent(con, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        con.startActivity(intent)
    }

    fun isLoggedIn() : Boolean {
        return pref.getBoolean(IS_LOGIN,false)
    }


}