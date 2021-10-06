package mx.tec.bamx_almacenista

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.detalle_entrega.*
import kotlinx.android.synthetic.main.donativo_entregado.*
import kotlinx.android.synthetic.main.toolbar.*
import mx.tec.bamx_almacenista.ListView.Model_Entrega

class DonativoEntregado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var index: Int = 0
        var estatus: Boolean = true
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donativo_entregado)

        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Habilita el botón de retroceso

        //val builder = AlertDialog.Builder(this)

        /*btnGuardar.setOnClickListener {
            builder.setTitle("Enviar información")
            builder.setMessage("¿Está seguro que desea realizar esta acción?")
            builder.setPositiveButton("SI") { dialogInterface: DialogInterface, i: Int ->
                //Do something
            }
            builder.setNegativeButton("NO") { dialogInterface: DialogInterface, i: Int -> }
            builder.show()

        }*/

        val datos = listOf(
            Model_Entrega("Fernando", R.drawable.camion, "W01", "TLAHUAPAN", 50, 50, 50, 50),
            Model_Entrega("Ricardo", R.drawable.camion, "W02",  "REFRIGERADOS",70, 30, 10, 50),
            Model_Entrega("Lucio", R.drawable.camion, "W03",  "ZAPATA",90, 40, 50, 90),
            Model_Entrega("Cristián", R.drawable.camion, "W04",  "TLAHUAPAN",7, 50, 6, 30),
            Model_Entrega("Ángel", R.drawable.camion, "W05",  "REFRIGERADOS",8, 10, 6, 50),
            Model_Entrega("Alejandro", R.drawable.camion, "W06",  "TLAHUAPAN",0, 50, 9, 50),
        )

        val operario = intent.getStringExtra("operario")

        while(estatus) {
            if(operario.equals(datos[index].operario)) {
                txtDonativo.text = "DONATIVO " + datos[index].folio
                txtOperador.text = datos[index].operario
                txtUbicacion.text = datos[index].almacen
                txtAbarrote.text = datos[index].cantAbarrote.toString()
                txtFrutaVerdura.text = datos[index].cantFruta.toString()
                txtPan.text = datos[index].cantPan.toString()
                txtNoComestible.text = datos[index].cantNoComer.toString()
                //exitProcess(0)
                estatus = false
            } else {
                index += 1
            }
        }

        val total: Int
        total = datos[index].cantAbarrote + datos[index].cantFruta +datos[index].cantPan + datos[index].cantNoComer
        txtTotal.text = total.toString()

        icon_Back.setOnClickListener {
            val intent = Intent(this, Detalle_Entrega::class.java)
            intent.putExtra("operario", datos[index].operario)
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
                    val builder = AlertDialog.Builder(this,  R.style.ThemeOverlay_App_MaterialAlertDialog)
                    builder.setMessage(resources.getString(R.string.ok))
                    builder.setIcon(R.drawable.checked)
                    builder.setCancelable(false)
                    builder.setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
                            //Do something
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
            intent.putExtra("operario", datos[index].operario)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        /*
        val builder = AlertDialog.Builder(this)
        btnGuardar.setOnClickListener {
            builder.setCan
            builder.setTitle("Enviar información")
            builder.setMessage("¿Está seguro que desea realizar esta acción?")
            builder.setPositiveButton("SI") { dialogInterface: DialogInterface, i: Int ->
                //Do something
            }
            builder.setNegativeButton("NO") { dialogInterface: DialogInterface, i: Int -> }
            builder.show()

        }*/
    }

}