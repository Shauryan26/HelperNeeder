package com.example.helper_needer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.helper_needer.ModelResponse.Home
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OTPVerify : AppCompatActivity() {
    lateinit var auth:FirebaseAuth
    lateinit var verifyOtp:Button
    lateinit var otp:EditText
    lateinit var otpStr:String
    lateinit var phoneNumber:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverify)
        verifyOtp=findViewById(R.id.verifyOtp)
        otp=findViewById(R.id.otp)
        auth=FirebaseAuth.getInstance()
        otpStr=intent.getStringExtra("OTP").toString()
        phoneNumber=intent.getStringExtra("PhoneNumber").toString()

        verifyOtp.setOnClickListener {
            val entOTP:String=otp.getText().toString()
            if(entOTP.isNotEmpty()){
                if(entOTP.length==6){
                    val credential: PhoneAuthCredential=PhoneAuthProvider.getCredential(otpStr,entOTP)
                    signInWithPhoneAuthCredential(credential)
                }
                else{
                    Toast.makeText(this, "Please Enter Correct OTP", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    sendto()
                } else {
                    sendto()
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
    fun sendto(){
        startActivity(Intent(this,Home::class.java))
    }
}