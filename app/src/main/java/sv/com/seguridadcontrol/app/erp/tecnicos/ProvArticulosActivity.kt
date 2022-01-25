package sv.com.seguridadcontrol.app.erp.tecnicos

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_prov_articulos.*
import kotlinx.android.synthetic.main.activity_prov_materiales.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.adapter.ArticlesMaterialsAdapter
import sv.com.seguridadcontrol.app.adapter.OrdersMaterialsProvAdapter
import sv.com.seguridadcontrol.app.modelos.Articulo
import sv.com.seguridadcontrol.app.modelos.MaterialArticle
import sv.com.seguridadcontrol.app.modelos.ProvisionamientoMateriales
import sv.com.seguridadcontrol.app.viewmodel.ArticleProvisioningViewModel

class ProvArticulosActivity : AppCompatActivity() {
    private lateinit var articleProvViewModel: ArticleProvisioningViewModel
    private lateinit var adapter: ArticlesMaterialsAdapter
    var lstArticulos:ArrayList<MaterialArticle> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prov_articulos)
        val tf = Typeface.createFromAsset(assets,"fonts/Nunito-Bold.ttf")

        articleProvViewModel =  ViewModelProvider(this).get(ArticleProvisioningViewModel::class.java)
        getArticulos("7","ticket_aprov_article","302d6b3a2ecd683c26e1f731897271ca757aa48fd3d802c53ff3a681108ffd1f")
        foMateriales2.setOnClickListener {
            val intent = Intent(this@ProvArticulosActivity,ProvMaterialesActivity::class.java)
            startActivity(intent)
        }
    }

    fun getArticulos(user_id:String,task:String,token:String){
        articleProvViewModel.getArticleProvisioningResultObserver().observe(this,{articulos->
            articulos.material.forEach { a->
                Log.d("ARTICULOS","${a.product}")
                val art = MaterialArticle(a.article,a.article_id,a.branch,a.brand,a.product,a.status)
                lstArticulos.add(art)
            }

            adapter = ArticlesMaterialsAdapter(lstArticulos)
            rvArticulos.layoutManager = LinearLayoutManager(this)
            CoroutineScope(Dispatchers.Main).launch {
                rvArticulos.adapter = adapter
            }


        })

        articleProvViewModel.getArticles(user_id,task,token)
    }

}