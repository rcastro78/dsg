package sv.com.seguridadcontrol.app.modelos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import sv.com.seguridadcontrol.app.dao.TicketDAO

@Entity(tableName = TicketDAO.TABLE_NAME)
data class TicketData(

    @PrimaryKey val ticket_id: String,
    @ColumnInfo(name = "id_usuario") val id_usuario: Int,
    @ColumnInfo(name = "client_branch") val client_branch: String,
    @ColumnInfo(name = "client_reason") val client_reason: String,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "creation_date") val creation_date: String,
    @ColumnInfo(name = "deparment") val deparment: String,
    @ColumnInfo(name = "municipality") val municipality: String,
    @ColumnInfo(name = "munucipality_id") val munucipality_id: String,
    @ColumnInfo(name = "order_canceled") val order_canceled: String,
    @ColumnInfo(name = "order_completed") val order_completed: String,
    @ColumnInfo(name = "order_required") val order_required: String,
    @ColumnInfo(name = "priority_color") val priority_color: String,
    @ColumnInfo(name = "programming_date") val programming_date: String,
    @ColumnInfo(name = "revision_date") val revision_date: String,
    @ColumnInfo(name = "revision_user_id") val revision_user_id: String,
    @ColumnInfo(name = "service") val service: String,
    @ColumnInfo(name = "service_id") val service_id: String,
    @ColumnInfo(name = "ticket_address") val ticket_address: String,
    @ColumnInfo(name = "ticket_code") val ticket_code: String,
    @ColumnInfo(name = "ticket_color_type") val ticket_color_type: String,
    @ColumnInfo(name = "ticket_end_date") val ticket_end_date: String,
    @ColumnInfo(name = "ticket_priority") val ticket_priority: String,
    @ColumnInfo(name = "ticket_priority_id") val ticket_priority_id: String,
    @ColumnInfo(name = "ticket_progress") val ticket_progress: String,
    @ColumnInfo(name = "ticket_start_date") val ticket_start_date: String,
    @ColumnInfo(name = "ticket_type") val ticket_type: String,
    @ColumnInfo(name = "ticket_type_id") val ticket_type_id: String


    /*val client_branch: String,
    val client_id: String,
    val client_reason: String,
    val client_service_id: String,
    val country: String,
    val creation_date: String,
    val deparment: String,
    val municipality: String,
    val munucipality_id: String,
    val order_canceled: String,
    val order_completed: String,
    val order_required: String,
    val priority_color: String,
    val programming_date: String,
    val revision_date: String,
    val revision_user_id: String,
    val service: String,
    val service_id: String,
    val ticket_address: String,
    val ticket_code: String,
    val ticket_color_type: String,
    val ticket_end_date: String,
    val ticket_id: String,
    val ticket_priority: String,
    val ticket_priority_id: String,
    val ticket_progress: String,
    val ticket_start_date: String,
    val ticket_type: String,
    val ticket_type_id: String*/
)