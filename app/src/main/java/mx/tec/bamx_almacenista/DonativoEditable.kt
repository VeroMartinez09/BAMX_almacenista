package mx.tec.bamx_almacenista

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.donativo_editable.*
import kotlinx.android.synthetic.main.donativo_entregado.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.icon_salir
import kotlinx.android.synthetic.main.toolbarsinflecha.*
import org.json.JSONObject

class DonativoEditable : AppCompatActivity() {

    lateinit var queue: RequestQueue
    lateinit var lista: ArrayList<String>
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.donativo_editable)

        queue = Volley.newRequestQueue(this@DonativoEditable)

        id = intent.getIntExtra("id", 0)
        //val id: Int = 12

        lista = intent.getStringArrayListExtra("lista") as ArrayList<String>
        println("listaEDIT " + lista)

        if (lista != null) {
            txtDonativoE.text = "DONATIVO " + lista.get(1)
            txtFechaHoraE.text = lista.get(2)
            txtUbicacionE.text = lista.get(3)
            txtOperadorE.text = lista.get(4) + ' ' + lista.get(5) + ' ' + lista.get(6)

            edtAbarrote.setText(lista.get(7))
            edtFrutaVerdura.setText(lista.get(8))
            edtPan.setText(lista.get(9))
            edtNoComestible.setText(lista.get(10))

            // total = lista.get(7).toInt() + lista.get(8).toInt() + lista.get(9).toInt() + lista.get(10).toInt()
        }
        txtTotalE.text = "Calculando"

            btnConfirmaE.setOnClickListener {
                if (edtAbarrote.text.isEmpty())
                    edtAbarrote.setText(0)
                else if(edtFrutaVerdura.text.isEmpty())
                    edtFrutaVerdura.setText(0)
                else if(edtPan.text.isEmpty())
                    edtPan.setText(0)
                else if(edtNoComestible.text.isEmpty())
                    edtNoComestible.setText(0)
                /*
                else if (edtAbarrote.text.isDigitsOnly())
                    edtAbarrote.setError("¡Este valor es inválido!")
                else if(edtFrutaVerdura.text.isDigitsOnly())
                    edtFrutaVerdura.setError("¡Este valor es inválido!")
                else if (edtPan.text.isDigitsOnly())
                    edtPan.setError("¡Este valor es inválido!")
                else if (edtNoComestible.text.isDigitsOnly())
                    edtNoComestible.setError("¡Este valor es inválido!")*/
                else
                    onClic()
                }


            btnCancelar.setOnClickListener {
                val intent = Intent(this@DonativoEditable, DonativoEntregado::class.java)
                intent.putExtra("id", id)
                intent.putStringArrayListExtra("lista", lista)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }

            icon_salir.setOnClickListener {
                logout()
            }

        }
    fun onClic() {
        update()
        val intent = Intent(this, DonativoEntregado::class.java)
        intent.putExtra("id", id)
        intent.putStringArrayListExtra("lista", lista)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun update(){
        val datos = JSONObject()
        datos.put("kg_frutas_verduras", edtAbarrote.text.toString())
        datos.put("kg_pan", edtFrutaVerdura.text.toString())
        datos.put("kg_abarrotes", edtPan.text.toString())
        datos.put("kg_no_comestibles", edtNoComestible.text.toString())
        datos.put("estatus", "Completado")

        lista.add(7, (datos.getString("kg_frutas_verduras")))
        lista.add(8, (datos.getString("kg_pan")))
        lista.add(9, (datos.getString("kg_abarrotes")))
        lista.add(10, (datos.getString("kg_no_comestibles")))

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.PATCH,
            "http://192.168.3.30:5000/warehouseman/editar-detalles/8",
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