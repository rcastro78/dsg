package sv.com.seguridadcontrol.app.erp.clientes

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_crear_cliente_pr2.*
import kotlinx.android.synthetic.main.activity_crear_cliente_pr3.*
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.viewmodel.ProspectClientViewModel

class CrearClientePr3Activity : AppCompatActivity() {
    private var sharedPreferences: SharedPreferences? = null
    var nombreCliePro:String=""
    var apeCliePro:String=""
    var nombreComercial:String=""
    var giro:String=""
    var pais:String=""
    var paisN:String=""
    var dept:String=""
    var deptN:String=""
    var adviser:String=""
    var telefonoProspecto:String=""
    var celularProspecto:String=""
    var emailProspecto:String=""
    var reaccion:Boolean = false
    var equipo:Boolean = false
    var revs:Boolean = false
    var firmaContrato:Boolean = false
    var noIva:Boolean = false

    var quiereGPS:Boolean=false
    var quiereAlarma:Boolean=false
    var quiereCamara:Boolean=false
    var quiereVigilancia:Boolean=false
    var quiereOtrosSvc:Boolean=false
    var dataCliente:String=""
    var dataGPS:String=""
    var dataAlarma:String=""
    var dataCamara:String=""
    var dataVig:String=""
    var dataOtrosSvc:String=""
    var dataTerms:String=""
    private lateinit var prospectClientViewModel: ProspectClientViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cliente_pr3)
        prospectClientViewModel = ViewModelProvider(this).get(ProspectClientViewModel::class.java)
        val tf = Typeface.createFromAsset(assets,"fonts/Nunito-Bold.ttf")
        val toolbar =
            findViewById<Toolbar>(R.id.tool_bar) // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar)
        val SHARED:String=getString(R.string.sharedpref)
        sharedPreferences = getSharedPreferences(SHARED, 0)
        val userId: String? = sharedPreferences!!.getString("userId", "")
        val token:String? = sharedPreferences!!.getString("token","")
        quiereGPS = intent.getBooleanExtra("quiereGPS",false)
        quiereAlarma = intent.getBooleanExtra("quiereAlarma",false)
        quiereCamara = intent.getBooleanExtra("quiereCamara",false)
        quiereVigilancia = intent.getBooleanExtra("quiereVigilancia",false)
        quiereOtrosSvc = intent.getBooleanExtra("quiereOtrosSvc",false)
        val lblToolbar = toolbar.findViewById<TextView>(R.id.lblToolbar)
        lblToolbar.text = "Crear cliente prospecto (3)"
        lblToolbar.typeface = tf
        nombreCliePro = sharedPreferences!!.getString("nombreCliePro","").toString()
        apeCliePro = sharedPreferences!!.getString("apeCliePro","").toString()
        nombreComercial = sharedPreferences!!.getString("nombreComercial","").toString()
        giro = sharedPreferences!!.getString("giro","").toString()
        pais = sharedPreferences!!.getString("pais","").toString()
        paisN = sharedPreferences!!.getString("paisn","").toString()
        dept = sharedPreferences!!.getString("department","").toString()
        deptN = sharedPreferences!!.getString("departmentn","").toString()
        adviser = sharedPreferences!!.getString("adviser","").toString()
        telefonoProspecto = sharedPreferences!!.getString("telefonoProspecto","").toString()
        celularProspecto = sharedPreferences!!.getString("celularProspecto","").toString()
        emailProspecto = sharedPreferences!!.getString("emailProspecto","").toString()

/**
 *  2022-08-26 23:59:54.722 31355-31355/sv.com.seguridadcontrol.app D/QUIERE_DATA: {"nombre_prospecto":Rafael ","apellido_prospecto":Castro ","nombre_comercial":"cels","giro_comercial":"teléfono ","id_pais":"3","id_departamento":"38","id_vendedor_asignado":"10","pais":"El Salvador","departamento":"San Salvador","telefono_prospecto":"7127","celular_prospecto":"6577","email_prospecto":"rcastroluna.sv@gmail.com"}
2022-08-26 23:59:54.722 31355-31355/sv.com.seguridadcontrol.app D/QUIERE_GPS: {"id_servicio":1,"cantidad":10,"preciosa":5,"descripcion":""}
2022-08-26 23:59:54.722 31355-31355/sv.com.seguridadcontrol.app D/QUIERE_CAM: {"id_servicio":3,"cantidad":2,"preciosa":10,"descripcion":""}
2022-08-26 23:59:54.722 31355-31355/sv.com.seguridadcontrol.app D/QUIERE_ALM: {"id_servicio":2,"cantidad":3,"preciosa":15,"descripcion":""}
2022-08-26 23:59:54.723 31355-31355/sv.com.seguridadcontrol.app D/QUIERE_VIG: {"id_servicio":4,"cantidad":4,"preciosa":20,"descripcion":""}
2022-08-26 23:59:54.723 31355-31355/sv.com.seguridadcontrol.app D/QUIERE_OTRS: {"id_servicio":5,"cantidad":5,"preciosa":25,"descripcion":""}
2022-08-26 23:59:54.723 31355-31355/sv.com.seguridadcontrol.app D/QUIERE_TMS: {"term_1":"1","term_2":"2","term_3":"3","term_4":"4","term_5":"5"}
2022-08-26 23:59:54.980 31355-31355/sv.com.seguridadcontrol.app D/QUIERE_PROSPECTO: Formato de email incorrecto
 * */


        rlFinalizar.setOnClickListener{
            reaccion = chkReaccion.isChecked
            equipo = chkEquipo.isChecked
            revs = chkRevs.isChecked
            firmaContrato = chkFirmaContrato.isChecked
            noIva = chkNoIVA.isChecked


            dataCliente = "{" +
                    "\"nombre_prospecto\":\""+nombreCliePro+"\"," +
                    "\"apellido_prospecto\":\"" +apeCliePro+"\"," +
                    "\"nombre_comercial\":\"" +nombreComercial+"\"," +
                    "\"giro_comercial\":\"" +giro+"\"," +
                    "\"id_pais\":\"" +pais+"\"," +
                    "\"id_departamento\":\"" +dept+"\"," +
                    "\"id_vendedor_asignado\":\"" +adviser+"\"," +
                    "\"pais\":\"" +paisN+"\"," +
                    "\"departamento\":\"" +deptN+"\"," +
                    "\"telefono_prospecto\":\"" +telefonoProspecto+"\"," +
                    "\"celular_prospecto\":\"" +celularProspecto+"\"," +
                    "\"comentario\":\"" +""+"\","+
                    "\"email_prospecto\":\"" +emailProspecto+"\"}"

            if(quiereGPS){
                dataGPS="{" +
                        "\"id_servicio\":" +
                        "1," +
                        "\"cantidad\":"+intent.getIntExtra("cantidadGPS",0)+"," +
                        "\"preciosa\":\"" +
                        ""+intent.getStringExtra("precioGPS")+"\"," +
                        "\"descripcion\":\"" +
                        ""+intent.getStringExtra("descripGPS")+"\"}"
            }


            if(quiereAlarma){
                dataAlarma="{" +
                        "\"id_servicio\":" +
                        "2," +
                        "\"cantidad\":"+intent.getIntExtra("cantidadAlm",0)+"," +
                        "\"preciosa\":\"" +
                        ""+intent.getStringExtra("precioAlm")+"\"," +
                        "\"descripcion\":\"" +
                        ""+intent.getStringExtra("descripAlm")+"\"}"
            }

            if(quiereCamara){
                dataCamara="{" +
                        "\"id_servicio\":" +
                        "3," +
                        "\"cantidad\":"+intent.getIntExtra("cantidadCam",0)+"," +
                        "\"preciosa\":\"" +
                        ""+intent.getStringExtra("precioCam")+"\"," +
                        "\"descripcion\":\"" +
                        ""+intent.getStringExtra("descripCam")+"\"}"
            }

            if(quiereVigilancia){
                dataVig="{" +
                        "\"id_servicio\":" +
                        "4," +
                        "\"cantidad\":"+intent.getIntExtra("cantidadVig",0)+"," +
                        "\"preciosa\":\"" +
                        ""+intent.getStringExtra("precioVig")+"\"," +
                        "\"descripcion\":\"" +
                        ""+intent.getStringExtra("descripVig")+"\"}"
            }

            if(quiereOtrosSvc){
                dataOtrosSvc="{" +
                        "\"id_servicio\":" +
                        "5," +
                        "\"cantidad\":"+intent.getIntExtra("cantidadOtros",0)+"," +
                        "\"preciosa\":\"" +
                        ""+intent.getStringExtra("precioOtros")+"\"," +
                        "\"descripcion\":\"" +
                        ""+intent.getStringExtra("descripOtros")+"\"}"
            }


            dataTerms="{"
            if(chkReaccion.isChecked){
                dataTerms+="\"term_1\":\"1\","
            }
            if(chkEquipo.isChecked){
                dataTerms+="\"term_2\":\"2\","
            }
            if(chkRevs.isChecked){
                dataTerms+="\"term_3\":\"3\","
            }
            if(chkFirmaContrato.isChecked){
                dataTerms+="\"term_4\":\"4\","
            }
            if(chkNoIVA.isChecked){
                dataTerms+="\"term_5\":\"5\","
            }

            dataTerms+="}"

            dataTerms = dataTerms.replace(",}","}")
            Log.d("QUIERE_DATA",dataCliente)
            Log.d("QUIERE_GPS",dataGPS)
            Log.d("QUIERE_CAM",dataCamara)
            Log.d("QUIERE_ALM",dataAlarma)
            Log.d("QUIERE_VIG",dataVig)
            Log.d("QUIERE_OTRS",dataOtrosSvc)
            Log.d("QUIERE_TMS",dataTerms)

            crearClienteProspecto(userId!!,"prospect_create",
                "1daa71a4caa5d90165fc9cb4faab465b40d381ede147569dd7ba5a444b053fa8",dataCliente,
                dataGPS,dataCamara,dataAlarma,
            dataVig,dataOtrosSvc,dataTerms)


        }

    }


    fun crearClienteProspecto(userId:String,
                             task:String,
                             token:String,
                             data:String,
                             dataGps:String,
                             dataCam:String,
                             dataAlm:String,
                             dataVig:String,
                             dataOtrs:String,
                             dataTerms:String){

        prospectClientViewModel.createProspectClientObserver().observe(this) { result ->
            //Log.d("QUIERE_PROSPECTO",result.message)
            if(result.message == "Exito"){
                showDialog("Se ha creado el cliente prospecto con código ${result.prospecto_id}")
            }else{
                showDialog("Ha ocurrido un error al crear el cliente prospecto")
            }

        }


        prospectClientViewModel.createProspectClient(userId, task, token, data, dataGps, dataCam,
            dataAlm, dataVig, dataOtrs, dataTerms)

    }


    private fun showDialog(mensaje:String){
        val builder= AlertDialog.Builder(this@CrearClientePr3Activity)
        builder.setMessage(mensaje)
            .setCancelable(false)
            .setPositiveButton("Aceptar") { dialog, id ->
                val intent = Intent(this@CrearClientePr3Activity,MenuClientesActivity::class.java)
                startActivity(intent)
                finish()
            }

        val alert: AlertDialog = builder.create()
        alert.setTitle("Crear Cliente Prospecto")
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