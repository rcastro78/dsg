package sv.com.seguridadcontrol.app.erp.autorizaciones

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_auth_ticket_detail.*
import kotlinx.android.synthetic.main.activity_orders2a.*
import kotlinx.android.synthetic.main.activity_orders3.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.adapter.SpinnerOrdersTicketAuthAdapter
import sv.com.seguridadcontrol.app.modelos.OrdenesTicket
import sv.com.seguridadcontrol.app.viewmodel.OrdersViewModel
import sv.com.seguridadcontrol.app.viewmodel.TicketsViewModel


class AuthTicketDetailActivity : AppCompatActivity() {
    private var sharedPreferences: SharedPreferences? = null
    private lateinit var ticketsViewModel: TicketsViewModel
    private lateinit var ordersViewModel: OrdersViewModel
    var ordersList: ArrayList<OrdenesTicket> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_ticket_detail)
        val SHARED:String=getString(R.string.sharedpref)
        sharedPreferences = getSharedPreferences(SHARED, 0)
        val userId: String? = sharedPreferences!!.getString("userId", "")
        val token:String? = sharedPreferences!!.getString("token","")
        val ticketId: String? = intent.getStringExtra("ticketId")
        val ordenSolicitud: String? = intent.getStringExtra("ordenSolicitud")
        val ticketTypeId: String? = intent.getStringExtra("ticketTypeId")
        val ticketCode: String? = intent.getStringExtra("ticketCode")
        val workId: String? = intent.getStringExtra("workId")
        ticketsViewModel= ViewModelProvider(this).get(TicketsViewModel::class.java)
        ordersViewModel = ViewModelProvider(this).get(OrdersViewModel::class.java)
        val tfb = Typeface.createFromAsset(assets,"fonts/Nunito-Bold.ttf")
        val tf = Typeface.createFromAsset(assets,"fonts/Nunito-Regular.ttf")
        val toolbar =
            findViewById<Toolbar>(R.id.tool_bar) // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar)
        val lblToolbar = toolbar.findViewById<TextView>(R.id.lblToolbar)
        lblToolbar.text = "Detalle del ticket a aprobar"
        lblToolbar.typeface = tfb
        lblInfo.typeface = tfb
        lblClie.typeface = tf
        lblCliente.typeface=tf
        lblCliente.text = intent.getStringExtra("ticketCliente")
        lblSvc.typeface = tf
        txtServicio.typeface=tf
        txtServicio.text = intent.getStringExtra("ticketServicio")
        lblSolic.typeface = tf
        lblSolicitud.typeface=tf
        lblSolicitud.text = intent.getStringExtra("ticketTipoSolicitud")

        txtTicketCod.text = ticketCode
        txtTicketCod.typeface = tf
        txtOrd.text = ordenSolicitud
        txtOrd.typeface = tf

        /*if(intent.getStringExtra("ticketPrioridad").equals("Alta")){
            imgPrioridad.setBackgroundResource(R.drawable.alta_prioridad)
        }
        if(intent.getStringExtra("ticketPrioridad").equals("Media")){
            imgPrioridad.setBackgroundResource(R.drawable.media_prioridad)
        }
        if(intent.getStringExtra("ticketPrioridad").equals("Baja")){
            imgPrioridad.setBackgroundResource(R.drawable.baja_prioridad)
        }*/
        //txtPrioridad.setTextColor(Color.parseColor(intent.getStringExtra("ticketPrioridadColor")))



        //getOrderList(ticketId!!,"orders_get_order_list",token!!)
        // getTickets("7","1","1","1","ticket_show_programming_ticket","302d6b3a2ecd683c26e1f731897271ca757aa48fd3d802c53ff3a681108ffd1f")

        btnAprobado.setOnClickListener {
            autorizar(userId!!,ordenSolicitud!!,workId!!,ticketId!!,ticketCode!!,"1",
                "A","authorize_approve_ticket",token!!)
            btnAprobado.visibility= View.INVISIBLE
        }
        btnDenegar.setOnClickListener {
            autorizar(userId!!,ordenSolicitud!!,ticketTypeId!!,ticketId!!,ticketCode!!,"1",
                "D","authorize_approve_ticket",token!!)
            btnDenegar.visibility=View.INVISIBLE
        }

        btnBack.setOnClickListener {
            onBackPressed()
        }


        //1daa71a4caa5d90165fc9cb4faab465b40d381ede147569dd7ba5a444b053fa8
/*
* {"login":[{"userID":"11","user":"eaguilar","ticket_type_id":"A",
* "id_user_type":"1","userType":"Administrador","user_name":"Ernesto",
* "user_lastname":"Aguilar","token":"1daa71a4caa5d90165fc9cb4faab465b40d381ede147569dd7ba5a444b053fa8"}]}
* */

    }



    fun autorizar(userId:String,
                  ordenSolicitud:String,
                  tipoOrdenId:String,
                  ticketId:String,
                  codigoTarea:String,
                  numSolicitud:String,
                  approvalOption:String,
                  task:String,
                  token:String){
        ticketsViewModel.approveTicketsResultObserver().observe(this) { result ->

            //if(result.color.equals("warning"))
            showDialog(result.message)
            //Toastie.warning(this@AuthTicketDetailActivity,"Warning Toast", Toast.LENGTH_LONG).show()


        }
        ticketsViewModel.approveTicket(userId, ordenSolicitud, tipoOrdenId, ticketId, codigoTarea,
            numSolicitud, approvalOption, task, token)
    }


    private fun showDialog(mensaje:String){
        val builder=AlertDialog.Builder(this@AuthTicketDetailActivity)
        builder.setMessage(mensaje)
            .setCancelable(false)
            .setPositiveButton("Aceptar") { dialog, id ->
                val intent = Intent(this@AuthTicketDetailActivity,AutorizacionesActivity::class.java)
                startActivity(intent)
                finish()
            }

        val alert: AlertDialog = builder.create()
        alert.setTitle("Autorizaciones")
        alert.show()
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