package sv.com.seguridadcontrol.app

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_menu_principal.*
import kotlinx.android.synthetic.main.activity_orders3.*
import kotlinx.android.synthetic.main.activity_tickets.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sv.com.seguridadcontrol.app.adapter.OrdersAdapter
import sv.com.seguridadcontrol.app.adapter.TicketsAdapter
import sv.com.seguridadcontrol.app.dao.AppDatabase
import sv.com.seguridadcontrol.app.dao.OrdenesDAO
import sv.com.seguridadcontrol.app.erp.autorizaciones.AutorizacionesActivity
import sv.com.seguridadcontrol.app.erp.clientes.MenuClientesActivity
import sv.com.seguridadcontrol.app.erp.cobros.RegistrarCobroActivity
import sv.com.seguridadcontrol.app.erp.tecnicos.ERPPrincipalActivity
import sv.com.seguridadcontrol.app.modelos.Orden
import sv.com.seguridadcontrol.app.modelos.TicketData
import sv.com.seguridadcontrol.app.viewmodel.OrdersViewModel
import sv.com.seguridadcontrol.app.viewmodel.TicketsViewModel
import java.text.SimpleDateFormat
import java.util.*


class MenuPrincipalActivity: AppCompatActivity() {
    private var sharedPreferences: SharedPreferences? = null
    private lateinit var ticketsViewModel: TicketsViewModel
    private lateinit var ordersViewModel: OrdersViewModel
    var ticketsList:ArrayList<TicketData> = ArrayList()
    var tickets:ArrayList<TicketData> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)
        val SHARED:String=getString(R.string.sharedpref)
        ordersViewModel = ViewModelProvider(this).get(OrdersViewModel::class.java)
        ticketsViewModel = ViewModelProvider(this).get(TicketsViewModel::class.java)
        sharedPreferences = getSharedPreferences(SHARED, 0)
        val token:String? = sharedPreferences!!.getString("token","")
        val userId:String? = sharedPreferences!!.getString("userId","")


        //val tickets = getTickets(userId!!,"1","1","1","ticket_show_start_tickets",token!!)

        val idUserType :String? = sharedPreferences!!.getString("userId","")
        if(idUserType=="5") {
            val tickets = getTickets(userId!!, "1", "1", "1", "ticket_show_start_tickets", token!!)
        }

        imbERP.setOnClickListener {
            val intent = Intent(this@MenuPrincipalActivity,ERPPrincipalActivity::class.java)
            startActivity(intent)
        }

        imbClientes.setOnClickListener {
            val intent = Intent(this@MenuPrincipalActivity,MenuClientesActivity::class.java)
            startActivity(intent)
        }


        imbAutorizaciones.setOnClickListener {
            val intent = Intent(this@MenuPrincipalActivity,AutorizacionesActivity::class.java)
            startActivity(intent)
        }

        imbCobros.setOnClickListener {
            val intent = Intent(this@MenuPrincipalActivity,RegistrarCobroActivity::class.java)
            startActivity(intent)
        }

        imbDownload.setOnClickListener{
            Toast.makeText(applicationContext,"Descargando tickets para trabajo offline",Toast.LENGTH_LONG).show()

            val db = AppDatabase.getInstance(application)
            CoroutineScope(Dispatchers.IO).launch {
                db.iTicketDAO.delete()
                db.iOrdenesDAO.delete()
            }

            var i=0

            tickets.forEach { ticket->
                CoroutineScope(Dispatchers.IO).launch {
                    db.iTicketDAO.insert(ticket)

                }
                CoroutineScope(Dispatchers.IO).launch {
                    val id = ticket.ticket_id
                    getOrderList(id,"orders_get_order_list",token!!)
                }

            }

            //tickets.forEach { ticket->

            //}


            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(applicationContext,"Tickets descargados: $i",Toast.LENGTH_LONG).show()
            }
        }


        imbLogout.setOnClickListener {
            val intent = Intent(this@MenuPrincipalActivity,MainActivity::class.java)
            var editor: SharedPreferences.Editor = sharedPreferences!!.edit()
            editor.putString("userId", "")
            editor.putString("userPwd", "")
            editor.putString("token", "")
            editor.putString("userName", "")
            editor.putString("userLastName", "")
            editor.putString("ticketTypeId", "")
            editor.putString("userType", "")
            editor.putString("idUserType", "")
            editor.putInt("auth", 0)
            editor.apply()
            startActivity(intent)
            finish()
        }

    }

    fun getOrderList(ticket_id:String,task:String,token: String){
        val db = AppDatabase.getInstance(application)

        ordersViewModel.getOrdersResultObserver().observe(this) { orders ->


                if (orders.order.isNotEmpty()) {
                    orders.order.forEach { o ->
                        try {
                            var orderNum: String
                            try {
                                orderNum = o.order_num
                            } catch (e: Exception) {
                                orderNum = ""
                            }

                            var order_id: String
                            try {
                                order_id = o.order_id
                            } catch (e: Exception) {
                                order_id = ""
                            }

                            var order_date: String
                            try {
                                order_date = o.order_date
                            } catch (e: Exception) {
                                order_date = ""
                            }

                            var order_start: String
                            try {
                                order_start = o.order_start
                            } catch (e: Exception) {
                                order_start = ""
                            }
                            var order_end: String
                            try {
                                order_end = o.order_end
                            } catch (e: Exception) {
                                order_end = ""
                            }
                            var order_color: String
                            try {
                                order_color = o.order_color
                            } catch (e: Exception) {
                                order_color = ""
                            }

                            val orden = OrdenesDAO(
                                0,
                                order_id,
                                ticket_id,
                                o.progress,
                                order_date,
                                order_start,
                                order_end,
                                order_color
                            )
                            CoroutineScope(Dispatchers.IO).launch {
                                db.iOrdenesDAO.insert(orden)
                            }
                        }catch (e:Exception){}

                    }


                } else {

                }



        }
        ordersViewModel.getOrdenes(ticket_id, task, token)
    }

    fun getTickets(userId:String,type:String,start:String,quantity:String,task:String,token:String):ArrayList<TicketData>{

        ticketsList.clear()


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
                    ticketsList.add(ticket)


                }





            } else {
                Toast.makeText(
                    applicationContext,
                    "No tienes tickets abiertos",
                    Toast.LENGTH_LONG
                ).show()
            }





        }

        ticketsViewModel.getTickets(userId,type,start, quantity, task, token)
        return ticketsList

    }

    //Obtener los tickets de hoy
   /*
   * CoroutineScope(Dispatchers.IO).launch {
                            db.iTicketDAO.insert(ticket)
                        }
   * */
}