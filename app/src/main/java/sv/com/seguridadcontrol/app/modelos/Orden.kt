package sv.com.seguridadcontrol.app.modelos

data class Orden(
    val order_num:String,
    val order_id:String,
    val progress:String,
    val order_date:String,
    val order_start:String,
    val order_end:String,
    val order_color:String
)
