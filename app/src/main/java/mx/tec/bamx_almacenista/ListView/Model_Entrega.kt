package mx.tec.bamx_almacenista.ListView

data class Model_Entrega (
    var operario: String,
    var imagen: Int, // tipo de objeto. También puede ser drawable, imageView
    var folio: String,
    var almacen: String,
    var cantAbarrote: Int,
    var cantFruta: Int,
    var cantPan: Int,
    var cantNoComer: Int,
        )