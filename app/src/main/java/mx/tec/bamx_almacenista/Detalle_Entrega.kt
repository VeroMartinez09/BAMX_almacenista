package mx.tec.bamx_almacenista

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.detalle_entrega.*
import kotlinx.android.synthetic.main.donativo_entregado.*
import kotlinx.android.synthetic.main.layout_entrega.*
import kotlinx.android.synthetic.main.toolbar.*
import mx.tec.bamx_almacenista.ListView.CantidadEntrega
import org.json.JSONObject

class Detalle_Entrega : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var index: Int = 0
        var total: Int = 0

        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalle_entrega)

        var queue = Volley.newRequestQueue(this@Detalle_Entrega)
        val url = "http://192.168.3.30:5000/warehouseman/detalle-entrega/7/8"
        val datos = mutableListOf<CantidadEntrega>() // mutableListOf para lista dinámica
        var lista = ArrayList<String>()

        val id = intent.getIntExtra("id", 0)
        //val id: Int = 12
        println("id "+ id)

        val listener = Response.Listener<JSONObject> { response ->
            Log.e("RESPONSE", response.toString())

            val array = response.getJSONArray("data")
            for (i in 0 until array.length()) {
                datos.add(
                    CantidadEntrega(array.getJSONObject(i).getInt("idDonativo"),
                        array.getJSONObject(i).getString("folio"),
                        array.getJSONObject(i).getString("fecha"),
                        array.getJSONObject(i).getString("bodega"),
                        array.getJSONObject(i).getString("nombre"),
                        array.getJSONObject(i).getString("apellidoPaterno"),
                        array.getJSONObject(i).getString("apellidoMaterno"),

                        array.getJSONObject(i).getInt("kg_frutas_verduras"),
                        array.getJSONObject(i).getInt("kg_pan"),
                        array.getJSONObject(i).getInt("kg_abarrotes"),
                        array.getJSONObject(i).getInt("kg_no_comestibles"),

                        array.getJSONObject(i).getString("estatus")

                    ))
                if (id == datos[i].id) {
                    txtAbarroteCant.text = datos[i].cantAbarrote.toString()
                    txtFrutaVCant.text = datos[i].cantFruta.toString()
                    txtPanCant.text = datos[i].cantPan.toString()
                    txtNoComerCant.text = datos[i].cantNoComer.toString()

                    total = datos[i].cantAbarrote + datos[i].cantFruta +datos[i].cantPan + datos[i].cantNoComer
                    //println("TOTAL "+ total)
                    txtTotalCant.text = total.toString()

                    index = i

                    lista.add(datos[i].id.toString())

                    lista.add(datos[i].folio)
                    lista.add(datos[i].fecha)
                    lista.add(datos[i].almacen)
                    lista.add(datos[i].nombre)
                    lista.add(datos[i].apPaterno)
                    lista.add(datos[i].apMaterno)

                    lista.add(datos[i].cantAbarrote.toString())
                    lista.add(datos[i].cantFruta.toString())
                    lista.add(datos[i].cantPan.toString())
                    lista.add(datos[i].cantNoComer.toString())

                }
            }
        }
       /* println("index "+ index)
        while(estatus) {
            if (id != null) {
                if (id == datos[index].id) {
                    txtAbarroteCant.text = datos[index].cantAbarrote.toString()
                    txtFrutaVCant.text = datos[index].cantFruta.toString()
                    txtPanCant.text = datos[index].cantPan.toString()
                    txtNoComerCant.text = datos[index].cantNoComer.toString()
                    //exitProcess(0)
                    estatus = false
                } else if(datos.length <= index){
                    index += 1
                } else {
                    estatus = false
                }
            }
        }*/

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


        btnContinuar.setOnClickListener {
            val intent = Intent(this@Detalle_Entrega, DonativoEntregado::class.java)
            intent.putExtra("id", datos[index].id)
            intent.putStringArrayListExtra("lista", lista)
            // Quita login del stack y deja al MAin como principal
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        ic_Close.setOnClickListener{
            val intent = Intent(this, ProximasEntregas::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

    }
}