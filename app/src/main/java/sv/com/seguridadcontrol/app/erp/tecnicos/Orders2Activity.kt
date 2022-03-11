package sv.com.seguridadcontrol.app.erp.tecnicos

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_orders2.*
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.adapter.OrderDetailsArticle2Adapter
import sv.com.seguridadcontrol.app.modelos.Unit
import sv.com.seguridadcontrol.app.viewmodel.OrderDetailArticleViewModel

class Orders2Activity : AppCompatActivity() {
    private var sharedPreferences: SharedPreferences? = null
    private lateinit var orderDetailArticleViewModel : OrderDetailArticleViewModel
    var articulos:ArrayList<Unit> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders2)
        val SHARED:String=getString(R.string.sharedpref)
        sharedPreferences = getSharedPreferences(SHARED, 0)
        orderDetailArticleViewModel =
            ViewModelProvider(this).get(OrderDetailArticleViewModel::class.java)

        val token:String? = sharedPreferences!!.getString("token","")
        val ticketId:String? = sharedPreferences!!.getString("ticketId","")
        val orderId:String? = intent.getStringExtra("orderId")
        val orderStart:String? = intent.getStringExtra("orderStart")
        val orderEnd:String? = intent.getStringExtra("orderEnd")
        val tfb = Typeface.createFromAsset(assets,"fonts/Nunito-Bold.ttf")
        val tf = Typeface.createFromAsset(assets,"fonts/Nunito-Regular.ttf")
        val toolbar =
            findViewById<Toolbar>(R.id.tool_bar) // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar)
        val lblToolbar = toolbar.findViewById<TextView>(R.id.lblToolbar)
        lblToolbar.text = "Detalle de la orden"
        lblToolbar.typeface = tfb
        lblCliente.typeface = tfb
        txtTicketCliente.typeface = tf
        lblTicketCliente.typeface = tf
        txtTicketCliente.text = ticketId

        txtTicketOrdenNo.typeface = tf
        lblTicketOrdNo.typeface = tf
        txtTicketOrdenNo.text = orderId

        txtTicketInicia.typeface = tf
        lblTicketInicia.typeface = tf
        txtTicketInicia.text = orderStart


        txtTicketFin.typeface = tf
        lblTicketFin.typeface = tf
        txtTicketFin.text = orderEnd

        //Unidades
        lblUActual.typeface = tfb
        txtUniActual.typeface = tf
        lblUniActual.typeface = tf

        txtUnidadActualMarca.typeface = tf
        lblUnidadActualMarca.typeface = tf

        lblUnidadActualModelo.typeface = tf
        txtUnidadActualModelo.typeface = tf

        txtUnidadActualColor.typeface = tf
        lblUnidadActualColor.typeface = tf

        //Unidad De
        txtUniDe.typeface = tf
        lblUniDe.typeface = tf
        lblUDe.typeface = tfb
        txtUnidadDeMarca.typeface = tf
        lblUnidadDeMarca.typeface = tf

        txtUnidadDeModelo.typeface = tf
        lblUnidadDeModelo.typeface = tf

        txtUnidadDeColor.typeface = tf
        lblUnidadDeColor.typeface = tf

        getDetailArticle(intent.getStringExtra("orderId")!!,"orders_detail_article",token!!)
        //getDetailArticleDe(orderId,"orders_detail_article_cambio", token)



        btnIniciarOrden.setOnClickListener{
            val intent = Intent(this@Orders2Activity, OrdenTrabajoActivity::class.java)
            intent.putExtra("orderId",getIntent().getStringExtra("orderId"))
            intent.putExtra("orderNum",getIntent().getStringExtra("orderNum"))

            startActivity(intent)
        }


    }


    fun getDetailArticle(order_num:String, task:String, token:String){
        orderDetailArticleViewModel.getOrderDetailArticlesObserver().observe(this) { res ->
            try {
                res.unit.forEach { r ->
                    Log.d("ARTICLE", "${r.article}")
                    //No es artículo, es unidad
                    if (r.article == null) {
                        txtUniActual.text = r.unit
                        var brand = "N/D"
                        try{
                            brand = r.unit_brand
                        }catch (e:Exception){}
                        txtUnidadActualMarca.text = brand

                        var unit_model = "N/D"
                        try{
                            unit_model = r.unit_model
                        }catch (e:Exception){}
                        txtUnidadActualModelo.text = unit_model
                        var unit_color = "N/D"
                        try{
                            unit_color = r.unit_color
                        }catch (e:Exception){}

                        txtUnidadActualColor.text = unit_color
                    }else{
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


    fun getDetailArticleDe(order_num:String, task:String, token:String){
        orderDetailArticleViewModel.getOrderDetailArticlesDeObserver().observe(this) { res ->
            if (res.unit != null) {
                res.unit.forEach { r ->
                    Log.d("ARTICLE", "${r.article}")
                    //No es artículo, es unidad
                    if (r.article == null) {
                        txtUniDe.text = r.unit
                        txtUnidadDeMarca.text = r.unit_brand
                        txtUnidadDeModelo.text = r.unit_model
                        txtUnidadDeColor.text = r.unit_color
                    }

                }
            }

        }

        orderDetailArticleViewModel.getOrderDetailArticlesDe(order_num, task, token)
    }

}