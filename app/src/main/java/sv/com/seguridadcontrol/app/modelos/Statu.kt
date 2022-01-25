package sv.com.seguridadcontrol.app.modelos

data class StatusOrder(
    val date: String,
    val date_now: String,
    val facility: String,
    val image: Boolean,
    val image_signature: Boolean,
    val is_it_start: Boolean,
    val materials_quantity: Boolean,
    val order_progress: String,
    val ticket_progress: String
)