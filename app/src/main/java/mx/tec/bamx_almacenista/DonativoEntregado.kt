package mx.tec.bamx_almacenista

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

class DonativoEntregado : AppCompatActivity() {

    lateinit var queue: RequestQueue
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        var total: Int = 0
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donativo_entregado)

        queue = Volley.newRequestQueue(this@DonativoEntregado)
        val id = intent.getIntExtra("id", 0)

        println("id " + id)

        var lista = intent.getStringArrayListExtra("lista")
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

            total =
                lista.get(7).toInt() + lista.get(8).toInt() + lista.get(9).toInt() + lista.get(10).toInt()
        }

        txtTotal.text = total.toString()


        icon_Back.setOnClickListener {
            val intent = Intent(this, Detalle_Entrega::class.java)
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
                    val builder =
                        AlertDialog.Builder(this, R.style.ThemeOverlay_App_MaterialAlertDialog)
                    builder.setMessage(resources.getString(R.string.ok))
                    builder.setIcon(R.drawable.checked)
                    builder.setCancelable(false)
                    builder.setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
                        //Do something
                        update()
                        val intent = Intent(this@DonativoEntregado, ProximasEntregas::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }

                    builder.show()
                }
                .show()
        }

        btnEditar.setOnClickListener {
            val intent = Intent(this@DonativoEntregado, DonativoEditable::class.java)
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
        datos.put("estatus", "Completado")

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.PATCH,
            "http://192.168.3.36:5000/warehouseman/editar-detalles/${id}",
            datos,
            { response ->
                Log.e("VOLLEYRESPONSE", response.toString())
            },
            { error ->
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
                    this.finish()
                }
                .show()
        }
    }

}