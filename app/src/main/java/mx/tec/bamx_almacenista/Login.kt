package mx.tec.bamx_almacenista

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.login.*

class Login : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        sharedPreferences = getSharedPreferences("login",
            Context.MODE_PRIVATE)
        if(sharedPreferences.getString("usuario", "@") != "" &&
            sharedPreferences.getString("usuario", "@") != "@") {
            // Mandar al home
            val intent = Intent(this, ProximasEntregas::class.java)
            // Quita login del stack y deja al MAin como principal
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        val usuario = findViewById<EditText>(R.id.edtUsername)
        val password = findViewById<EditText>(R.id.edtPassword)

        btnLogin.setOnClickListener {
            val intent = Intent(this@Login, ProximasEntregas::class.java)
            println("Diste click en el boton LogIn")
            if(usuario.text.toString() == "Verito" && // <- Petición volley al API
                password.text.toString() == "mando"){
                // Usuario correcto
                with(sharedPreferences.edit()){
                    putString("usuario", usuario.text.toString())
                    commit()
                }
                startActivity(intent)
            }
            else {
                // Usuario incorrecto
                Toast.makeText(this,
                    "Usuario o contraseña incorrectas",
                    Toast.LENGTH_LONG).show()
            }

        }
    }
}