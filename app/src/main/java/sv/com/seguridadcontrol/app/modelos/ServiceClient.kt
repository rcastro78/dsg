package sv.com.seguridadcontrol.app.modelos

data class ServicesClient(
    val data:List<ServiceClient>,
    val status: Boolean
)
data class ServiceClientSpr( val id_servicio_cliente: String,
                             val servicio: String)

data class ServiceClient(
    val descripcion_servicio_cliente: String,
    val estado_servicio_cliente: String,
    val id_cliente: String,
    val id_servicio: String,
    val id_servicio_cliente: String,
    val servicio: String
)