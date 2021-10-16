package mx.tec.bamx_almacenista.ListView

data class CantidadEntrega (
    var id: Int,

    var folio: String,
    var fecha: String,
    var almacen: String,
    var nombre: String,
    var apPaterno: String,
    var apMaterno: String,

    var cantAbarrote: Int,
    var cantFruta: Int,
    var cantPan: Int,
    var cantNoComer: Int,

    var estatus: String
    )