package sv.com.seguridadcontrol.app.erp.tecnicos

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.rm.freedrawview.FreeDrawView
import kotlinx.android.synthetic.main.activity_orden_firma.*
import kotlinx.android.synthetic.main.activity_orden_ubicacion.*
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.viewmodel.OrderClientViewModel

class OrdenFirmaActivity : AppCompatActivity() {
    private lateinit var orderClienteViewModel : OrderClientViewModel
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orden_firma)
        val SHARED:String=getString(R.string.sharedpref)
        sharedPreferences = getSharedPreferences(SHARED, 0)
        val token:String? = sharedPreferences!!.getString("token","")
        val orderId:String? = intent.getStringExtra("orderId")
        orderClienteViewModel = ViewModelProvider(this).get(OrderClientViewModel::class.java)
        val tfBold = Typeface.createFromAsset(assets,"fonts/Nunito-Bold.ttf")
        val tf = Typeface.createFromAsset(assets,"fonts/Nunito-Regular.ttf")
        val toolbar =
            findViewById<Toolbar>(R.id.tool_bar) // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar)
        val lblToolbar = toolbar.findViewById<TextView>(R.id.lblToolbar)
        lblToolbar.text = "Firma del cliente"
        lblToolbar.typeface = tfBold
        txtClienteNombre.typeface=tf
        btnGuardaFirma.typeface=tf
        imbDeshacer2.setOnClickListener{
            fdvFirma.undoLast()
        }
        imbRehacer2.setOnClickListener{
            fdvFirma.redoLast()
        }
        btnGuardaFirma.setOnClickListener {
            fdvFirma.getDrawScreenshot(object : FreeDrawView.DrawCreatorListener {
                override fun onDrawCreated(draw: Bitmap?) {
                    var bitmap: Bitmap? = draw

                }

                override fun onDrawCreationError() {
                    TODO("Not yet implemented")
                }

            })
            Toast.makeText(applicationContext,"Guardando, espere por favor...",Toast.LENGTH_LONG).show()
            val nombreCliente = txtClienteNombre.text.toString()
            if(nombreCliente.isNotEmpty()){
                storeClientName(orderId!!,nombreCliente,"orders_client_name_store",token!!)
            }else{
                Toast.makeText(applicationContext,"El nombre del cliente no puede ir vacío",Toast.LENGTH_LONG).show()
            }

        }
    }


    fun storeClientName(order_id:String,client_name:String,task:String,token:String){
        orderClienteViewModel.storeClientNameObserver().observe(this,{result->
            if(result.contains("Exito")){
                Toast.makeText(applicationContext,"Firma guardada correctamente",Toast.LENGTH_LONG).show()
                val intent = Intent(this@OrdenFirmaActivity, OrdenTrabajoActivity::class.java)
                intent.putExtra("orderId",order_id)
                startActivity(intent)

            }

        })
        orderClienteViewModel.storeClienName(order_id,client_name,task,token)
    }

}