package sv.com.seguridadcontrol.app.erp.tecnicos

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_orden_trabajo.*
import kotlinx.android.synthetic.main.activity_tipo_instalacion.*
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.viewmodel.OrderVerificationViewModel
import java.text.SimpleDateFormat
import java.util.*

class OrdenTrabajoActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null
    private lateinit var orderVerificationVieModel : OrderVerificationViewModel
    var hasInstall=false
    var hasImage=false
    var hasSignature=false
    var hasMaterials=false
    var ubicacion = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orden_trabajo)

        val toolbar =
            findViewById<Toolbar>(R.id.tool_bar) // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar)
        val lblToolbar = toolbar.findViewById<TextView>(R.id.lblToolbar)
        lblToolbar.text = "Detalle de trabajo de la orden"
        orderVerificationVieModel  =
            ViewModelProvider(this).get(OrderVerificationViewModel::class.java)

        val SHARED:String=getString(R.string.sharedpref)
        sharedPreferences = getSharedPreferences(SHARED, 0)
        ubicacion = sharedPreferences!!.getInt("ubicacion",0);
        val ticketCod : String? = sharedPreferences!!.getString("ticketCod","")
        val userId : String? = sharedPreferences!!.getString("userId","")
        val token : String? = sharedPreferences!!.getString("token","")
        val ticketColor : String? = sharedPreferences!!.getString("ticketColor","")
        val orderNum = intent.getStringExtra("orderNum")!!
        val orderId  = intent.getStringExtra("orderId")!!
        val tf = Typeface.createFromAsset(assets,"fonts/Nunito-Bold.ttf")
        lblTicket.text="Ticket "+ticketCod
        lblOrder.text="Orden No. "+orderNum+" -- "+"Orden Id: "+orderId
        lblTicket.typeface = tf
        lblOrder.typeface = tf
        lblCheckList.typeface = tf
        lblUbica.typeface = tf
        lblMateriales.typeface = tf
        lblFirma.typeface = tf
        lblTicket.setBackgroundColor(Color.parseColor(ticketColor))

        getOrderVerificationDetails(orderId,userId!!,"orders_progress_verification",token!!)

        imbMateriales.setOnClickListener{
            val intent = Intent(this@OrdenTrabajoActivity, OrdenMaterialesActivity::class.java)
            intent.putExtra("orderNum",orderNum)
            intent.putExtra("orderId",orderId)
            startActivity(intent)
        }

        imbFirma.setOnClickListener{
            val intent = Intent(this@OrdenTrabajoActivity, OrdenFirmaActivity::class.java)
            intent.putExtra("orderId",orderId)
            startActivity(intent)
        }

        imbUbica.setOnClickListener{
            val intent = Intent(this@OrdenTrabajoActivity, OrdenUbicacionActivity::class.java)
            intent.putExtra("orderId",orderId)
            intent.putExtra("orderNum",orderNum)
            startActivity(intent)
        }
        imbCheckList.setOnClickListener{
            val intent = Intent(this@OrdenTrabajoActivity, TipoInstalacionActivity::class.java)
            intent.putExtra("orderId",orderId)
            startActivity(intent)
        }


        btnFinalizarOrden.setOnClickListener {
            if(hasInstall && hasImage && hasSignature && hasMaterials){
                //Enviar comentario via viewModel
                showDialog(this,orderId,userId,token)
            }else{
                Toast.makeText(applicationContext,"Para finalizar la orden debes tener los 4 estados en verde",Toast.LENGTH_LONG).show()
            }
        }



    }

    fun showDialog(activity: Activity,order_id: String,user_id: String,token: String){
        val dialog = Dialog(activity)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_comment)
        val txtComentario:TextView? = dialog.findViewById(R.id.txtComentario)
        val btnCommentOK = dialog.findViewById(R.id.btnCommentOK) as Button
        btnCommentOK.setOnClickListener {
            //Invocar al view model para enviar el comentario
            val comentario:String = txtComentario!!.text.toString()
            orderVerificationVieModel.storeFacilitiesObserver().observe(this,{result->
                if(result.equals("Exito")){
                    Toast.makeText(applicationContext,"Comentario enviado",Toast.LENGTH_LONG).show()
                }
            })
            val c = Calendar.getInstance().time
            val df = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
            val fecha = df.format(c)
            orderVerificationVieModel.storeComment(order_id,user_id,comentario,fecha,"orders_stored_comment_offline",token)

        }
        val btnCommentCancel = dialog.findViewById(R.id.btnCommentCancel) as Button
        btnCommentCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }



    fun getOrderVerificationDetails(order_id:String,user_id:String,task:String,token:String){
        orderVerificationVieModel.getOrderVerificationObserver().observe(this,{st->

            val fac = st.status[0].facility
            if(fac.contains("|")){
                var div = fac.split("|")
                if(div.count()==4){
                    rlInstalacion.setBackgroundColor(Color.parseColor("#4EA158"))
                    hasInstall = true
                }
            }

           hasImage = st.status[0].image
            if(hasImage){
                rlUbicacion.setBackgroundColor(Color.parseColor("#4EA158"))
            }


            hasSignature = st.status[0].image_signature
            if(hasSignature){
                rlFirma.setBackgroundColor(Color.parseColor("#4EA158"))
            }
            hasMaterials = st.status[0].materials_quantity
            if(hasMaterials){
                rlMateriales.setBackgroundColor(Color.parseColor("#4EA158"))
            }

            //btnFinalizarOrden.isEnabled = hasInstall && hasImage && hasSignature && hasMaterials



        })
        orderVerificationVieModel.getOrderVerificationDetail(order_id,user_id, task, token)
    }



}