package sv.com.seguridadcontrol.app.modelos

data class ApprovalTicket(val request:ArrayList<TicketApprovRequest>)


data class TicketApprovRequest(
    val client_branch: String,
    val country_cod: String,
    val order_required: String,
    val programming_date: String,
    val request_num: String,
    val service: String,
    val service_cod: String,
    val ticket_color_type: String,
    val ticket_description: String,
    val ticket_id: String,
    val ticket_type: String,
    val ticket_type_cod: String,
    val work_id: String
)