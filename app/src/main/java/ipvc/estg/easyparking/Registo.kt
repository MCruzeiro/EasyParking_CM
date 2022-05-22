package ipvc.estg.easyparking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import ipvc.estg.easyparking.api.EndPoints
import ipvc.estg.easyparking.api.ServiceBuilder
import ipvc.estg.easyparking.api.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Registo : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var nome: EditText
    private lateinit var pass: EditText
    private lateinit var btnRegisto : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registo)

        email = findViewById(R.id.etEmail)
        nome = findViewById(R.id.etNome)
        pass = findViewById(R.id.etPass)
        btnRegisto = findViewById(R.id.btnRegisto)

        btnRegisto.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?) {
                if(TextUtils.isEmpty(email.text.toString()) || TextUtils.isEmpty(nome.text.toString()) || TextUtils.isEmpty(pass.text.toString())){
                    Toast.makeText(this@Registo, "Tem de preencher todos os campos!",Toast.LENGTH_SHORT).show()
                } else {
                    registo()
                }
            }
        })

    }

    fun registo() {
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.userRegister(email.text.toString(), nome.text.toString(), pass.text.toString())

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful){
                    val c: User = response.body()!!
                    Toast.makeText(this@Registo, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@Registo, MainActivity::class.java))
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@Registo, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

