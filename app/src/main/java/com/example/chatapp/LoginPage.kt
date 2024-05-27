package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.chatapp.databinding.ActivityLoginPageBinding
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {
    lateinit var binding: ActivityLoginPageBinding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val login = findViewById<Button>(R.id.btnlogin)
        val user = findViewById<EditText>(R.id.email)
        val pwd = findViewById<EditText>(R.id.pwd)
        val sign_up = findViewById<ConstraintLayout>(R.id.register_layout)
        val gmail_page = findViewById<ImageView>(R.id.gmail_page)
        val fb_page = findViewById<ImageView>(R.id.fb_page)
        val twitter_page = findViewById<ImageView>(R.id.twitter_page)
        val forgetpwd = findViewById<TextView>(R.id.forgetpwd)

        login.setOnClickListener {
            val user_data = user.text.toString()
            val pwd_data = pwd.text.toString()

            var a = 0;
            var b = 0;
            if (user_data.isEmpty() || !user_data.contains("@")) {
                a = 1
            }
            if (pwd_data.isEmpty() || pwd_data.length<8) {
                b = 1
            }


            if(a==1 && b==1){
                user.error = "Invalid Email"
                pwd.error = "Password Required"
            }
            else if(a==1){
                user.error = "Invalid Email"
            }
            else if(b==1){
                pwd.error = "Password Required"
            }
            else {
                firebaseAuth.signInWithEmailAndPassword(user_data,pwd_data).addOnCompleteListener{
                    if(it.isSuccessful){
                        intent = Intent(applicationContext, Home::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this,"No User Found!!!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        sign_up.setOnClickListener{
            intent = Intent(applicationContext, Register::class.java)
            startActivity(intent)
            finish()
        }

        gmail_page.setOnClickListener{
            intent = Intent(applicationContext, RegisterGmail::class.java)
            startActivity(intent)
            finish()
        }

        twitter_page.setOnClickListener{
            intent = Intent(applicationContext, RegisterTwitter::class.java)
            startActivity(intent)
            finish()
        }

        fb_page.setOnClickListener{
            intent = Intent(applicationContext, RegisterFacebook::class.java)
            startActivity(intent)
            finish()
        }

        forgetpwd.setOnClickListener{
            intent = Intent(applicationContext, ForgetPassword::class.java)
            startActivity(intent)
            finish()
        }

        var fade_in = AnimationUtils.loadAnimation(this,R.anim.fade_in)
        var bottom_down = AnimationUtils.loadAnimation(this,R.anim.bottom_down)


        binding.topLinearLayout.animation = bottom_down

        var handler = Handler()
        var runnable = Runnable{
            binding.cardView.animation = fade_in
            binding.cardView2.animation= fade_in
            binding.textView3.animation = fade_in
            binding.cardView4.animation = fade_in
            binding.registerLayout.animation = fade_in
            binding.textView.animation = fade_in
            binding.textView2.animation = fade_in
            binding.logoView.animation = fade_in
        }

        handler.postDelayed(runnable,1000)
    }
}