package sv.com.seguridadcontrol.app.erp.tecnicos

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_orden_detalle_articulo.*
import kotlinx.android.synthetic.main.activity_orden_materiales.*
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.adapter.OrdersDetailArticleAdapter
import sv.com.seguridadcontrol.app.modelos.Unit
import sv.com.seguridadcontrol.app.viewmodel.OrderDetailArticleViewModel
import sv.com.seguridadcontrol.app.viewmodel.OrderMaterialsViewModel


class OrdenDetalleArticuloActivity : AppCompatActivity() {

    private lateinit var orderDetailArticleViewModel : OrderDetailArticleViewModel
    private lateinit var adapter: OrdersDetailArticleAdapter
    var lstMateriales:ArrayList<Unit> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orden_detalle_articulo)
        val tf = Typeface.createFromAsset(assets,"fonts/Nunito-Bold.ttf")
        val toolbar =
            findViewById<Toolbar>(R.id.tool_bar) // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar)
        val lblToolbar = toolbar.findViewById<TextView>(R.id.lblToolbar)
        lblToolbar.text = "Detalle de artículos para la orden"
        lblToolbar.typeface = tf
        lblArticuloUnidad.typeface=tf
        val orderId:String? = intent.getStringExtra("orderId")
        val token:String? = intent.getStringExtra("token")
        orderDetailArticleViewModel =
            ViewModelProvider(this).get(OrderDetailArticleViewModel::class.java)
        //getDetailArticle("167","orders_detail_article","7f642e7bff6909fac7b055ec70129e47aa95342a04f1550dc15ed11cdff32ce2")
        getDetailArticle(orderId!!,"orders_detail_article",token!!)
    }

    fun getDetailArticle(order_num:String, task:String, token:String){
        orderDetailArticleViewModel.getOrderDetailArticlesObserver().observe(this) { res ->
            res.unit.forEach { r ->
                Log.d("ARTICLE", "${r.article}")
                if (r.article != null) {
                    val unit = Unit(
                        r.article, r.category, r.id_article, "",
                        "", r.product, "r.unit", "",
                        "", ""
                    )

                    lstMateriales.add(unit)
                } else {
                    lblArticuloUnidad.text = "Unidad: " + r.unit
                    llUnidad.setBackgroundColor(Color.parseColor(r.order_color))
                }

            }

            adapter = OrdersDetailArticleAdapter(lstMateriales)
            rvArticulosOrden.layoutManager = LinearLayoutManager(this)
            rvArticulosOrden.adapter = adapter

            /**/
        }

        orderDetailArticleViewModel.getOrderDetailArticles(order_num, task, token)
    }

}