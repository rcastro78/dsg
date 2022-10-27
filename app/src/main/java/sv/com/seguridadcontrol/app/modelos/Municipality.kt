package sv.com.seguridadcontrol.app.modelos
data class Municipalities(
    val data: List<Mcpty>,
    val status: Boolean
)
data class Mcpty(
    val codigo_municipio: String,
    val id_departamento: String,
    val id_municipio: String,
    val municipio: String
)

