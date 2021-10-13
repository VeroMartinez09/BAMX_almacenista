package mx.tec.bamx_almacenista

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.donativo_editable.*
import kotlinx.android.synthetic.main.donativo_entregado.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.icon_salir
import kotlinx.android.synthetic.main.toolbarsinflecha.*
import mx.tec.bamx_almacenista.ListView.CantidadEntrega
import mx.tec.bamx_almacenista.ListView.Model_Entrega
import org.json.JSONObject

class DonativoEditable : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var index: Int = 0
        var total: Int = 0
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donativo_editable)

        var queue = Volley.newRequestQueue(this@DonativoEditable)
        val url = "http://192.168.3.36:5000/warehouseman/editar-detalles/12"
        val datos = mutableListOf<CantidadEntrega>() // mutableListOf para lista dinámica

        val id = intent.getIntExtra("id", 0)
        //val id: Int = 12

        var lista = intent.getStringArrayListExtra("lista")

        if (lista != null) {
            txtDonativo.text = "DONATIVO " + lista.get(1)
            txtFechaHora.text = lista.get(2)
            txtUbicacion.text = lista.get(3)
            txtOperador.text = lista.get(4) + ' ' + lista.get(5) + ' ' + lista.get(6)

            edtAbarrote.setText(lista.get(7))
            edtFrutaVerdura.setText(lista.get(8))
            edtPan.setText(lista.get(9))
            edtNoComestible.setText(lista.get(10))

            // total = lista.get(7).toInt() + lista.get(8).toInt() + lista.get(9).toInt() + lista.get(10).toInt()
        }

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
                        array.getJSONObject(i).getInt("kg_no_comestibles")
                    ))


                if (id == datos[i].id) {
                    txtDonativoE.text = "DONATIVO " + datos[i].folio
                    txtFechaHoraE.text = datos[i].fecha
                    txtUbicacionE.text = datos[i].almacen
                    txtOperadorE.text = datos[i].nombre + ' ' + datos[i].apPaterno + ' ' + datos[i].apMaterno

                    edtAbarrote.setText(datos[i].cantAbarrote.toString())
                    edtFrutaVerdura.setText(datos[i].cantFruta.toString())
                    edtPan.setText(datos[i].cantPan.toString())
                    edtNoComestible.setText(datos[i].cantNoComer.toString())

                    total = datos[i].cantAbarrote + datos[i].cantFruta +datos[i].cantPan + datos[i].cantNoComer
                    txtTotalE.text = total.toString()

                    index = i

                }
            }

        }

            val error = Response.ErrorListener { error ->
                Log.e("ERROR", error.message!!)
            }

            val edita = JsonObjectRequest(
                Request.Method.PATCH,
                url,
                null,
                listener,
                error
            )
            queue.add(edita)


            btnConfirmaE.setOnClickListener {
                val intent = Intent(this, DonativoEntregado::class.java)
                intent.putExtra("operario", datos[index].id)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }

            btnCancelar.setOnClickListener {
                val intent = Intent(this@DonativoEditable, DonativoEntregado::class.java)
                intent.putExtra("operario", datos[index].id)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }


            icon_salir.setOnClickListener {
                logout()
            }

        }


    fun logout() {
        icon_salir.setOnClickListener{
            println("DISTE CLICK BRO")

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