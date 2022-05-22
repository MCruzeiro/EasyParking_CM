package ipvc.estg.easyparking

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlin.system.exitProcess

class PerfilUtilizador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_utilizador)

        val sharedPref: SharedPreferences = getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )

        val sharedPref2: SharedPreferences = getSharedPreferences(
            getString(R.string.preference_file_key2), Context.MODE_PRIVATE
        )

        val nomeValue = sharedPref.getString(getString(R.string.nomeUtilizador),"")
        val emailValue = sharedPref2.getString(getString(R.string.emailUtilizador),"")

        //        val profileEmail= intent.getStringExtra("email")
        //        val profileNome = intent.getStringExtra("nome")

        findViewById<TextView>(R.id.nomeUtilizador).setText(nomeValue.toString())
        findViewById<TextView>(R.id.emailUtilizador).setText(emailValue.toString())

    }

    fun Search(view: android.view.View) {
        val intent = Intent(this, MapsActivity::class.java).apply {
        }
        startActivity(intent)
    }
    fun Back(view: android.view.View) {
        System.exit(-1)
    }

    fun sair(view: View) {
        startActivity(Intent(this@PerfilUtilizador, MainActivity::class.java))
    }
}