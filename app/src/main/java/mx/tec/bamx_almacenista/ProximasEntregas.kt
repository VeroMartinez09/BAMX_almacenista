package mx.tec.bamx_almacenista

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.cardview.widget.CardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.layout_entrega.*
import kotlinx.android.synthetic.main.proximas_entregas.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.icon_salir
import kotlinx.android.synthetic.main.toolbarsinflecha.*
import mx.tec.bamx_almacenista.ListView.Entregas_Adapter
import mx.tec.bamx_almacenista.ListView.Model_Entrega

class ProximasEntregas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.proximas_entregas)

        val lstElemnetos = findViewById<ListView>(R.id.lstEntregas)

        val datos = listOf(
            Model_Entrega("Fernando", R.drawable.camion, "W01", "TLAHUAPAN", 50, 50, 50, 50),
            Model_Entrega("Ricardo", R.drawable.camion, "W02",  "REFRIGERADOS",70, 30, 10, 50),
            Model_Entrega("Lucio", R.drawable.camion, "W03",  "ZAPATA",90, 40, 50, 90),
            Model_Entrega("Cristián", R.drawable.camion, "W04",  "TLAHUAPAN",7, 50, 6, 30),
            Model_Entrega("Ángel", R.drawable.camion, "W05",  "REFRIGERADOS",8, 10, 6, 50),
            Model_Entrega("Alejandro", R.drawable.camion, "W06",  "TLAHUAPAN",0, 50, 9, 50),
        )

        val adaptador = Entregas_Adapter(
            this@ProximasEntregas,
            R.layout.layout_entrega,
            datos
        )
        lstElemnetos.adapter = adaptador

        lstElemnetos.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@ProximasEntregas, Detalle_Entrega::class.java)
            intent.putExtra("operario", datos[position].operario)
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