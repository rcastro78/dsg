package sv.com.seguridadcontrol.app.erp.tecnicos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_orden_materiales.*
import kotlinx.android.synthetic.main.activity_prov_articulos.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.adapter.OrdersMaterialsAdapter
import sv.com.seguridadcontrol.app.adapter.SpinnerAdapter
import sv.com.seguridadcontrol.app.dao.AppDatabase
import sv.com.seguridadcontrol.app.modelos.MaterialArticle
import sv.com.seguridadcontrol.app.modelos.Order
import sv.com.seguridadcontrol.app.modelos.OrderMaterials
import sv.com.seguridadcontrol.app.viewmodel.OrderMaterialsViewModel
import sv.com.seguridadcontrol.app.viewmodel.OrdersViewModel

class OrdenMaterialesActivity : AppCompatActivity() {
    private lateinit var orderMaterialsViewModel: OrderMaterialsViewModel
    private lateinit var orderViewModel: OrdersViewModel
    private lateinit var adapter: OrdersMaterialsAdapter
    private var sharedPreferences: SharedPreferences? = null
    var lstMateriales: ArrayList<OrderMaterials> = ArrayList()
    var orderNum: String = ""
    var orderId: String = ""
    var cantidad: String = ""
    var idMaterial: String = ""
    var nomMaterial: String = ""
    var unidades: String = ""
    private lateinit var db: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orden_materiales)
        val SHARED: String = getString(R.string.sharedpref)
        sharedPreferences = getSharedPreferences(SHARED, 0)
        val tf = Typeface.createFromAsset(assets, "fonts/Nunito-Bold.ttf")
        val toolbar =
            findViewById<Toolbar>(R.id.tool_bar) // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar)
        val lblToolbar = toolbar.findViewById<TextView>(R.id.lblToolbar)
        lblToolbar.text = "Materiales para la orden " + intent.getStringExtra("orderId")!!
        lblToolbar.typeface = tf
        val userId: String? = sharedPreferences!!.getString("userId", "")
        val token: String? = sharedPreferences!!.getString("token", "")
        orderNum = intent.getStringExtra("orderNum")!!
        orderId = intent.getStringExtra("orderId")!!
        orderMaterialsViewModel =
            ViewModelProvider(this).get(OrderMaterialsViewModel::class.java)
        orderViewModel =
            ViewModelProvider(this).get(OrdersViewModel::class.java)


        CoroutineScope(Dispatchers.IO).launch {
            fillMaterials(orderId,this@OrdenMaterialesActivity)
        }


        btnAgregarMateriales.setOnClickListener {
            val bottomSheet1 = BottomSheetDialog(this@OrdenMaterialesActivity)
            bottomSheet1.setContentView(R.layout.activity_materiales_dialog)
            val sprMateriales: Spinner? = bottomSheet1.findViewById(R.id.sprMateriales)
            val btnGuardarMateriales: Button? = bottomSheet1.findViewById(R.id.btnGuardarMateriales)
            val txtCantidadMaterial: TextView? = bottomSheet1.findViewById(R.id.txtCantidadMaterial)
            getMaterials(sprMateriales!!,orderId, userId!!, "orders_user_materials", token!!)
            sprMateriales?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    val material: OrderMaterials =
                        sprMateriales?.getItemAtPosition(pos) as OrderMaterials
                    cantidad = txtCantidadMaterial?.text.toString()
                    idMaterial = material.materials_id
                    nomMaterial = material.materials
                    unidades = material.unity
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }


            btnGuardarMateriales?.setOnClickListener {
                db = AppDatabase.getInstance(this.application)
                cantidad = txtCantidadMaterial?.text.toString()
                if (!cantidad.equals("0")) {
                    CoroutineScope(Dispatchers.IO).launch {
                        if(db.iMaterialesDAO.getMaterialOrden(orderId,idMaterial).isEmpty()) {
                            db.iMaterialesDAO.insert(
                                orderId,
                                idMaterial,
                                nomMaterial,
                                cantidad,
                                unidades
                            )
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(applicationContext,"$nomMaterial agregado exitosamente",Toast.LENGTH_LONG).show()
                            }
                        }else{
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(applicationContext,"Ya se ingresó este material",Toast.LENGTH_LONG).show()
                            }

                        }
                        fillMaterials(orderId,this@OrdenMaterialesActivity)
                    }

                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(applicationContext, "No puedes guardar 0", Toast.LENGTH_LONG).show()
                    }

                }

            }




            bottomSheet1.show()
        }







        btnProcesarMateriales.setOnClickListener {
            db = AppDatabase.getInstance(this.application)
            var materialsList = "["

            CoroutineScope(Dispatchers.IO).launch {
                val materialesOrden = db.iMaterialesDAO.getMateriales(orderId)
                materialesOrden.forEach{material->
                    materialsList += "{\"material_id\":${material.id_material},\"material_used\":${material.cantidad}},"

                }

                materialsList+="]"
                materialsList = materialsList.replace("},]","}]")
                Log.d("MATERIALESORDER",materialsList)
                CoroutineScope(Dispatchers.Main).launch {
                    orderMaterialsViewModel.getOrderMaterialsStoreObserver()
                        .observe(this@OrdenMaterialesActivity) { result ->
                            if (result.contains("Exito")) {
                                /*Toast.makeText(
                                    applicationContext,
                                    "Materiales procesados exitosamente",
                                    Toast.LENGTH_LONG
                                ).show()*/

                                  Log.d("MATERIALESORDER",result)

                                val intent = Intent(
                                    this@OrdenMaterialesActivity,
                                    OrdenTrabajoActivity::class.java
                                )
                                intent.putExtra("orderId", orderId)
                                intent.putExtra("orderNum", orderId)
                                //intent.getStringExtra("orderNum")!!
                                startActivity(intent)
                            } else {
                                Log.d("MATERIALESORDER","Error")
                            }
                        }
                    if (token != null) {
                        orderMaterialsViewModel.storeOrderMaterials(
                            orderId,
                            materialsList,
                            "orders_stored_materials",
                            token
                        )
                    }
                }


            }

        }






       /* fabStartOrder1.setOnClickListener {
            startOrder(orderId, userId, "orders_started", token)
            val intent = Intent(this@OrdenMaterialesActivity, OrdenTrabajoActivity::class.java)
            intent.putExtra("orderNum", orderNum)
            it.context.startActivity(intent)
        }*/


    }





    fun fillMaterials(order_id: String,context: Context) {
        db = AppDatabase.getInstance(context)
        var lstMateriales2: ArrayList<OrderMaterials> = ArrayList()
        //orderMaterialsViewModel.getOrderMaterialsObserver().observe(this,{ordenMaterial->
        CoroutineScope(Dispatchers.IO).launch {
            val materialesOrden = db.iMaterialesDAO.getMateriales(order_id)

            materialesOrden.forEach { o ->
                val ordenM =
                    OrderMaterials(
                        o.nombre_material!!,
                        o.id_material!!,
                        o.cantidad!!,
                        "",
                        o.unidad!!
                    )
                lstMateriales2.add(ordenM)

            }
        }
        CoroutineScope(Dispatchers.Main).launch {
           // Toast.makeText(applicationContext,"${lstMateriales2.size}",Toast.LENGTH_LONG).show()
            rvMaterialOrden.layoutManager =
                LinearLayoutManager(this@OrdenMaterialesActivity, RecyclerView.VERTICAL, false)
            val adapter = OrdersMaterialsAdapter(lstMateriales2,order_id,this@OrdenMaterialesActivity)
            rvMaterialOrden.adapter = adapter
        }

    }






private fun getMaterials(spr:Spinner, order_id:String, user_id:String, task:String, token:String){
orderMaterialsViewModel.getOrderMaterialsObserver().observe(this) { ordenMaterial ->

    ordenMaterial.order.forEach { o ->
        val ordenM = OrderMaterials(o.materials, o.materials_id, o.materials_quantity, "", o.unity)
        lstMateriales.add(ordenM)
    }
    val adapter = SpinnerAdapter(this@OrdenMaterialesActivity, lstMateriales)
    spr.adapter = adapter
}

    orderMaterialsViewModel.getOrderMaterials(order_id, user_id, task, token)

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


