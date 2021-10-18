package mx.tec.bamx_almacenista

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.layout_entrega.*
import kotlinx.android.synthetic.main.proximas_entregas.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.icon_salir
import kotlinx.android.synthetic.main.toolbarsinflecha.*
import mx.tec.bamx_almacenista.ListView.Entregas_Adapter
import com.android.volley.toolbox.Volley
import mx.tec.bamx_almacenista.ListView.Model_Entrega
import org.json.JSONObject

class ProximasEntregas : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.proximas_entregas)

        var idBodega = intent.getIntExtra("idBodega", 0)

        var queue = Volley.newRequestQueue(this@ProximasEntregas)
        val url = "http://192.168.3.36:5000/warehouseman/datos-entrega/${idBodega}"
        val datos = mutableListOf<Model_Entrega>() // mutableListOf para lista dinámica

        val listener = Response.Listener<JSONObject> { response ->
            Log.e("RESPONSE", response.toString())
            val array = response.getJSONArray("data")
            for (i in 0 until array.length()) {
                    datos.add(
                        Model_Entrega(
                            array.getJSONObject(i).getInt("id_Donation"),
                            array.getJSONObject(i).getInt("idBodega"),
                            array.getJSONObject(i).getString("nombre"),
                            array.getJSONObject(i).getString("apellidoMaterno"),
                            array.getJSONObject(i).getString("apellidoPaterno"),
                            R.drawable.camion,
                            "\n" + array.getJSONObject(i).getInt("folio")

                        )
                    )
            }

            val adaptador = Entregas_Adapter(
                this@ProximasEntregas,
                R.layout.layout_entrega,
                datos
            )
            lstEntregas.adapter = adaptador

        }
            val error = Response.ErrorListener { error ->
                Log.e("ERROR", error.message!!)
            }
            val peticion = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                listener,
                error
            )
            queue.add(peticion)


        lstEntregas.setOnItemClickListener { parent, view, position, id ->
                val intent = Intent(this@ProximasEntregas, Detalle_Entrega::class.java)
                intent.putExtra("idBodega", idBodega)
                intent.putExtra("id", datos[position].id)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }

            icon_salir.setOnClickListener {
                logout()
            }
    }

    fun logout() {
        icon_salir.setOnClickListener{

            val sharedPreferences = getSharedPreferences("login",
                Context.MODE_PRIVATE)

            MaterialAlertDialogBuilder(this)
                .setCancelable(false)
                .setTitle(resources.getString(R.string.tituloS))
                .setMessage(resources.getString(R.string.mensajeS))
                .setNegativeButton(resources.getString(R.string.no)) { dialog, which ->
                    // Respond to negative button press
                }
                .setPositiveButton(resources.getString(R.string.si)) { dialog, which ->

                    with(sharedPreferences.edit()){
                        remove("usuario")
                        commit()
                    }
                    this.finish()
                }
                .show()
        }
    }
}