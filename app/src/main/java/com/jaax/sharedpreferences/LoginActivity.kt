package com.jaax.sharedpreferences

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar

class LoginActivity: AppCompatActivity() {
    private lateinit var btnAcceder: Button
    private lateinit var btnRegistrar: Button
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var switch: SwitchCompat

    private var preferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_login )

        btnAcceder = findViewById(R.id.btnAcceder)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        switch = findViewById(R.id.switch1)
        preferences = PreferenceManager.getDefaultSharedPreferences( this )

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar( toolbar )
    }

    override fun onResume() {
        super.onResume()

        btnRegistrar.setOnClickListener {
            savePreferences( username.text.toString(), password.text.toString() )
        }

        setPreferences()

        btnAcceder.setOnClickListener {
            if( login( username.text.toString(), password.text.toString() ) ){
                val intent = Intent( this, MainActivity::class.java )
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity( intent )
            }
        }
    }

    private fun isValidEmail( username: String ): Boolean {
        return !TextUtils.isEmpty( username) && Patterns.EMAIL_ADDRESS.matcher( username ).matches()
    }

    private fun isValidPassword( password: String ): Boolean{
        return password.length >= 3
    }

    private fun login( email: String, password: String ): Boolean {
        return if( isValidEmail(email) && isValidPassword(password) ){
            Toast.makeText(this, "Bienvenid@", Toast.LENGTH_SHORT).show()
            true
        } else {
            Toast.makeText(this, "Datos no válidos", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun savePreferences( email: String, password: String ){
        if( switch.isChecked ){
            preferences!!
                .edit()
                .putString( "email", email )
                .putString( "password", password )
                .apply()
        }
    }

    private fun setPreferences() {
        val email = preferences?.getString( "email", "?" )
        val pass = preferences?.getString( "password", "?" )
        if( email != "?" && pass != "?" ){
            username.setText( email )
            password.setText( pass )
            switch.isChecked = true
        }
    }
}