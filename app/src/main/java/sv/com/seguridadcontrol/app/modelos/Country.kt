package sv.com.seguridadcontrol.app.modelos

data class Countries(
    val data: List<Ctry>,
    val status: Boolean
)


data class Ctry(
    val codigo_pais: String,
    val estado_pais: String,
    val id_pais: String,
    val pais: String,
    val prefijo_telefonico: String
)