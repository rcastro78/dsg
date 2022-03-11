package sv.com.seguridadcontrol.app.erp.tecnicos

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_prov_materiales.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.adapter.OrdersMaterialsProvAdapter
import sv.com.seguridadcontrol.app.modelos.ProvisionamientoMateriales
import sv.com.seguridadcontrol.app.viewmodel.OrdersProvisioningViewModel

class ProvMaterialesActivity : AppCompatActivity() {
    var provisionamiento:ArrayList<ProvisionamientoMateriales> = ArrayList()
    private lateinit var adapter: OrdersMaterialsProvAdapter
    private lateinit var ordersProvViewModel: OrdersProvisioningViewModel
    private var sharedPreferences: SharedPreferences? = null
    var userId:String?=""
    var token:String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prov_materiales)
        val SHARED:String=getString(R.string.sharedpref)
        sharedPreferences = getSharedPreferences(SHARED, 0)
        userId = sharedPreferences!!.getString("userId","")
        token = sharedPreferences!!.getString("token","")
        ordersProvViewModel = ViewModelProvider(this).get(OrdersProvisioningViewModel::class.java)
        getProvisioningMaterials(userId!!,"orders_aprovisionamiento",token!!)

        foArticulos.setOnClickListener {
            val intent = Intent(this@ProvMaterialesActivity,ProvArticulosActivity::class.java)
            startActivity(intent)
        }


    }

    fun getProvisioningMaterials(user_id:String,task:String,token:String){
        ordersProvViewModel.getOrderProvisioningResultObserver().observe(this,{op->
            op.material.forEach({m ->
               val p = ProvisionamientoMateriales(m.product,m.brand,m.quantity,m.unity)
                provisionamiento.add(p)
            })


            adapter = OrdersMaterialsProvAdapter(provisionamiento)
            rvProvisioning.layoutManager = LinearLayoutManager(this)
            CoroutineScope(Dispatchers.Main).launch {
                rvProvisioning.adapter = adapter
            }



        })

        ordersProvViewModel.getOrdersProvisioningMaterials(user_id, task, token)

    }
}