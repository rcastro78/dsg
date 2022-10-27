package sv.com.seguridadcontrol.app.modelos

data class ProspectClient(val data: List<ProspectClientDetail>,
                  val status: Boolean)

data class ProspectClientSpr(val id: String,
                             val nombre_comercial: String)

data class ProspectClientDetail(
    val celular_prospecto: String,
    val comentario: String,
    val departamento: String,
    val email_prospecto: String,
    val giro_comercial: String,
    val id: String,
    val nombre_comercial: String,
    val nombre_prospecto: String,
    val pais: String,
    val telefono_prospecto: String
)