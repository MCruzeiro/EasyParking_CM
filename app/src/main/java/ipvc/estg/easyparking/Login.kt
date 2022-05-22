package ipvc.estg.easyparking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import ipvc.estg.easyparking.api.ServiceBuilder
import com.google.mlkit.common.sdkinternal.SharedPrefManager
import ipvc.estg.easyparking.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        fun login(view: View) {
            val email = findViewById<EditText>(R.id.etEmail).text.toString().trim()
            val password = findViewById<EditText>(R.id.etPass).text.toString().trim()
            val etEmail = findViewById<EditText>(R.id.etEmail)
            val etPass = findViewById<EditText>(R.id.etPass)


            if(email.isEmpty()){
                etEmail.error = "Email obrigatório"
                etEmail.requestFocus()
                return@login

            }

            if(password.isEmpty()){
                etPass.error = "Password required"
                etPass.requestFocus()
                return@login
            }

            ServiceBuilder.instance.userLogin(email, password)
                .enqueue(object: Callback<LoginResponse>{
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }


                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if(!response.body()?.error!!){ // se não houver erros

                            SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.user!!)

                            val intent = Intent(applicationContext, CriarConta::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                            startActivity(intent)


                        }else{
                            Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                        }

                    }
                })

        }
    }












    }




