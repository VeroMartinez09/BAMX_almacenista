package mx.tec.bamx_almacenista

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.detalle_entrega.*
import kotlinx.android.synthetic.main.donativo_editable.*
import kotlinx.android.synthetic.main.donativo_entregado.*
import kotlinx.android.synthetic.main.toolbar.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DonativoEntregado : AppCompatActivity() {

    lateinit var queue: RequestQueue
    var idBodega: Int = 0
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        var total: Int = 0
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donativo_entregado)

        queue = Volley.newRequestQueue(this@DonativoEntregado)
        id = intent.getIntExtra("id", 0)
        idBodega = intent.getIntExtra("idBodega", 0)

        println("id " + id)

        val SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

        var lista = intent.getStringArrayListExtra("lista") as ArrayList<String>
        println("lista " + lista)

        //txtDonativo.text = "DONATIVO " + (lista?.get(0) ?: 0)
        if (lista != null) {
            txtDonativo.text = "DONATIVO " + lista.get(1)
            txtFechaHora.text = lista.get(2)
            txtUbicacion.text = lista.get(3)
            txtOperador.text = lista.get(4) + ' ' + lista.get(5) + ' ' + lista.get(6)

            txtAbarrote.text = lista.get(7)
            txtFrutaVerdura.text = lista.get(8)
            txtPan.text = lista.get(9)
            txtNoComestible.text = lista.get(10)
            txtFechaHora.text = SimpleDateFormat.format(Date())

            total =
                lista.get(7).toInt() + lista.get(8).toInt() + lista.get(9).toInt() + lista.get(10).toInt()
        }

        txtTotal.text = total.toString()
        lista.add(SimpleDateFormat.format(Date()))

        icon_Back.setOnClickListener {
            val intent = Intent(this, Detalle_Entrega::class.java)
            intent.putExtra("idBodega", idBodega)
            intent.putExtra("id", id)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        btnGuardar.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setCancelable(false)
                .setTitle(resources.getString(R.string.titulo))
                .setMessage(resources.getString(R.string.mensaje))
                .setNegativeButton(resources.getString(R.string.no)) { dialog, which ->
                    // Respond to negative button press
                }
                .setPositiveButton(resources.getString(R.string.si)) { dialog, which ->
                    // Respond to positive button press
                    update()
                    }
                .show()
        }

        btnEditar.setOnClickListener {
            val intent = Intent(this@DonativoEntregado, DonativoEditable::class.java)
            intent.putExtra("idBodega", idBodega)
            intent.putExtra("id", id)
            intent.putStringArrayListExtra("lista", lista)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        icon_salir.setOnClickListener {
            logout()
        }
    }

    fun update(){
        val datos = JSONObject()
        datos.put("kg_abarrotes", txtAbarrote.text.toString())
        datos.put("kg_frutas_verduras", txtFrutaVerdura.text.toString())
        datos.put("kg_pan", txtPan.text.toString())
        datos.put("kg_no_comestibles", txtNoComestible.text.toString())
        datos.put("estatus", "Completado")

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.PATCH,
            "http://192.168.3.48:5000/warehouseman/editar-detalles/${idBodega}/${id}",
            datos,
            { response ->
                    MaterialAlertDialogBuilder(this)
                        .setCancelable(false)
                        .setMessage(resources.getString(R.string.ok))
                        .setPositiveButton("0K") { dialog, which ->
                            // Respond to positive button press
                            val intent = Intent(this@DonativoEntregado, ProximasEntregas::class.java)
                            intent.putExtra("idBodega", idBodega)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                        .show()
                Log.e("VOLLEYRESPONSE", response.toString())
            },
            { error ->
                    Toast.makeText(
                        this,
                        "Algo salió mal, vuelve a intentarlo.",
                        Toast.LENGTH_LONG
                    ).show()
                Log.e("VOLLEYRESPONSE", error.message!!)
            }
        )
        queue.add(jsonObjectRequest)
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
                    val intent = Intent(this@DonativoEntregado, Login::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    //this.finish()
                }
                .show()
        }
    }

}