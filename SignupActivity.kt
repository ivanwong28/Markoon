package e.windows10.markoon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val btndaftar = findViewById<Button>(R.id.btn_daftar)
        val btnbaliklogin = findViewById<Button>(R.id.btn_baliklogin)
        val edtemailsignup = findViewById<EditText>(R.id.edt_emailsignup)
        val edtpasswordsignup = findViewById<EditText>(R.id.edt_passwordsignup)

        auth = FirebaseAuth.getInstance()

        btndaftar.setOnClickListener {
            val email = edtemailsignup.text.toString().trim()
            val password = edtpasswordsignup.text.toString().trim()

            if (email.isEmpty()){
                edtemailsignup.error = "Email Harus Diisi!"
                edtemailsignup.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                edtemailsignup.error = "Email Tidak Valid!"
                edtemailsignup.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6){
                edtpasswordsignup.error = "Password Tidak Boleh Kurang Dari 6 "
                edtpasswordsignup.requestFocus()
                return@setOnClickListener
            }

            signupuser(email,password)

        }

        btnbaliklogin.setOnClickListener {
            Intent(this@SignupActivity, LoginActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun signupuser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isComplete){
                    Intent(this@SignupActivity, HomeActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                }else{
                    Toast.makeText(this,"${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            Intent(this@SignupActivity, HomeActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }
}