package mx.tec.bamx_almacenista

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.detalle_entrega.*
import kotlinx.android.synthetic.main.donativo_entregado.*
import kotlinx.android.synthetic.main.layout_entrega.*
import kotlinx.android.synthetic.main.toolbar.*
import mx.tec.bamx_almacenista.ListView.Model_Entrega

class Detalle_Entrega : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var index: Int = 0
        var estatus: Boolean = true
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalle_entrega)

        val operario = intent.getStringExtra("operario")

        val datos = listOf(
            Model_Entrega("Fernando", R.drawable.camion, "W01", "TLAHUAPAN", 50, 50, 50, 50),
            Model_Entrega("Ricardo", R.drawable.camion, "W02",  "REFRIGERADOS",70, 30, 10, 50),
            Model_Entrega("Lucio", R.drawable.camion, "W03",  "ZAPATA",90, 40, 50, 90),
            Model_Entrega("Cristián", R.drawable.camion, "W04",  "TLAHUAPAN",7, 50, 6, 30),
            Model_Entrega("Ángel", R.drawable.camion, "W05",  "REFRIGERADOS",8, 10, 6, 50),
            Model_Entrega("Alejandro", R.drawable.camion, "W06",  "TLAHUAPAN",0, 50, 9, 50),
        )
        println("OPERARIO "+ operario)
        while(estatus) {
            if(operario.equals(datos[index].operario)) {
                txtAbarroteCant.text = datos[index].cantAbarrote.toString()
                txtFrutaVCant.text = datos[index].cantFruta.toString()
                txtPanCant.text = datos[index].cantPan.toString()
                txtNoComerCant.text = datos[index].cantNoComer.toString()
                //exitProcess(0)
                estatus = false
            } else {
                index += 1
            }
        }
        println("INDEX "+ index)

        val total: Int
        total = datos[index].cantAbarrote + datos[index].cantFruta +datos[index].cantPan + datos[index].cantNoComer
        println("TOTAL "+ total)
        txtTotalCant.text = total.toString()

        btnContinuar.setOnClickListener {
            val intent = Intent(this@Detalle_Entrega, DonativoEntregado::class.java)
            intent.putExtra("operario", datos[index].operario)
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