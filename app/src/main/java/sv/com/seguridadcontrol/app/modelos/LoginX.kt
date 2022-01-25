package sv.com.seguridadcontrol.app.modelos

data class LoginX(
    val id_user_type: String,
    val ticket_type_id: String,
    val token: String,
    val user: String,
    val userID: String,
    val userType: String,
    val user_lastname: String,
    val user_name: String
)