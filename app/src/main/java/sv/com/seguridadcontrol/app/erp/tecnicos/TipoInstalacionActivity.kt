package sv.com.seguridadcontrol.app.erp.tecnicos

import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_tipo_instalacion.*
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.viewmodel.OrderMaterialsViewModel
import sv.com.seguridadcontrol.app.viewmodel.OrderVerificationViewModel

class TipoInstalacionActivity : AppCompatActivity() {
    var tipo=""
    var instalacion=""
    var apagado=""
    var bateria=""
    var orderId=""
    var userId:String?=""
    var token:String?=""
    var orderNum:String = ""
    private var sharedPreferences: SharedPreferences? = null
    private lateinit var orderVerificationVieModel : OrderVerificationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipo_instalacion)
        val SHARED:String=getString(R.string.sharedpref)
        sharedPreferences = getSharedPreferences(SHARED, 0)
        orderId = intent.getStringExtra("orderId")!!
        orderNum = intent.getStringExtra("orderNum")!!

        userId = sharedPreferences!!.getString("userId","")
        token = sharedPreferences!!.getString("token","")
        val tfBold = Typeface.createFromAsset(assets,"fonts/Nunito-Bold.ttf")
        val tf = Typeface.createFromAsset(assets,"fonts/Nunito-Regular.ttf")
        val toolbar =
            findViewById<Toolbar>(R.id.tool_bar) // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar)
        val lblToolbar = toolbar.findViewById<TextView>(R.id.lblToolbar)
        lblToolbar.text = "Tipo de Instalación"
        lblToolbar.typeface = tfBold
        rbVH.typeface = tf
        rbFG.typeface = tf
        rbT1.typeface = tf
        rbT2.typeface = tf
        rbAR.typeface = tf
        rbSAR.typeface = tf
        rbBI.typeface = tf
        rbSBI.typeface = tf
        lblTipoInstal.typeface = tfBold
        lblInstal.typeface = tfBold
        lblApagado.typeface = tfBold
        lblBatt.typeface = tfBold


        orderVerificationVieModel  =
            ViewModelProvider(this).get(OrderVerificationViewModel::class.java)


        //Tipos
        rbVH.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                tipo="VH"
            }
        }
        rbFG.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                tipo="FG"
            }
        }
        //Instalacion
        rbT1.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                instalacion="T1"
            }
        }
        rbT2.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                instalacion="T2"
            }
        }
        //Apagado
        rbAR.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                apagado="AR"
            }
        }
        rbSAR.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                apagado="SAR"
            }
        }

        //Bateria
        rbBI.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                bateria="BI"
            }
        }
        rbSBI.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                bateria="SBI"

            }
        }

        getOrderVerificationDetails(orderId,userId!!,"orders_progress_verification",token!!)


        btnGuardaInstall.setOnClickListener {
            guardarInstalacion(orderId,tipo,instalacion,apagado,bateria,"orders_stored_facilities",token!!)
        }



    }


    fun guardarInstalacion(order_id:String,type:String,facility:String,
                           turn:String,battery:String,task:String,token:String){
        orderVerificationVieModel.storeFacilitiesObserver().observe(this) { storeResult ->
            Toast.makeText(applicationContext, storeResult, Toast.LENGTH_LONG).show()
            val intent = Intent(this@TipoInstalacionActivity, OrdenTrabajoActivity::class.java)

            intent.putExtra("orderId", orderId)
            intent.putExtra("orderNum", orderNum)
            startActivity(intent)
        }
        orderVerificationVieModel.storeOrderFacilities(order_id, type, facility, turn, battery, task, token)
    }



    fun getOrderVerificationDetails(order_id:String,user_id:String,task:String,token:String){
        orderVerificationVieModel.getOrderVerificationObserver().observe(this) { st ->
            if (st != null) {
                val fac = st.status[0].facility
                if (fac.contains("|")) {
                    var div = fac.split("|")
                    for (i in 0 until div.count()) {
                        Log.d("ORDENES", div[i])
                        if (div[i].equals("VH")) {
                            rbVH.isChecked = true
                        }
                        if (div[i].equals("FG")) {
                            rbFG.isChecked = true
                        }
                        if (div[i].equals("T1")) {
                            rbT1.isChecked = true
                        }
                        if (div[i].equals("T2")) {
                            rbT2.isChecked = true
                        }
                        if (div[i].equals("SAR")) {
                            rbSAR.isChecked = true
                        }
                        if (div[i].equals("AR")) {
                            rbAR.isChecked = true
                        }
                        if (div[i].equals("SBI")) {
                            rbSBI.isChecked = true
                        }
                        if (div[i].equals("BI")) {
                            rbBI.isChecked = true
                        }
                    }


                }
            }
        }
        orderVerificationVieModel.getOrderVerificationDetail(order_id,user_id, task, token)
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