package mx.tec.bamx_almacenista

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.donativo_editable.*
import kotlinx.android.synthetic.main.login.*
import mx.tec.bamx_almacenista.ListView.Model_Entrega
import org.json.JSONObject

class Login : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        var queue = Volley.newRequestQueue(this@Login)

        sharedPreferences = getSharedPreferences("login",
            Context.MODE_PRIVATE)
        if(sharedPreferences.getString("usuario", "@") != "" &&
            sharedPreferences.getString("usuario", "@") != "@") {
            // Mandar al home
            val intent = Intent(this, ProximasEntregas::class.java)
            // Quita login del stack y deja al MAin como principal
            intent.putExtra("user", edtUsername.text.toString())
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val intent = Intent(this@Login, ProximasEntregas::class.java)
            println("Diste clic en el boton LogIn")
        //    if(usuario.text.toString() == "Verito" && // <- Petición volley al API
        //        password.text.toString() == "mando")

            val datos = JSONObject()
            datos.put("username", edtUsername.text.toString())
            datos.put("contrasena", edtPassword.text.toString())

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST,
                "http://192.168.3.48:5000/warehouseman/login",
                datos,
                { response ->
                        // Usuario correcto
                        val idBodega = response.getJSONObject("data").getJSONObject("almacenista")
                        datos.put("idBodega", idBodega.getInt("idBodega"))

                        println("DATOS " + datos)

                        with(sharedPreferences.edit()) {
                            putString("usuario", edtUsername.text.toString())
                            commit()
                        }
                        intent.putExtra("idBodega", datos.getInt("idBodega"))
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    Log.e("USER ", response.toString())

                },
                { error ->
                        // Usuario incorrecto
                        Log.e("USER ", error.message!!)
                        Toast.makeText(
                            this,
                            "Usuario o contraseña incorrectas",
                            Toast.LENGTH_LONG
                        ).show()

                }

            )
            queue.add(jsonObjectRequest)
        }
    }

}