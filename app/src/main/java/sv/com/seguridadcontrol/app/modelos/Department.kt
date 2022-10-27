package sv.com.seguridadcontrol.app.modelos


data class Dep(
    val codigo_departamento: String,
    val departamento: String,
    val id_departamento: String,
    val id_pais: String
)

data class Departments(
    val data: List<Dep>,
    val status: Boolean
)