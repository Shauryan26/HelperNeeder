package com.example.helper_needer

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    lateinit var emailId: EditText
    lateinit var password: EditText

    lateinit var register1: Button

    lateinit var login: Button

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        actionBar?.hide()



       val sharedpref=getSharedPreferences("myPref", MODE_PRIVATE)
        val editor=sharedpref.edit()

        register1=findViewById(R.id.RegisterButton)
        login=findViewById(R.id.loginButton)
        emailId=findViewById(R.id.emailId)
        password=findViewById(R.id.Password)
        auth=FirebaseAuth.getInstance()

        val check_login=sharedpref.getString("EmailID",null).toString()
        Log.d(TAG, check_login)
        if((check_login.length)>4){
            emailId.setText(check_login)
            val intent = Intent(applicationContext,Profile::class.java)
            startActivity(intent)
            finish()
        }
        else {
            register1.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    val intent = Intent(applicationContext, RegisterPage::class.java)
                    startActivity(intent)
                }

            })
            login.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    var email1 = emailId.getText().toString()
                    var password1 = password.getText().toString()
                    if (TextUtils.isEmpty(email1) || TextUtils.isEmpty(password1)) {
                        Toast.makeText(
                            applicationContext,
                            "Enter Email and Password both",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        logindo(email1, password1,sharedpref,editor)
                    }
                }

            })
        }

    }

    private fun logindo(email1: String, password1: String,sharedpref: SharedPreferences,editor: SharedPreferences.Editor) {
        auth.signInWithEmailAndPassword(email1, password1).addOnCompleteListener(this) {
            if (it.isSuccessful) {

                val verification=auth.currentUser?.isEmailVerified
              if(verification!=true)
              {
                  auth.currentUser?.delete()

                  val intent = Intent(applicationContext,Verification::class.java)
                  startActivity(intent)
              }
                else {
                  editor.apply{
                      putString("EmailID", emailId.getText().toString())
                      apply()
                  }
                  val intent = Intent(applicationContext,Profile::class.java)
                  startActivity(intent)
              }} else
                Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
        }
    }
}
