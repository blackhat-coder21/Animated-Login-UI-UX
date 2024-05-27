package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.chatapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val login = findViewById<Button>(R.id.btnsignin)
        val user = findViewById<EditText>(R.id.email)
        val pwd1 = findViewById<EditText>(R.id.pwd1)
        val pwd2 = findViewById<EditText>(R.id.pwd2)
        val sign_up = findViewById<ConstraintLayout>(R.id.register_layout)

        login.setOnClickListener {
            val user_data = user.text.toString()
            val pwd_data1 = pwd1.text.toString()
            val pwd_data2 = pwd2.text.toString()

            var a = 0;
            var b = 0;
            var c = 0;
            var d = 0;
            if (user_data.isEmpty() || !user_data.contains("@")) {
                a = 1
            }
            if (pwd_data1.isEmpty() || pwd_data1.length<8) {
                b = 1
            }
            if (pwd_data2.isEmpty() || pwd_data2.length<8) {
                c = 1
            }
            if(pwd_data1 != pwd_data2){
                d = 1;
            }


            if(a==1 && b==1){
                user.error = "Invalid Email"
                pwd1.error = "Password Required"
                pwd2.error = "Password Required"
            }
            else if(a==1){
                user.error = "Invalid Email"
            }
            else if(b==1){
                pwd1.error = "Password Required"
            }
            else if(c==1){
                pwd2.error = "Password Required"
            }
            else if(d==1){
                pwd1.error = "Password Mismatched"
                pwd2.error = "Password Mismatched"
            }
            else {
                firebaseAuth.createUserWithEmailAndPassword(user_data,pwd_data1).addOnCompleteListener{
                    if(it.isSuccessful){
                        intent = Intent(applicationContext, Home::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this,"Server Down", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        sign_up.setOnClickListener{
            intent = Intent(applicationContext, LoginPage::class.java)
            startActivity(intent)
            finish()
        }

        var fade_in = AnimationUtils.loadAnimation(this,R.anim.fade_in)
        var bottom_down = AnimationUtils.loadAnimation(this,R.anim.bottom_down)


        binding.topLinearLayout.animation = bottom_down

        var handler = Handler()
        var runnable = Runnable{
            binding.textView3.animation = fade_in
            binding.registerLayout.animation = fade_in
            binding.textView.animation = fade_in
            binding.textView2.animation = fade_in
            binding.logoView.animation = fade_in
        }

        handler.postDelayed(runnable,1000)
    }
}