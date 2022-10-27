package sv.com.seguridadcontrol.app.erp.tecnicos

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_orders.*
import kotlinx.android.synthetic.main.activity_orders3.*
import kotlinx.android.synthetic.main.activity_prov_materiales.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.adapter.OrdersAdapter
import sv.com.seguridadcontrol.app.modelos.*
import sv.com.seguridadcontrol.app.viewmodel.OrdersViewModel
import sv.com.seguridadcontrol.app.viewmodel.TicketDetailsViewModel
import sv.com.seguridadcontrol.app.viewmodel.TicketProvisioningViewModel
import java.text.SimpleDateFormat
import java.util.*

class OrdersActivity : AppCompatActivity() {

    private lateinit var ordersViewModel: OrdersViewModel
    private lateinit var ticketsProvViewModel: TicketProvisioningViewModel
    private lateinit var ticketDetailsViewModel:TicketDetailsViewModel
    private lateinit var adapter: OrdersAdapter
    private var sharedPreferences: SharedPreferences? = null
    var totalOrdersNoCompletadas:Int=0
    var ordersList:ArrayList<Orden> = ArrayList()

    override fun onPause() {
        super.onPause()
        finish()
    }


    override fun onStop() {
        super.onStop()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders3)
        val ticketId = intent.getStringExtra("ticketId")
        val ticketCod = intent.getStringExtra("ticketCod")
        val ticketColor = intent.getStringExtra("ticketColor")
        val tf = Typeface.createFromAsset(assets,"fonts/Nunito-Bold.ttf")

        val SHARED:String=getString(R.string.sharedpref)
        sharedPreferences = getSharedPreferences(SHARED, 0)
        val userId: String? = sharedPreferences!!.getString("userId", "")
        val token:String? = sharedPreferences!!.getString("token","")
        val editor : SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString("ticketId",ticketId)
        editor.putString("ticketCod",ticketCod)
        editor.putString("ticketColor",ticketColor)
        editor.apply()


        val toolbar =
            findViewById<Toolbar>(R.id.tool_bar) // Attaching the layout to the toolbar object

        setSupportActionBar(toolbar)
        val lblToolbar = toolbar.findViewById<TextView>(R.id.lblToolbar)
        lblToolbar.text = "Ordenes para el ticket:  ${ticketId}"
        lblToolbar.typeface = tf

        rlCerrarTicket.setOnClickListener {
            if(totalOrdersNoCompletadas==0){
                val c = Calendar.getInstance().time
                val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val fecha = df.format(c)
                ticketCompleted(ticketId!!,userId!!,"","ticket_completed_offline",token!!,fecha)
            }else{
                Toast.makeText(applicationContext,"Todavía no puedes cerrar el ticket",Toast.LENGTH_LONG).show()
            }
        }

        imgDocument.setOnClickListener{

            val bottomSheet1 = BottomSheetDialog(this@OrdersActivity)
            bottomSheet1.setContentView(R.layout.activity_orders)

            ticketsProvViewModel = ViewModelProvider(this).get(TicketProvisioningViewModel::class.java)
            ticketDetailsViewModel = ViewModelProvider(this).get(TicketDetailsViewModel::class.java)
            getTicketDetails(bottomSheet1, ticketId!!,"ticket_show_ticket_details",token!!)

            bottomSheet1.show()

        }


        /*txtTicketCliente.typeface = tf
        txtTicketTrabajo.typeface = tf
        txtTicketSvc.typeface = tf
        txtTicketReq.typeface = tf
        txtTicketCompletado.typeface = tf

        txtTicketFecha.typeface = tf
        txtTicketHora.typeface = tf
        txtTicketDireccion.typeface = tf

        txtProgreso.typeface = tf
        txtHoraInicio.typeface = tf
        txtHoraFin.typeface = tf*/


        ordersViewModel = ViewModelProvider(this).get(OrdersViewModel::class.java)
        /*ticketsProvViewModel = ViewModelProvider(this).get(TicketProvisioningViewModel::class.java)
        ticketDetailsViewModel = ViewModelProvider(this).get(TicketDetailsViewModel::class.java)

        getTicketDetails(ticketId!!,"ticket_show_ticket_details",token!!)*/
        Log.d("ORDENES",ticketId!!)
        Log.d("ORDENES",token!!)
        getOrderList(ticketId!!,"orders_get_order_list",token!!)

    }


    fun getTicketDetails(bottomSheet1:BottomSheetDialog, ticket_id: String,task: String,token: String){
        val tf = Typeface.createFromAsset(assets,"fonts/Nunito-Bold.ttf")
        val txtTicketCliente: TextView? = bottomSheet1.findViewById(R.id.txtTicketCliente)
        val txtTicketTrabajo: TextView? = bottomSheet1.findViewById(R.id.txtTicketTrabajo)
        val txtTicketSvc: TextView? = bottomSheet1.findViewById(R.id.txtTicketSvc)
        val txtTicketCompletado: TextView? = bottomSheet1.findViewById(R.id.txtTicketCompletado)
        val txtTicketFecha: TextView? = bottomSheet1.findViewById(R.id.txtTicketFecha)
        val txtTicketHora: TextView? = bottomSheet1.findViewById(R.id.txtTicketHora)
        val txtTicketDireccion: TextView? = bottomSheet1.findViewById(R.id.txtTicketDireccion)
        val txtProgreso: TextView? = bottomSheet1.findViewById(R.id.txtProgreso)
        val txtHoraInicio: TextView? = bottomSheet1.findViewById(R.id.txtHoraInicio)
        val txtHoraFin: TextView? = bottomSheet1.findViewById(R.id.txtHoraFin)
        txtTicketCliente?.typeface = tf
        txtTicketTrabajo?.typeface = tf
        txtTicketSvc?.typeface = tf
        txtTicketReq?.typeface = tf
        txtTicketCompletado?.typeface = tf

        txtTicketFecha?.typeface = tf
        txtTicketHora?.typeface = tf
        txtTicketDireccion?.typeface = tf

        txtProgreso?.typeface = tf
        txtHoraInicio?.typeface = tf
        txtHoraFin?.typeface = tf





        ticketDetailsViewModel.getTicketDetailsObserver().observe(this) { details ->
            txtTicketCliente?.text = details.ticket[0].client_reason
            txtTicketTrabajo?.text = details.ticket[0].ticket_type
            txtTicketSvc?.text = details.ticket[0].service
            if(details.ticket[0].order_required.equals("1"))
                txtTicketReq?.text = "Sí"
            else
                txtTicketReq?.text = "No"
            if(details.ticket[0].order_completed.equals("1"))
                txtTicketCompletado?.text = "Sí"
            else
                txtTicketCompletado?.text = "No"


            val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val fmtDia = SimpleDateFormat("dd/MM/yyyy")
            val fmtHora = SimpleDateFormat("HH:mm")
            //val fechaProgramada:String = fmtDia.format(parser.parse(details.ticket[0].programming_date))
            //val horaProgramada:String = fmtHora.format(parser.parse(details.ticket[0].programming_date))
            txtTicketFecha?.text = details.ticket[0].programming_date.split(" ")[0]
            txtTicketHora?.text = details.ticket[0].programming_date.split(" ")[1]

            txtTicketDireccion?.text = details.ticket[0].ticket_address_all

            txtProgreso?.text = details.ticket[0].ticket_progress_complete
            txtHoraInicio?.text = details.ticket[0].ticket_start_date_formatted
            txtHoraFin?.text = details.ticket[0].ticket_end_date_formatted


        }


        ticketDetailsViewModel.getTicketDetails(ticket_id, task, token)
    }



    fun ticketCompleted(ticketId:String,user_id:String,comment:String,task:String,token: String,date:String){
        ordersViewModel.ticketCompletedObserver().observe(this){ticket->
            if (ticket.contains("Exito")){
                Toast.makeText(applicationContext,"Ticket cerrado exitosamente",Toast.LENGTH_LONG).show()
                val intent = Intent(
                    this@OrdersActivity,
                    ERPPrincipalActivity::class.java
                )
                startActivity(intent)
            }
        }
        ordersViewModel.ticketCompleted(ticketId, user_id, comment, task, token,date)
    }


    fun getOrderList(ticket_id:String,task:String,token: String){

        ordersViewModel.getOrdersResultObserver().observe(this) { orders ->

            if(orders.order.isNotEmpty()){
                orders.order.forEach { o ->

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

                    val orden = Orden(
                        orderNum,
                        order_id,
                        o.progress,
                        order_date,
                        order_start,
                        order_end,
                        order_color
                    )
                    ordersList.add(orden)

                    if(o.progress.equals("O")){
                        totalOrdersNoCompletadas++
                    }

                }

                adapter = OrdersAdapter(ordersList)
                rvOrders.layoutManager = LinearLayoutManager(this)
                CoroutineScope(Dispatchers.Main).launch {
                    rvOrders.adapter = adapter
                }
            }else{

            }


        }
        ordersViewModel.getOrdenes(ticket_id, task, token)
    }




    fun getTicketsProv(user_id:String,task:String,token: String){
        var ticketProv:ArrayList<TicketProvisioning> = ArrayList()
        ticketsProvViewModel.getTicketProvisioningResultObserver().observe(this) { tickets ->
            tickets.material?.forEach { ticket ->
                ticket.product?.let { Log.d("TICKET", it) }
            }
        }

        ticketsProvViewModel.getTicketProvisioning(user_id, task, token)

    }


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