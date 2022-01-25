package sv.com.seguridadcontrol.app.erp.tecnicos

import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_prov_materiales.*
import kotlinx.android.synthetic.main.activity_tickets.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.adapter.TicketsAdapter
import sv.com.seguridadcontrol.app.modelos.Orden
import sv.com.seguridadcontrol.app.modelos.TicketData
import sv.com.seguridadcontrol.app.modelos.Tickets
import sv.com.seguridadcontrol.app.viewmodel.OrdersViewModel
import sv.com.seguridadcontrol.app.viewmodel.TicketProvisioningViewModel
import sv.com.seguridadcontrol.app.viewmodel.TicketsViewModel

class TicketsHoyActivity : AppCompatActivity() {
    private lateinit var adapter: TicketsAdapter
    private lateinit var ticketsViewModel: TicketsViewModel
    var ticketsList:ArrayList<TicketData> = ArrayList()
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tickets)
        val SHARED:String=getString(R.string.sharedpref)
        sharedPreferences = getSharedPreferences(SHARED, 0)
        val token:String? = sharedPreferences!!.getString("token","")
        val userId:String? = sharedPreferences!!.getString("userId","")
        ticketsViewModel  =  ViewModelProvider(this).get(TicketsViewModel::class.java)
        val tf = Typeface.createFromAsset(assets,"fonts/Nunito-Bold.ttf")
        val toolbar =
            findViewById<Toolbar>(R.id.tool_bar) // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar)
        val lblToolbar = toolbar.findViewById<TextView>(R.id.lblToolbar)
        lblToolbar.text = "Tickets"
        lblToolbar.typeface = tf
        // getOrderList("110","orders_get_order_list","302d6b3a2ecd683c26e1f731897271ca757aa48fd3d802c53ff3a681108ffd1f")
        getTickets(userId!!,"1","1","1","ticket_show_programming_ticket",token!!)
        //getTickets("7","1","1","1","ticket_show_programming_ticket","302d6b3a2ecd683c26e1f731897271ca757aa48fd3d802c53ff3a681108ffd1f")
    }

    fun getTickets(userId:String,type:String,start:String,quantity:String,task:String,token:String){
        ticketsViewModel.getTicketsResultObserver().observe(this,{tickets->
            if(tickets.ticket != null) {
                tickets.ticket.forEach { t ->

                    var colorType="#000000"
                    if(t.ticket_color_type == null){
                        colorType=t.priority_color
                    }else{
                        colorType=t.ticket_color_type
                    }
                    var ticketCode="--"
                    if(t.ticket_code != null){
                        ticketCode = t.ticket_code
                    }

                    
                    val ticket = TicketData(
                        t.ticket_id,
                        t.id_usuario,
                        t.client_branch,
                        t.client_reason,
                        t.country,
                        t.creation_date,
                        t.deparment,
                        t.municipality,
                        t.munucipality_id,
                        t.order_canceled,
                        t.order_completed,
                        t.order_required,
                        t.priority_color,
                        t.programming_date,
                        t.revision_date,
                        t.revision_user_id,
                        t.service,
                        t.service_id,
                        t.ticket_address,
                        ticketCode,
                        t.ticket_color_type,
                        t.ticket_end_date,
                        t.ticket_priority,
                        t.ticket_priority_id,
                        t.ticket_progress,
                        t.ticket_start_date,
                        t.ticket_type,
                        t.ticket_type_id

                    )

                    ticketsList.add(ticket)

                }
                adapter = TicketsAdapter(ticketsList)
                rvTickets.layoutManager = LinearLayoutManager(this)
                CoroutineScope(Dispatchers.Main).launch {
                    rvTickets.adapter = adapter
                }
            }else{
                Toast.makeText(applicationContext,"No tienes tickets asignados",Toast.LENGTH_LONG).show()
            }


        })

        ticketsViewModel.getTickets(userId,type,start, quantity, task, token)


    }

}