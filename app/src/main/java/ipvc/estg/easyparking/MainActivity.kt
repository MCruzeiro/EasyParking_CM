package ipvc.estg.easyparking

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ipvc.estg.easyparking.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    private lateinit var btnLogin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etEmail = findViewById(R.id.etEmail)
        etPass = findViewById(R.id.etPass)
        btnLogin = findViewById(R.id.btnLogin)

        val sharedPref: SharedPreferences = getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
        val sharedPref2: SharedPreferences = getSharedPreferences(
            getString(R.string.preference_file_key2), Context.MODE_PRIVATE
        )

        val nomeValue = sharedPref.getString(getString(R.string.nomeUtilizador),"")
        val emailValue = sharedPref2.getString(getString(R.string.emailUtilizador),"")

        btnLogin.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?) {
                if(TextUtils.isEmpty(etEmail.text.toString()) || TextUtils.isEmpty(etPass.text.toString())){
                    Toast.makeText(this@MainActivity, "Email e/ou Palavra-passe não estão preenchidos!",Toast.LENGTH_SHORT).show()
                } else {
                    login()
                }
            }
        })
    }

    fun login() {
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getUsers()

        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful){
                    val users = response.body()!!
                    for (user in users){
                        if(etEmail.text.toString()==user.email && etPass.text.toString() == user.password){
                            Toast.makeText(this@MainActivity, "Login sucesso",Toast.LENGTH_SHORT).show()
                            //startActivity(Intent(this@MainActivity, MapsActivity::class.java))

                            val sharedPref: SharedPreferences = getSharedPreferences(
                                getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                            with (sharedPref.edit()) {

                                putString(getString(R.string.nomeUtilizador),user.nome)
                                commit()
                            }

                            val sharedPref2: SharedPreferences = getSharedPreferences(
                                getString(R.string.preference_file_key2), Context.MODE_PRIVATE)
                            with (sharedPref2.edit()) {

                                putString(getString(R.string.emailUtilizador),user.email)
                                commit()
                            }


                            val intent = Intent(this@MainActivity,PerfilUtilizador::class.java)
//                            intent.putExtra("email", user.email)
//                            intent.putExtra("nome", user.nome)
                            startActivity(intent)
                        } else{
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun registar(view: View) {
        startActivity(Intent(this@MainActivity, Registo::class.java))
    }
}