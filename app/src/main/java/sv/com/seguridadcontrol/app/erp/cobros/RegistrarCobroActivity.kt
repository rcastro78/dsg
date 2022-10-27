package sv.com.seguridadcontrol.app.erp.cobros

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_registrar_cobro.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.com.seguridadcontrol.app.MenuPrincipalActivity
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.adapter.*
import sv.com.seguridadcontrol.app.dao.AppDatabase
import sv.com.seguridadcontrol.app.modelos.*
import sv.com.seguridadcontrol.app.networking.APIWS
import sv.com.seguridadcontrol.app.networking.IDSGTecnicosService
import sv.com.seguridadcontrol.app.utilities.Constants
import sv.com.seguridadcontrol.app.viewmodel.RegistrarCobroViewModel
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegistrarCobroActivity : AppCompatActivity() {
    private lateinit var registrarCobroViewModel:RegistrarCobroViewModel
    var branches:ArrayList<BranchSpr> = ArrayList()
    var formas:ArrayList<FormaPago> = ArrayList()
    var clients:ArrayList<ClientSpr> = ArrayList()
    var services:ArrayList<ServiceClientSpr> = ArrayList()
    val lstFactura:ArrayList<Factura> = ArrayList()
    var idBranch:String=""
    var idCliente:String=""
    var idService:String=""
    var forma:String=""
    var empresaNombre:String=""
    var tipoPago:String=""
    lateinit var idsgService: IDSGTecnicosService
    private var sharedPreferences: SharedPreferences? = null
    private lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_cobro)
        registrarCobroViewModel = ViewModelProvider(this).get(RegistrarCobroViewModel::class.java)
        idsgService = APIWS.getTecnicosService()!!
        val tf = Typeface.createFromAsset(assets,"fonts/Nunito-Bold.ttf")
        val toolbar =
            findViewById<Toolbar>(R.id.tool_bar) // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar)
        val lblToolbar = toolbar.findViewById<TextView>(R.id.lblToolbar)
        lblToolbar.text = "Registrar cobro"
        val SHARED:String=getString(R.string.sharedpref)
        sharedPreferences = getSharedPreferences(SHARED, 0)
        getFormaPago()
        val userId: String? = sharedPreferences!!.getString("userId", "")
        val token:String? = sharedPreferences!!.getString("token","")
        getBranches("catalogue_branch",token!!,"1")
        sprEmpresa.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                try{
                    val empresa: BranchSpr = sprEmpresa?.getItemAtPosition(pos) as BranchSpr
                    idBranch = empresa.id_sucursal
                    empresaNombre = empresa.nombre_comercial
                    clients.clear()
                    getClients("catalogue_client",token!!,"1",idBranch)
                }catch (e:Exception){}
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        sprCliente.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                try{
                    val cliente: ClientSpr = sprCliente?.getItemAtPosition(pos) as ClientSpr
                    idCliente = cliente.id_cliente
                    services.clear()
                   getServices("catalogue_services_customer",token,idCliente)
                   getFacturas(idCliente.toInt(),idBranch.toInt(),this@RegistrarCobroActivity)
                }catch (e:Exception){

                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        sprServicios.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                try{
                    val svc: ServiceClientSpr = sprServicios?.getItemAtPosition(pos) as ServiceClientSpr
                    idService = svc.id_servicio_cliente
                }catch (e:Exception){

                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        rbtParcial.setOnClickListener {
            if(rbtParcial.isChecked){
                tipoPago="2"
            }
        }
        rbtCompleto.setOnClickListener {
            if(rbtCompleto.isChecked){
                tipoPago="1"
            }
        }


        btnAddFactura.setOnClickListener {

        }

    }

    private fun getFormaPago(){
        formas.clear()
        formas.add(FormaPago("1","Efectivo"))
        formas.add(FormaPago("2","Cheque"))
        formas.add(FormaPago("3","Transferencia"))
        formas.add(FormaPago("4","Tarjeta"))

    }

    private fun getFacturas(cliente:Int,empresa:Int,context: Context) {
        db = AppDatabase.getInstance(context)
        lstFactura.clear()

          CoroutineScope(Dispatchers.IO).launch {

            val facturas = db.iFacturaDAO.getFacturas(cliente,empresa)
              Log.d("TAMANIO",facturas.size.toString())
            var fpago:String=""
            if(facturas.size>0) {
                facturas.forEach { f ->
                    if (f.forma_pago == Constants.EFECTIVO) {
                        fpago = "Efectivo"
                    }
                    if (f.forma_pago == Constants.CHEQUE) {
                        fpago = "Cheque"
                    }
                    if (f.forma_pago == Constants.TRANSFERENCIA) {
                        fpago = "Transferencia"
                    }
                    if (f.forma_pago == Constants.TARJETA) {
                        fpago = "Tarjeta"
                    }
                    val factura = Factura(f.num_factura, f.monto, f.fecha, fpago)
                    lstFactura.add(factura)
                }
                CoroutineScope(Dispatchers.Main).launch {
                    if(lstFactura.isNotEmpty()) {

                        val adapter = FacturaAdapter(lstFactura, this@RegistrarCobroActivity)

                        rvFacturas.layoutManager =
                            LinearLayoutManager(
                                this@RegistrarCobroActivity,
                                RecyclerView.VERTICAL,
                                false
                            )
                        rvFacturas.adapter = adapter

                    }else{

                    }
                }
            }else{
                //Esto es para limpiar el recyclerview
                CoroutineScope(Dispatchers.Main).launch {
                    rvFacturas.adapter = null
                }
            }
        }

    }


    private fun getServices(task:String,
                            token:String,
                            id_customer:String){
        idsgService.getServicesClient(task, token, id_customer)
            .enqueue(object: Callback<ServicesClient> {
                override fun onResponse(call: Call<ServicesClient>, response: Response<ServicesClient>) {
                    if(response!=null){
                        val s = response.body()
                        s!!.data.forEach { service->
                            services.add(ServiceClientSpr(service.id_servicio_cliente,service.servicio))
                        }
                        val adapter = ServiceClientSpinnerAdapter(this@RegistrarCobroActivity,services)
                        sprServicios.adapter = adapter
                    }else{

                    }
                }

                override fun onFailure(call: Call<ServicesClient>, t: Throwable) {

                }

            })

    }



    private fun getClients(task:String,
                                 token:String,
                                 param:String,
                                 id_branch:String){


        idsgService.getClients(task, token, param,id_branch)
            .enqueue(object: Callback<Client> {
                override fun onResponse(call: Call<Client>, response: Response<Client>) {

                    if(response!=null){
                        val c = response.body()
                        c!!.data.forEach { client->
                            val clientName = client.nombre_cliente+" "+client.apellido_cliente
                            val idClient = client.id_cliente
                            clients.add(ClientSpr(clientName,idClient))
                        }
                        val adapter = ClientSpinnerAdapter(this@RegistrarCobroActivity,clients)
                        sprCliente.adapter = adapter
                    }else{

                    }
                }

                override fun onFailure(call: Call<Client>, t: Throwable) {

                }

            })

    }


    private fun registrarCobro(task:String,token:String,data:String,data_payment:String){
        registrarCobroViewModel.getPaymentResultObserver().observe(this){result->
            Log.d("FACTURA",result.message)
            if(result.message.contains("Exito")){
                Toast.makeText(applicationContext,"Pago procesado exitosamente",Toast.LENGTH_LONG).show()
                lstFactura.forEach { f->
                    CoroutineScope(Dispatchers.IO).launch {
                        db.iFacturaDAO.delete(f.numFactura)
                    }
                }
                val intent = Intent(this@RegistrarCobroActivity,MenuPrincipalActivity::class.java)
                startActivity(intent)
            }
        }
        registrarCobroViewModel.makePayment(task,token,data,data_payment)
    }



    fun validarFecha(fecha:String):Boolean{
        val regex = "^(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)\\d\\d\$"
        val pattern: Pattern = Pattern.compile(regex)
        val matcher: Matcher = pattern.matcher(fecha)
        return matcher.matches()
    }



    private fun getBranches(task:String,
                            token:String,
                            id_branch:String){


        registrarCobroViewModel.getBranchesObserver().observe(this) { result ->
                result.data.forEach { branch->
                    val branchName = branch.nombre_comercial+" - "+branch.pais
                    val branchId = branch.id_sucursal
                    branches.add(BranchSpr(branchId,branchName))
                }
            val adapter = BranchSpinnerAdapter(this@RegistrarCobroActivity,branches)
            sprEmpresa.adapter = adapter
        }


        registrarCobroViewModel.getBranches(task,token,id_branch)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_transaccional_2, menu)

        // return true so that the menu pop up is opened
        return true
    }





    override fun onOptionsItemSelected(item: MenuItem):Boolean
    {

        if (item.itemId == R.id.action_add) {
            db = AppDatabase.getInstance(this.application)
            val tfMedium = Typeface.createFromAsset(assets, "fonts/Nunito-Regular.ttf")
            val tf = Typeface.createFromAsset(assets,"fonts/Nunito-Bold.ttf")
            val bottomSheet = BottomSheetDialog(this@RegistrarCobroActivity)
            bottomSheet.setContentView(R.layout.bottom_view_add_factura)
            val btnRegFactura: Button? = bottomSheet.findViewById(R.id.btnRegFactura)
            val txtNumFactura: TextView? = bottomSheet.findViewById(R.id.txtNumFactura)
            val txtMontoFactura: TextView? = bottomSheet.findViewById(R.id.txtMontoFactura)
            val txtFechaFactura: TextView? = bottomSheet.findViewById(R.id.txtFechaFactura)
            btnRegFactura?.typeface=tf
            txtNumFactura?.typeface=tf
            txtMontoFactura?.typeface=tf
            txtFechaFactura?.typeface=tf

            btnRegFactura?.setOnClickListener{
                val numFactura = txtNumFactura?.text.toString()
                val monto = txtMontoFactura?.text.toString()
                val fecha = txtFechaFactura?.text.toString()

                if(numFactura.isNotEmpty() && monto.isNotEmpty() && fecha.isNotEmpty() && validarFecha(fecha))
                    CoroutineScope(Dispatchers.IO).launch {
                        db.iFacturaDAO.guardarFactura(numFactura,monto,fecha,forma.toInt(),0,
                            idBranch.toInt(),idCliente.toInt(),idService.toInt())
                    }
                else
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(applicationContext,"Faltan campos obligatorios o la fecha no tiene el formato correcto", Toast.LENGTH_LONG).show()
                    }

                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(applicationContext,"factura núm: $numFactura guardada exitosamente", Toast.LENGTH_LONG).show()
                    bottomSheet.dismiss()
                    getFacturas(idCliente.toInt(),idBranch.toInt(),this@RegistrarCobroActivity)
                }

            }
            val sprFormaPago:Spinner? = bottomSheet.findViewById(R.id.sprFormaPago)
            val adapter = SpinnerFormaPagoAdapter(this@RegistrarCobroActivity,formas)
            sprFormaPago?.adapter = adapter
            sprFormaPago?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    try{
                        val formaPago = sprFormaPago?.getItemAtPosition(pos) as FormaPago
                        forma = formaPago.idForma
                    }catch (e:Exception){}
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }


            bottomSheet.show()
        }

        if (item.itemId == R.id.action_back) {
            onBackPressed()
        }
        if (item.itemId == R.id.action_send) {

            var data = "{\"empresa\":\"$empresaNombre\",\"selectCliente\":\"$idCliente\",\"radioPago\":\"$tipoPago\"}"
            var tipoPago:String=""
            var data_payment = "["
            var i=0
            lstFactura.forEach { f->
                i++
                val dia = f.fecha.split("/")[0]
                val mes = f.fecha.split("/")[1]
                val anio = f.fecha.split("/")[2]
                val fechaFormateada = "$anio-$mes-$dia"
                if(f.formaPago=="Efectivo")
                    tipoPago="1"
                if(f.formaPago=="Cheque")
                    tipoPago="2"
                if(f.formaPago=="Transferencia")
                    tipoPago="3"
                if(f.formaPago=="Tarjeta")
                    tipoPago="4"
                data_payment += "{\"bill\":\"$i\",\"amount\":\"${f.monto}\",\"date\":\"$fechaFormateada\",\"selectPayment\":\"${tipoPago}\"},"
            }

            data_payment = data_payment.replace("},","}]").replace("}]{","},{")
            val token:String? = sharedPreferences!!.getString("token","")
            registrarCobro("collection_payment", token!!,data,data_payment)






        }
        return true
    }
}