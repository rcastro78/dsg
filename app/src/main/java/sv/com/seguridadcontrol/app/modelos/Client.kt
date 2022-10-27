package sv.com.seguridadcontrol.app.modelos

data class Client(val data: List<ClientDetail>,
                  val status: Boolean)


data class ClientSpr(val nombre: String,val id_cliente: String)

data class ClientDetail(
    val apellido_cliente: String,
    val cantidad_equipo: String,
    val confirm_email: String,
    val correlativo_sac: String,
    val correo_electronico: String,
    val direccion_cliente: String,
    val direccion_sucursal: String,
    val dui_cliente: String,
    val estado_cliente: String,
    val estado_sucursal: String,
    val fin_sucursal: String,
    val giro_comercial_cliente: String,
    val id_clasificacion_cliente: String,
    val id_cliente: String,
    val id_empresa: String,
    val id_municipio: String,
    val id_prospecto: String,
    val id_sucursal: String,
    val id_usuario_cliente: String,
    val id_vendedor_asignado: String,
    val inicio_sucursal: String,
    val mail_pago_f: String,
    val movil_cliente: String,
    val nit_cliente: String,
    val nit_empresa: String,
    val nombre_cliente: String,
    val nombre_comercial_cliente: String,
    val nombre_contacto: String,
    val nombre_pago_f: String,
    val password_wialon: String,
    val phone_pago_f: String,
    val razon_social_cliente: String,
    val registro_cliente: String,
    val registro_sucursal: String,
    val sucursal: String,
    val telefono_cliente: String,
    val tipo_cliente: String,
    val tipo_persona: String,
    val tipo_sucursal: String,
    val usuario_wialon: String
)