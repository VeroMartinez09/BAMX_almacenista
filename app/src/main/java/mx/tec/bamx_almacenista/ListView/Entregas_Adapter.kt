package mx.tec.bamx_almacenista.ListView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import mx.tec.bamx_almacenista.R
import org.w3c.dom.Text

class Entregas_Adapter(val context: Context,
                       val layout: Int,
                       val dataSource: List<Model_Entrega>): BaseAdapter() {
    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    // getSystemService -> regresa un tipo de dato genérico,
    // por lo que hay que convertirlo al tipo que lo ocupamos

    override fun getView(position: Int, // Renderiza cada elemento de la lista
                         convertView: View?,
                         parent: ViewGroup?): View {
        val view = inflater.inflate(layout, parent, false)
        // inflater -> lee un recurso xml y crea una instancia de el

        // Cargar datos del dataSource en el elemento cargado (en la view)
        val imagen = view.findViewById<ImageView>(R.id.imgCamion)
        val operario = view.findViewById<TextView>(R.id.txtOperario)
        val folio = view.findViewById<TextView>(R.id.txtFolio)


        // Recuperando un elemento a la vez del dataSource
        // getItem devuelve Any (cualquier clase) por lo que hay que "castearlo"
        // convertirlo al tipo que ocupamos
        val elemento = getItem(position) as Model_Entrega

        // Asignar valores a los controles
        operario.text = elemento.operario
        folio.text = elemento.folio
        imagen.setImageResource(elemento.imagen as Int)


        return view;
    }
}