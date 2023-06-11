package com.example.helper_needer

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.helper_needer.ModelResponse.RegisterResponse
import com.example.helper_needer.ModelResponse.ReterofitClient1
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

lateinit var ll1: LinearLayout
class Profile : AppCompatActivity() {
    lateinit var submit: Button
    lateinit var auth: FirebaseAuth
    lateinit var phoneNumber: TextInputEditText
    lateinit var number: String
    lateinit var Name: TextInputEditText
    lateinit var Age: TextInputEditText
    lateinit var Room: TextInputEditText
    lateinit var Task: TextInputEditText
    lateinit var City: TextInputEditText
    lateinit var Male: RadioButton
    lateinit var Female: RadioButton
    lateinit var Part: RadioButton
    lateinit var Full: RadioButton
    lateinit var helper: RadioButton
    lateinit var needer: RadioButton
    lateinit var name: String
    var age by Delegates.notNull<Int>()
    lateinit var gender: String
    lateinit var duration: String
    lateinit var type: String
    lateinit var city: String
    var rooms by Delegates.notNull<Int>()
    var tasks by Delegates.notNull<Int>()
   public lateinit var sharedpref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        ll1=findViewById(R.id.ll1)
        sharedpref=getSharedPreferences("myPref", MODE_PRIVATE)
        val editor=sharedpref.edit()

        init()
        submit.setOnClickListener {
            Toast.makeText(this, "Submit clicked", Toast.LENGTH_SHORT).show()
           number= phoneNumber.text?.trim().toString()
           name= Name.text?.trim().toString()
          city= City.text?.trim().toString()
           age= Age.text?.trim().toString().toIntOrNull() ?: -1
           rooms= Room.text?.trim().toString().toIntOrNull() ?: -1
           tasks= Task.text?.trim().toString().toIntOrNull() ?: -1

           if(Male.isChecked){
               gender="male"
           }else{
               gender="female"
           }
            if(Part.isChecked){
                duration="PartTime"
            }else{
                duration="FullTime"
            }
            if(helper.isChecked){
                type="helper"
            }else{
                type="needer"
            }
            editor.apply{
                putString("Type", type)
                putString("City", city)
                apply()
            }
            ReterofitClient1.api.setProfile(name,age,number,gender,duration,city,rooms,tasks,type).enqueue(object :
                Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    if(response.body()!=null) {

                        Log.i("NumberGenerated",response.body()!!.message);
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Log.i("Tag",t.message.toString())
                }

            })
           if(number.isNotEmpty()){
               if(number.length==10){
                   number="+91$number"

                   val options = PhoneAuthOptions.newBuilder(auth)
                       .setPhoneNumber(number)       // Phone number to verify
                       .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                       .setActivity(this)                 // Activity (for callback binding)
                       .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                       .build()
                   PhoneAuthProvider.verifyPhoneNumber(options)

               }else
               {
                   Toast.makeText(this, "Please Enter Correct Number", Toast.LENGTH_SHORT).show()
               }
           }else{
               Toast.makeText(this, "Please Enter Number", Toast.LENGTH_SHORT).show()
           }

        }
    }

    fun onRadioButtonClicked(view: View) {
        var isvisible: Int=ll1.getVisibility()
        if(view.id==R.id.radioNeeder)
        {
           ll1.setVisibility(View.VISIBLE)
        }
        else
        {
            ll1.setVisibility(View.GONE)
        }
    }

    fun init(){
        submit=findViewById(R.id.loginButton)
        phoneNumber=findViewById(R.id.phone)
       Name=findViewById(R.id.name)
        Age=findViewById(R.id.age)
        Room=findViewById(R.id.room)
        Task=findViewById(R.id.task)
        City=findViewById(R.id.city)
        Male=findViewById(R.id.radioMale)
        Female=findViewById(R.id.radioFemale)
        helper=findViewById(R.id.radioHelper)
        needer=findViewById(R.id.radioNeeder)
        Part=findViewById(R.id.PT)
        Full=findViewById(R.id.FT)
        auth=FirebaseAuth.getInstance()
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this, "SignInSuccessful", Toast.LENGTH_SHORT).show()
                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Toast.makeText(this@Profile, "Failure", Toast.LENGTH_SHORT).show()
            if (e is FirebaseAuthInvalidCredentialsException) {
                Toast.makeText(this@Profile, "Failure1", Toast.LENGTH_SHORT).show()
            }
            if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Toast.makeText(this@Profile, "Failure2", Toast.LENGTH_SHORT).show()
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Toast.makeText(this@Profile, "CODE SENT", Toast.LENGTH_SHORT).show()
            // Save verification ID and resending token so we can use them later
            val intent = Intent(this@Profile, OTPVerify::class.java)
            intent.putExtra("OTP",verificationId)
            intent.putExtra("resendToken", token)
            intent.putExtra("PhoneNumber",number)
            startActivity(intent)
        }
    }


    }


