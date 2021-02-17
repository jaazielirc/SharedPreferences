package com.jaax.sharedpreferences

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.CollapsingToolbarLayout

class MainActivity : AppCompatActivity() {
    private var preferences: SharedPreferences? = null
    private var toolbarCollapse: CollapsingToolbarLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        preferences = PreferenceManager.getDefaultSharedPreferences( this )


        toolbarCollapse = findViewById(R.id.collapsingToolbar)
        toolbarCollapse!!.title = "SharedPreferences"

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar( toolbar )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate( R.menu.menu, menu )
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when( item.itemId ) {
        R.id.salir -> {
            val intent = Intent( this, LoginActivity::class.java )
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity( intent )
            true
        }

        R.id.salirOlvidar -> {
            preferences!!
                .edit()
                .clear()
                .apply()

            //ANKO SHAREDPREFERENCES
            //defaultSharedPreferences.edit() //y la lógica que se desee
            val intent = Intent( this, LoginActivity::class.java )
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity( intent )
            true
        }
        else -> false
    }

    override fun onBackPressed(){}
}