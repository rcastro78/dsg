package sv.com.seguridadcontrol.app.erp.autorizaciones

import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_autorizaciones.*

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.adapter.TicketsAuthAdapter
import sv.com.seguridadcontrol.app.dao.AppDatabase
import sv.com.seguridadcontrol.app.modelos.TicketApprovRequest
import sv.com.seguridadcontrol.app.modelos.TicketData
import sv.com.seguridadcontrol.app.viewmodel.TicketsViewModel

class AutorizacionesActivity : AppCompatActivity() {


    private lateinit var adapter: TicketsAuthAdapter
    private lateinit var ticketsViewModel: TicketsViewModel
    var ticketsList:ArrayList<TicketApprovRequest> = ArrayList()
    private var sharedPreferences: SharedPreferences? = null

    override fun onPause() {
        super.onPause()
        finish()
    }

    override fun onResume() {
        super.onResume()
        val SHARED:String=getString(R.string.sharedpref)
        sharedPreferences = getSharedPreferences(SHARED, 0)
        val token:String? = sharedPreferences!!.getString("token","")
        getApprovalTickets("1","ticket_show_request",token!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autorizaciones)

        ticketsViewModel  =  ViewModelProvider(this).get(TicketsViewModel::class.java)
        val tf = Typeface.createFromAsset(assets,"fonts/Nunito-Bold.ttf")

        val toolbar =
            findViewById<Toolbar>(R.id.tool_bar) // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar)
        val lblToolbar = toolbar.findViewById<TextView>(R.id.lblToolbar)
        lblToolbar.text = "Autorizaciones"
        lblToolbar.typeface = tf

    }




    fun getApprovalTickets(type:String,task:String,token:String){
        ticketsViewModel.getTicketsApprovalResultObserver().observe(this){tickets->
            tickets.request.forEach { t->
                /*
                * "ticket_id": "818",
"work_id": "1",
"service": "GPS",
"service_cod": "GPS",
"ticket_type_cod": "INST",
"ticket_type": "Instalacion de GPS",
"ticket_color_type": "#E85A8E",
"client_branch": "Transporte Lemus",
"ticket_description": "1",
"country_cod": "SLV",
"order_required": "1",
"programming_date": "14 Marzo 2022",
"request_num": "1"
                * */

                var colorType = "#000000"
                if (t.ticket_color_type == null) {
                    colorType = "#003300"
                } else {
                    colorType = t.ticket_color_type
                }


                var branch = t.client_branch
                var programming_date = t.programming_date


                ticketsList.add(TicketApprovRequest(branch,t.country_cod,t.order_required,programming_date,t.request_num,
                t.service,t.service_cod,colorType,t.ticket_description,t.ticket_id,t.ticket_type,t.ticket_type_cod,
                t.work_id))

            }


            adapter = TicketsAuthAdapter(ticketsList,token)
            rvAutorizaciones.layoutManager = LinearLayoutManager(this@AutorizacionesActivity)
            CoroutineScope(Dispatchers.Main).launch {
                rvAutorizaciones.adapter = adapter
            }


        }
        ticketsViewModel.getTicketsToApprove(type, task, token)
    }


   /* fun getTickets(userId:String,type:String,start:String,quantity:String,task:String,token:String){
        ticketsList.clear()
        val db = AppDatabase.getInstance(application)
        CoroutineScope(Dispatchers.IO).launch {

        }


        ticketsViewModel.getTicketsResultObserver().observe(this) { tickets ->
            if (tickets.ticket != null) {
                tickets.ticket.forEach { t ->

                    var colorType = "#000000"
                    if (t.ticket_color_type == null) {
                        colorType = t.priority_color
                    } else {
                        colorType = t.ticket_color_type
                    }
                    var ticketCode = "--"
                    if (t.ticket_code != null) {
                        ticketCode = t.ticket_code
                    }
                    var ticketEndDate = ""
                    if (t.ticket_end_date != null) {
                        ticketEndDate = t.ticket_end_date
                    }

                    var ticketStartDate = ""
                    if (t.ticket_start_date != null) {
                        ticketStartDate = t.ticket_start_date
                    }
                    val ticket = TicketData(
                        t.ticket_id,
                        t.id_usuario,
                        t.client_branch,
                        "",
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
                    CoroutineScope(Dispatchers.IO).launch {
                        db.iTicketDAO.insert(ticket)
                    }
                    CoroutineScope(Dispatchers.Main).launch {
                        if(ticket.ticket_type.contains("Instalacion de GPS") ||
                            ticket.ticket_type.contains("Homologacion de GPS"))
                        ticketsList.add(ticket)
                    }

                }

            } else {
                Toast.makeText(
                    applicationContext,
                    "No tienes tickets por autorizar",
                    Toast.LENGTH_LONG
                ).show()
            }


        }

        ticketsViewModel.getTickets(userId,type,start, quantity, task, token)


    }*/


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_transaccional_1, menu)

        // return true so that the menu pop up is opened
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem):Boolean
    {
        if (item.itemId == R.id.action_back) {
            onBackPressed()
        }
        return true
    }

}