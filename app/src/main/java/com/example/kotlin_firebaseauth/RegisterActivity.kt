package com.example.kotlin_firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()

        login_frm.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
        btn_register.setOnClickListener {
            registerUser()
        }

    }

    private fun registerUser() {
        val em = email.text.toString()
        val pwd = password.text.toString()
        if (em.isNotEmpty() && pwd.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(em, pwd).await()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterActivity,"Register Success",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity,MainActivity::class.java))
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }else if(email.text.toString().isEmpty()){
            Toast.makeText(this,"Please fill the Email above!",Toast.LENGTH_SHORT).show()
            email.requestFocus()
        }else if(password.text.toString().isEmpty()){
            Toast.makeText(this,"Please fill the Password above!",Toast.LENGTH_SHORT).show()
            password.requestFocus()
        }
    }

}
