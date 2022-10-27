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
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_orders2.*
import kotlinx.android.synthetic.main.activity_orders2a.*
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.adapter.OrderDetailsArticle2Adapter
import sv.com.seguridadcontrol.app.modelos.Unit
import sv.com.seguridadcontrol.app.viewmodel.OrderDetailArticleViewModel
import sv.com.seguridadcontrol.app.viewmodel.OrderMaterialsViewModel
import sv.com.seguridadcontrol.app.viewmodel.OrdersViewModel

class Orders2Activity : AppCompatActivity() {
    private var sharedPreferences: SharedPreferences? = null
    private lateinit var orderMaterialsViewModel: OrderMaterialsViewModel
    private lateinit var orderDetailArticleViewModel : OrderDetailArticleViewModel
    private lateinit var orderViewModel:OrdersViewModel
    var articulos:ArrayList<Unit> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders2a)
        val SHARED:String=getString(R.string.sharedpref)
        sharedPreferences = getSharedPreferences(SHARED, 0)
        val userId: String? = sharedPreferences!!.getString("userId", "")
        orderDetailArticleViewModel =
            ViewModelProvider(this).get(OrderDetailArticleViewModel::class.java)
        orderViewModel =
            ViewModelProvider(this).get(OrdersViewModel::class.java)
        val token:String? = sharedPreferences!!.getString("token","")

        val tfb = Typeface.createFromAsset(assets,"fonts/Nunito-Bold.ttf")
        val tf = Typeface.createFromAsset(assets,"fonts/Nunito-Regular.ttf")
        val toolbar =
            findViewById<Toolbar>(R.id.tool_bar) // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar)
        val lblToolbar = toolbar.findViewById<TextView>(R.id.lblToolbar)
        lblToolbar.text = "Detalle de la orden (artículos)"
        lblToolbar.typeface = tfb
        txtIniciarOrden.typeface = tfb
        getArticles(intent.getStringExtra("orderId")!!,"orders_detail_article",token!!)

        rlIniciarOrden.setOnClickListener {

            val intent = Intent(this@Orders2Activity, OrdenTrabajoActivity::class.java)
            intent.putExtra("orderId",getIntent().getStringExtra("orderId"))
            intent.putExtra("orderNum",getIntent().getStringExtra("orderNum"))
            startOrder(intent.getStringExtra("orderId")!!,userId!!,"order_started",token!!)
            startActivity(intent)
        }



        imgInfo.setOnClickListener{
            val bottomSheet1 = BottomSheetDialog(this@Orders2Activity)
            bottomSheet1.setContentView(R.layout.activity_orders2)
            getDetailArticle(bottomSheet1,intent.getStringExtra("orderId")!!,"orders_detail_article",token!!)
            getDetailArticleDe(bottomSheet1,intent.getStringExtra("orderId")!!,"orders_detail_article_cambio", token!!)
            bottomSheet1.show()
        }


    }


    private fun startOrder(order_id: String, user_id: String, task: String, token: String) {
        orderViewModel.getStartOrderObserver().observe(this) { result ->
            Log.d("START_ORDER", result)
        }
        orderViewModel.startOder(order_id, user_id, task, token)
    }


    fun getDetailArticle(bottomSheet1:BottomSheetDialog, order_num: String, task:String, token:String){
        orderDetailArticleViewModel.getOrderDetailArticlesObserver().observe(this) { res ->
            val tfb = Typeface.createFromAsset(assets,"fonts/Nunito-Bold.ttf")
            val tf = Typeface.createFromAsset(assets,"fonts/Nunito-Regular.ttf")
            val txtUniActual: TextView? = bottomSheet1.findViewById(R.id.txtUniActual)
            val lblUActual: TextView? = bottomSheet1.findViewById(R.id.lblUActual)
            val lblUniActual: TextView? = bottomSheet1.findViewById(R.id.lblUniActual)
            val lblUnidadActualMarca: TextView? = bottomSheet1.findViewById(R.id.lblUnidadActualMarca)
            val txtUnidadActualMarca: TextView? = bottomSheet1.findViewById(R.id.txtUnidadActualMarca)
            val txtUnidadActualModelo: TextView? = bottomSheet1.findViewById(R.id.txtUnidadActualModelo)
            val lblUnidadActualModelo: TextView? = bottomSheet1.findViewById(R.id.lblUnidadActualModelo)
            val txtUnidadActualColor: TextView? = bottomSheet1.findViewById(R.id.txtUnidadActualColor)
            val lblUnidadActualColor: TextView? = bottomSheet1.findViewById(R.id.lblUnidadActualColor)

            val txtTicketCliente: TextView? = bottomSheet1.findViewById(R.id.txtTicketCliente)
            val lblTicketCliente: TextView? = bottomSheet1.findViewById(R.id.lblTicketCliente)
            val txtTicketOrdenNo: TextView? = bottomSheet1.findViewById(R.id.txtTicketOrdenNo)
            val lblTicketOrdNo: TextView? = bottomSheet1.findViewById(R.id.lblTicketOrdNo)
            val txtTicketInicia: TextView? = bottomSheet1.findViewById(R.id.txtTicketInicia)
            val lblTicketInicia: TextView? = bottomSheet1.findViewById(R.id.lblTicketInicia)
            val txtTicketFin: TextView? = bottomSheet1.findViewById(R.id.txtTicketFin)
            val lblTicketFin: TextView? = bottomSheet1.findViewById(R.id.lblTicketFin)


            val ticketId:String? = sharedPreferences!!.getString("ticketId","")
            val orderId:String? = intent.getStringExtra("orderId")
            val orderStart:String? = intent.getStringExtra("orderStart")
            val orderEnd:String? = intent.getStringExtra("orderEnd")


            txtTicketCliente?.typeface = tf
            lblTicketCliente?.typeface = tf
            txtTicketCliente?.text = ticketId

            txtTicketOrdenNo?.typeface = tf
            lblTicketOrdNo?.typeface = tf
            txtTicketOrdenNo?.text = orderId

            txtTicketInicia?.typeface = tf
            lblTicketInicia?.typeface = tf
            txtTicketInicia?.text = orderStart


            txtTicketFin?.typeface = tf
            lblTicketFin?.typeface = tf
            txtTicketFin?.text = orderEnd


            //Unidades
            lblUActual?.typeface = tfb
            txtUniActual?.typeface = tf
            lblUniActual?.typeface = tf

            txtUnidadActualMarca?.typeface = tf
            lblUnidadActualMarca?.typeface = tf

            lblUnidadActualModelo?.typeface = tf
            txtUnidadActualModelo?.typeface = tf

            txtUnidadActualColor?.typeface = tf
            lblUnidadActualColor?.typeface = tf



            try {
                res.unit.forEach { r ->
                    Log.d("ARTICLE", "${r.article}")
                    //No es artículo, es unidad
                    if (r.article == null) {
                        txtUniActual?.text = r.unit
                        var brand = "N/D"
                        try{
                            brand = r.unit_brand
                        }catch (e:Exception){}
                        txtUnidadActualMarca?.text = brand

                        var unit_model = "N/D"
                        try{
                            unit_model = r.unit_model
                        }catch (e:Exception){}
                        txtUnidadActualModelo?.text = unit_model
                        var unit_color = "N/D"
                        try{
                            unit_color = r.unit_color
                        }catch (e:Exception){}

                        txtUnidadActualColor?.text = unit_color
                    }

                }



            } catch (ex: Exception) {
                Log.d("ARTICLE", "${ex.message}")
            }

        }

        orderDetailArticleViewModel.getOrderDetailArticles(order_num, task, token)
    }
    fun getArticles( order_num: String, task:String, token:String){
        orderDetailArticleViewModel.getOrderDetailArticlesObserver().observe(this) { res ->
            articulos.clear()
            try {
                res.unit.forEach { r ->
                    Log.d("ARTICLE", "${r.article}")
                    //No es artículo, es unidad
                    if (r.article != null) {
                        articulos.add(Unit(r.article,r.category,r.id_article,"","",r.product,"","","",""))
                    }

                }

                val adapter = OrderDetailsArticle2Adapter(articulos)
                rvArticulosUnidades.layoutManager = LinearLayoutManager(this)
                rvArticulosUnidades.adapter = adapter

            } catch (ex: Exception) {
                Log.d("ARTICLE", "${ex.message}")
            }

        }

        orderDetailArticleViewModel.getOrderDetailArticles(order_num, task, token)
    }


    fun getDetailArticleDe(bottomSheet1:BottomSheetDialog, order_num:String, task:String, token:String){

        val txtUniDe: TextView? = bottomSheet1.findViewById(R.id.txtUniDe)
        val txtUnidadDeMarca: TextView? = bottomSheet1.findViewById(R.id.txtUnidadDeMarca)
        val txtUnidadDeModelo: TextView? = bottomSheet1.findViewById(R.id.txtUnidadDeModelo)
        val txtUnidadDeColor: TextView? = bottomSheet1.findViewById(R.id.txtUnidadDeColor)
        val lblUniDe: TextView? = bottomSheet1.findViewById(R.id.lblUniDe)
        val lblUDe: TextView? = bottomSheet1.findViewById(R.id.lblUDe)
        val lblUnidadDeMarca: TextView? = bottomSheet1.findViewById(R.id.lblUnidadDeMarca)
        val lblUnidadDeModelo: TextView? = bottomSheet1.findViewById(R.id.lblUnidadDeModelo)
        val lblUnidadDeColor: TextView? = bottomSheet1.findViewById(R.id.lblUnidadDeColor)

        val tfb = Typeface.createFromAsset(assets,"fonts/Nunito-Bold.ttf")
        val tf = Typeface.createFromAsset(assets,"fonts/Nunito-Regular.ttf")

        txtUniDe?.typeface = tf
        lblUniDe?.typeface = tf
        lblUDe?.typeface = tfb
        txtUnidadDeMarca?.typeface = tf
        lblUnidadDeMarca?.typeface = tf

        txtUnidadDeModelo?.typeface = tf
        lblUnidadDeModelo?.typeface = tf

        txtUnidadDeColor?.typeface = tf
        lblUnidadDeColor?.typeface = tf


        orderDetailArticleViewModel.getOrderDetailArticlesDeObserver().observe(this) { res ->
            if (res.unit != null) {
                res.unit.forEach { r ->
                    Log.d("ARTICLE", "${r.article}")
                    //No es artículo, es unidad
                    if (r.article == null) {
                        txtUniDe?.text = r.unit
                        txtUnidadDeMarca?.text = r.unit_brand
                        txtUnidadDeModelo?.text = r.unit_model
                        txtUnidadDeColor?.text = r.unit_color
                    }

                }
            }

        }

        orderDetailArticleViewModel.getOrderDetailArticlesDe(order_num, task, token)
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