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
import java.util.*
import kotlin.collections.ArrayList

class DonativoEditable : AppCompatActivity() {

    lateinit var queue: RequestQueue
    lateinit var lista: ArrayList<String>
    var idBodega: Int = 0
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.donativo_editable)

        queue = Volley.newRequestQueue(this@DonativoEditable)

        id = intent.getIntExtra("id", 0)
        idBodega = intent.getIntExtra("idBodega", 0)
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
            txtFechaHoraE.text = lista.get(11)

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
                else
                    onClic()
                }


            btnCancelar.setOnClickListener {
                val intent = Intent(this@DonativoEditable, DonativoEntregado::class.java)
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
    fun onClic() {
        lista.add(7, edtAbarrote.text.toString())
        lista.add(8, edtFrutaVerdura.text.toString())
        lista.add(9, edtPan.text.toString())
        lista.add(10, edtNoComestible.text.toString())

        val intent = Intent(this, DonativoEntregado::class.java)
        intent.putExtra("idBodega", idBodega)
        intent.putExtra("id", id)
        intent.putStringArrayListExtra("lista", lista)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
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
                    val intent = Intent(this@DonativoEditable, Login::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    //this.finish()
                }
                .show()
        }
    }
}