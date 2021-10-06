package mx.tec.bamx_almacenista

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.donativo_editable.*
import kotlinx.android.synthetic.main.donativo_entregado.*
import kotlinx.android.synthetic.main.toolbar.*
import mx.tec.bamx_almacenista.ListView.Model_Entrega

class DonativoEditable : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var i: Int = 0
        var estatus: Boolean = true
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donativo_editable)

        val operario = intent.getStringExtra("operario")

        val datos = listOf(
            Model_Entrega("Fernando", R.drawable.camion, "W01", "TLAHUAPAN", 50, 50, 50, 50),
            Model_Entrega("Ricardo", R.drawable.camion, "W02",  "REFRIGERADOS",70, 30, 10, 50),
            Model_Entrega("Lucio", R.drawable.camion, "W03",  "ZAPATA",90, 40, 50, 90),
            Model_Entrega("Cristián", R.drawable.camion, "W04",  "TLAHUAPAN",7, 50, 6, 30),
            Model_Entrega("Ángel", R.drawable.camion, "W05",  "REFRIGERADOS",8, 10, 6, 50),
            Model_Entrega("Alejandro", R.drawable.camion, "W06",  "TLAHUAPAN",0, 50, 9, 50),
        )

        while(estatus) {
            if(operario.equals(datos[i].operario)) {
                txtDonativoE.text = "DONATIVO " + datos[i].folio
                txtOperadorE.text = datos[i].operario
                txtUbicacionE.text = datos[i].almacen
                edtAbarrote.setText(datos[i].cantAbarrote.toString())
                edtFrutaVerdura.setText(datos[i].cantFruta.toString())
                edtPan.setText(datos[i].cantPan.toString())
                edtNoComestible.setText(datos[i].cantNoComer.toString())

                //exitProcess(0)
                estatus = false
            } else {
                i += 1
            }
        }
        println("INDEX "+ i)
        val total: Int
        total = datos[i].cantAbarrote + datos[i].cantFruta +datos[i].cantPan + datos[i].cantNoComer
        txtTotalE.text = total.toString()

        icon_Back.setOnClickListener {
            val intent = Intent(this, DonativoEntregado::class.java)
            intent.putExtra("operario", datos[i].operario)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        btnGuardarE.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.titulo))
                .setMessage(resources.getString(R.string.mensaje))
                .setNegativeButton(resources.getString(R.string.no)) { dialog, which ->
                    // Respond to negative button press
                }
                .setPositiveButton(resources.getString(R.string.si)) { dialog, which ->
                    // Respond to positive button press
                    MaterialAlertDialogBuilder(this,  R.style.ThemeOverlay_App_MaterialAlertDialog)
                        .setMessage(resources.getString(R.string.ok))
                        .setNeutralButton("OK") { dialog, which ->
                            // Respond to positive button press
                            val intent = Intent(this@DonativoEditable, ProximasEntregas::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                        .show()
                }
                .show()
        }

        btnCancelar.setOnClickListener {
            val intent = Intent(this@DonativoEditable, DonativoEntregado::class.java)
            intent.putExtra("operario", datos[i].operario)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }


    }
}