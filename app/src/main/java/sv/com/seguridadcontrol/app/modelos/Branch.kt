package sv.com.seguridadcontrol.app.modelos

data class Branch(val data: List<BranchDetail>,
                  val status: Boolean)

data class BranchSpr(val id_sucursal: String,val nombre_comercial: String)

data class BranchDetail(
    val departamento: String,
    val direccion_sucursal: String,
    val estado_sucursal: String,
    val fin_sucursal: String,
    val id_departamento: String,
    val id_empresa: String,
    val id_municipio: String,
    val id_pais: String,
    val id_sucursal: String,
    val inicio_sucursal: String,
    val municipio: String,
    val nombre_comercial: String,
    val pais: String,
    val razon_social: String,
    val registro_sucursal: String,
    val sucursal: String,
    val tipo_sucursal: String
)