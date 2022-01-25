package sv.com.seguridadcontrol.app.modelos

data class Tickets(
    val ticket_id: String,
    val ticket_code: String,
    val ticket_color_type: String,
    val ticket_type: String,
    val ticket_progress: String,
    val ticket_priority: String,
    val priority_color: String,

)