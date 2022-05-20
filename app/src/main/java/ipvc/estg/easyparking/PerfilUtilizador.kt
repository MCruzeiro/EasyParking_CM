package ipvc.estg.easyparking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PerfilUtilizador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_utilizador)
    }

    fun Search(view: android.view.View) {
        val intent = Intent(this, MapsActivity::class.java).apply {
        }
        startActivity(intent)
    }
    fun Back(view: android.view.View) {}
}