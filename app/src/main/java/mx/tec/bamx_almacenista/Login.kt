package mx.tec.bamx_almacenista

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.login.*

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        btnLogin.setOnClickListener {
            println("Diste click en el boton LogIn")
            // Origen, Destino
            // val intent = Intent(this@MainActivity, RegistrarDonativo::class.java)

            val intent = Intent(this, ProximasEntregas::class.java)
            // Quita login del stack y deja al MAin como principal
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}