package sv.com.seguridadcontrol.app.erp.clientes

import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_follow_prospect.*
import retrofit2.http.Field
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.adapter.GenericSpinnerAdapter
import sv.com.seguridadcontrol.app.adapter.ProspectClientSprAdapter
import sv.com.seguridadcontrol.app.modelos.FormaPago
import sv.com.seguridadcontrol.app.modelos.ProspectClientSpr
import sv.com.seguridadcontrol.app.viewmodel.ProspectClientViewModel

class FollowProspectActivity : AppCompatActivity() {
    private lateinit var prospectClientViewModel: ProspectClientViewModel
    var lstProspects: ArrayList<ProspectClientSpr> = ArrayList()
    var lstTipoCom: ArrayList<String> = ArrayList()
var tipoC:String=""
    var token:String=""
var id_prospecto:String=""
    var fechaProx: String = ""
    var horaProx: String = ""
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follow_prospect)
        prospectClientViewModel = ViewModelProvider(this).get(ProspectClientViewModel::class.java)

        val tf = Typeface.createFromAsset(assets, "fonts/Nunito-Bold.ttf")
        val toolbar =
            findViewById<Toolbar>(R.id.tool_bar)
        setSupportActionBar(toolbar)
        val lblToolbar = toolbar.findViewById<TextView>(R.id.lblToolbar)
        lblToolbar.text = "Seguimiento del cliente"
        lblToolbar.typeface = tf
        val SHARED: String = getString(R.string.sharedpref)
        sharedPreferences = getSharedPreferences(SHARED, 0)
        val userId: String? = sharedPreferences!!.getString("userId", "")
        token = sharedPreferences!!.getString("token", "").toString()


        getProspectClients("prospect_list", token, "Administrador", "11")
        getTipoCom()

        sprProspecto.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                try{
                    val p = sprProspecto?.getItemAtPosition(pos) as ProspectClientSpr
                    id_prospecto = p.id
                }catch (e:Exception){}
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        sprTipoCom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                try{
                    val t = sprTipoCom?.getItemAtPosition(pos) as String
                    tipoC = t
                }catch (e:Exception){}
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }


    fun getTipoCom() {
        lstTipoCom.add("Socialnetwork")
        lstTipoCom.add("Correo")
        lstTipoCom.add("Llamada")
        lstTipoCom.add("Visita")
        val adapter = GenericSpinnerAdapter(this@FollowProspectActivity, lstTipoCom)
        sprTipoCom.adapter = adapter
    }


    fun followProspectClient(
        task: String,
        token: String,
        user_id: String,
        data: String
    ) {
        prospectClientViewModel.followResponseObserver().observe(this) { result ->
            Log.d("DETALLE_PROSPECTO",result.message)
            Log.d("DETALLE_PROSPECTO",result.status.toString())
            if (result.message == "Exito") {
                Toast.makeText(
                    applicationContext,
                    "Seguimiento agendado exitosamente",
                    Toast.LENGTH_LONG
                ).show()



            }else{
                Toast.makeText(applicationContext,"Ha ocurrido un error",Toast.LENGTH_LONG).show()
            }

        }
        prospectClientViewModel.followProspectClient(task, token, user_id, data)

    }


        fun getProspectClients(
            task: String,
            token: String,
            user_level: String,
            user_id: String
        ) {
            prospectClientViewModel.getProspectClientsObserver().observe(this) { prospect ->
                prospect.data.forEach { client ->
                    val id = client.id
                    val nombre = client.nombre_comercial
                    lstProspects.add(ProspectClientSpr(id, nombre))
                }
                val adapter = ProspectClientSprAdapter(this@FollowProspectActivity, lstProspects)
                sprProspecto.adapter = adapter
            }

            prospectClientViewModel.getProspectClients(task, token, user_level, user_id)

        }



        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            val inflater = menuInflater
            inflater.inflate(R.menu.menu_transaccional_2, menu)

            // return true so that the menu pop up is opened
            return true
        }


        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            if (item.itemId == R.id.action_back) {
                onBackPressed()
            }
            if (item.itemId == R.id.action_send) {
                var agendadaTipo: String = "AM"
                var agendadaCont: String = "AM"
                var agendadaProx: String = "AM"
                if (chkAgendaPM.isChecked)
                    agendadaTipo = "PM"

                if (chkContacto.isChecked)
                    agendadaCont = "PM"
                if (chkProxCont.isChecked)
                    agendadaProx = "PM"

                val fechaAgendada =
                    txtFechaAgendada.text.toString() + " " + txtHoraAgendada.text.toString() + " " + agendadaTipo
                val fechaAgendadaCont =
                    txtFechaCont.text.toString() + " " + txtHoraCont.text.toString() + " " + agendadaCont
                val fechaAgendaPCont =
                    txtFechaProx.text.toString() + " " + txtHoraProx.text.toString() + " " + agendadaProx

                var data = "{\"id_prospecto\":\"$id_prospecto\",\"tipo_cliente\":\"P\",\"tipo_comunicacion\":\"$tipoC\"," +
                        "\"fecha_agenda\":\"$fechaAgendada\",\"fecha_contacto\":\"$fechaAgendadaCont\",\"estado\":\"abierto\",\"comentario\":\"${txtComentario.text.toString()}\",\"fecha_programada\":\"${fechaAgendaPCont}\"}"

                Log.d("DATA_PROSPECT",data)
                followProspectClient("prospect_following", "1daa71a4caa5d90165fc9cb4faab465b40d381ede147569dd7ba5a444b053fa8","11",data)


            }
            return true
        }

    }


