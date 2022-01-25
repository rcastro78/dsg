package sv.com.seguridadcontrol.app.fragments

import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_tickets.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.adapter.TicketsAdapter
import sv.com.seguridadcontrol.app.modelos.TicketData
import sv.com.seguridadcontrol.app.viewmodel.TicketsViewModel

class TicketsCompletedFragment: Fragment(){
    private lateinit var adapter: TicketsAdapter
    private lateinit var ticketsViewModel: TicketsViewModel
    var ticketsList:ArrayList<TicketData> = ArrayList()
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_tickets,container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val SHARED:String=getString(R.string.sharedpref)
        sharedPreferences = requireActivity().getSharedPreferences(SHARED, 0)
        val token:String? = sharedPreferences!!.getString("token","")
        val userId:String? = sharedPreferences!!.getString("userId","")
        ticketsViewModel  =  ViewModelProvider(this).get(TicketsViewModel::class.java)
        val tf = Typeface.createFromAsset(requireActivity().assets,"fonts/Nunito-Bold.ttf")

        // getOrderList("110","orders_get_order_list","302d6b3a2ecd683c26e1f731897271ca757aa48fd3d802c53ff3a681108ffd1f")
        getTickets(userId!!,"1","1","1","ticket_show_completed_ticket",token!!)
        //getTickets("7","1","1","1","ticket_show_programming_ticket","302d6b3a2ecd683c26e1f731897271ca757aa48fd3d802c53ff3a681108ffd1f")
    }

    fun getTickets(userId:String,type:String,start:String,quantity:String,task:String,token:String){
        ticketsList.clear()
        ticketsViewModel.getTicketsResultObserver().observe(viewLifecycleOwner,{tickets->
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

                    var ticketEndDate=""
                    if(t.ticket_end_date != null){
                        ticketEndDate=t.ticket_end_date
                    }

                    var ticketStartDate=""
                    if(t.ticket_start_date != null){
                        ticketStartDate=t.ticket_start_date
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
                        "",
                        "",
                        t.service,
                        t.service_id,
                        t.ticket_address,
                        ticketCode,
                        colorType,
                        ticketEndDate,
                        t.ticket_priority,
                        t.ticket_priority_id,
                        t.ticket_progress,
                        ticketStartDate,
                        t.ticket_type,
                        t.ticket_type_id
                    )

                    ticketsList.add(ticket)

                }
                adapter = TicketsAdapter(ticketsList)
                rvTickets.layoutManager = LinearLayoutManager(requireContext())
                CoroutineScope(Dispatchers.Main).launch {
                    rvTickets.adapter = adapter
                }
            }else{
                Toast.makeText(requireActivity().applicationContext,"No tienes tickets asignados", Toast.LENGTH_LONG).show()
            }


        })

        ticketsViewModel.getTickets(userId,type,start, quantity, task, token)


    }
}