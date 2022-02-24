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

class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnlogin = findViewById<Button>(R.id.btn_login)
        val btnsignup = findViewById<Button>(R.id.btn_signup)
        val edtemail = findViewById<EditText>(R.id.edt_email)
        val edtpassword = findViewById<EditText>(R.id.edt_password)

        auth = FirebaseAuth.getInstance()

        btnlogin.setOnClickListener {
            val emaillogin = edtemail.text.toString().trim()
            val passwordlogin = edtpassword.text.toString().trim()

            if (emaillogin.isEmpty()){
                edtemail.error = "Email Tidak Boleh Kosong!"
                edtemail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emaillogin).matches()){
                edtemail.error = "Email Tidak Valid!"
                edtemail.requestFocus()
                return@setOnClickListener
            }

            if (passwordlogin.isEmpty() || passwordlogin.length < 6){
                edtpassword.error = "Password Tidak Boleh Kurang Dari 6!"
                edtpassword.requestFocus()
                return@setOnClickListener
            }
            loginuser(emaillogin,passwordlogin)
        }

        btnsignup.setOnClickListener {
            Intent(this@LoginActivity, SignupActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun loginuser(emaillogin: String, passwordlogin: String) {
        auth.signInWithEmailAndPassword(emaillogin,passwordlogin)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Intent(this@LoginActivity,HomeActivity::class.java).also {
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
            Intent(this@LoginActivity,HomeActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }
}