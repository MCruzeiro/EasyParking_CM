package ipvc.estg.easyparking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pesquisa =findViewById<Button>(R.id.btnPesquisa)
        pesquisa.setOnClickListener{
            val intent= Intent(this@MainActivity,Pesquisa::class.java)
            startActivity(intent)
        }
    }


}

//Teste Commit