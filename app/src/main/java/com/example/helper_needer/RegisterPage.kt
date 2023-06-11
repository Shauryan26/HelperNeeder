package com.example.helper_needer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterPage : AppCompatActivity() {

    lateinit var submit: Button
    lateinit var email: EditText
    lateinit var password: EditText
    public lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        actionBar?.hide()



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        email=findViewById(R.id.emailId1)
        password=findViewById(R.id.password1)
        submit=findViewById(R.id.Submit)
        auth=FirebaseAuth.getInstance()
        submit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
               var email1=email.getText().toString()
                var  password1=password.getText().toString()
                if(TextUtils.isEmpty(email1)||TextUtils.isEmpty(password1))
                {
                    Toast.makeText(applicationContext, "Enter Email and Password both", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    regis(email1,password1)
                }
            }

        })

    }

    private fun regis(email1: String, password1: String) {
          auth.createUserWithEmailAndPassword(email1,password1)
              .addOnCompleteListener(this) {
                  if (it.isSuccessful) {
                      Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show()

                      auth.currentUser?.sendEmailVerification()?.addOnSuccessListener {
                          Toast.makeText(this, "Verification Link Sent", Toast.LENGTH_SHORT).show()
                          finish()
                      }
                          ?.addOnFailureListener{
                              Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                          }
                  } else {
                      Toast.makeText(this, "RegistrationFailed", Toast.LENGTH_SHORT).show()
                  }
    }}
}