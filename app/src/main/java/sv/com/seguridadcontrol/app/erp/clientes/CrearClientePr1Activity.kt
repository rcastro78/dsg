package sv.com.seguridadcontrol.app.erp.clientes

import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import retrofit2.Callback


import kotlinx.android.synthetic.main.activity_crear_cliente_pr1.*

import retrofit2.Call

import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.adapter.AdvisersSpinnerAdapter
import sv.com.seguridadcontrol.app.adapter.DepartmentSpinnerAdapter
import sv.com.seguridadcontrol.app.adapter.PaisSpinnerAdapter
import sv.com.seguridadcontrol.app.modelos.*
import sv.com.seguridadcontrol.app.networking.APIWS
import sv.com.seguridadcontrol.app.networking.IDSGTecnicosService
import sv.com.seguridadcontrol.app.viewmodel.CatalogViewModel

class CrearClientePr1Activity : AppCompatActivity() {
    private lateinit var catalogViewModel: CatalogViewModel
    var paises:ArrayList<Ctry> = ArrayList()
    var country:String=""
    var departments:ArrayList<Dep> = ArrayList()
    var dept:String=""
    var deptN:String=""
    var countryN:String=""
    var advisers:ArrayList<AdviserData> = ArrayList()
    var adv:String=""
    lateinit var idsgService: IDSGTecnicosService
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cliente_pr1)
        idsgService = APIWS.getTecnicosService()!!
        val SHARED:String=getString(R.string.sharedpref)
        sharedPreferences = getSharedPreferences(SHARED, 0)
        val tf = Typeface.createFromAsset(assets,"fonts/Nunito-Bold.ttf")
        val toolbar =
            findViewById<Toolbar>(R.id.tool_bar) // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar)
        val lblToolbar = toolbar.findViewById<TextView>(R.id.lblToolbar)
        lblToolbar.text = "Crear cliente prospecto (1)"
        lblToolbar.typeface = tf
        catalogViewModel = ViewModelProvider(this).get(CatalogViewModel::class.java)
        getAdvisers("catalogue_advisers","1daa71a4caa5d90165fc9cb4faab465b40d381ede147569dd7ba5a444b053fa8","1")
        getPaises("catalogue_country","1daa71a4caa5d90165fc9cb4faab465b40d381ede147569dd7ba5a444b053fa8")

        sprPais?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val pais:Ctry = sprPais?.getItemAtPosition(pos) as Ctry
                country = pais.id_pais
                countryN = pais.pais


                    getDepartamentos(
                        "catalogue_department",
                        "1daa71a4caa5d90165fc9cb4faab465b40d381ede147569dd7ba5a444b053fa8",
                        pais.id_pais)




            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        sprDepartamento?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {

                val dep:Dep = sprDepartamento?.getItemAtPosition(pos) as Dep
                dept = dep.id_departamento
                deptN = dep.departamento

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        sprAdvisers?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val adviser:AdviserData = sprAdvisers?.getItemAtPosition(pos) as AdviserData
                adv = adviser.id_empleado

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        rlClientePr2.setOnClickListener {
            catalogViewModel.clearDepartments()
        }
        
        /*

        rlClientePr2.setOnClickListener {
            //verificar que los campos vallan llenos

            val intent = Intent(this@CrearClientePr1Activity, CrearClientePr2Activity::class.java)
            val nombre = txtNombre.text
            val apellido = txtApellido.text
            val nombreComercial = txtNombreComercial.text
            val giro = txtGiroComercial.text
            val telf = txtTelef.text
            val celular = txtNumeroMovil.text
            val email = txtEmail.text

            val editor: SharedPreferences.Editor = sharedPreferences!!.edit()




            if (nombre.isNotEmpty() && apellido.isNotEmpty()
                && nombreComercial.isNotEmpty() && giro.isNotEmpty()
                && adv.isNotEmpty() && telf.isNotEmpty()
                && celular.isNotEmpty() && email.isNotEmpty()
            ) {
                editor.putString("nombreCliePro", nombre.toString())
                editor.putString("apeCliePro",apellido.toString())
                editor.putString("nombreComercial",nombreComercial.toString())
                editor.putString("giro",giro.toString())
                editor.putString("pais",country)
                editor.putString("department",dept)
                editor.putString("paisn",countryN)
                editor.putString("departmentn",deptN)
                editor.putString("adviser",adv)
                editor.putString("telefonoProspecto",txtTelef.text.toString())
                editor.putString("celularProspecto",txtNumeroMovil.text.toString())
                editor.putString("emailProspecto",txtEmail.text.toString())
                editor.apply()
                startActivity(intent)
            }
        }
*/


        //8175 1550 1943,75 3750

    }



    private fun getPaises(task:String,token:String){
        paises.clear()
        //paises.add(Ctry("0","A","0","Selecciona el país","0"))
        catalogViewModel.countriesObserver().observe(this){result->

            result.data.forEach {c->
                
                if(c.estado_pais == "A")
                    paises.add(Ctry(c.codigo_pais,c.estado_pais,c.id_pais,c.pais,c.prefijo_telefonico))
            }
            
            val paisAdapter = PaisSpinnerAdapter(this@CrearClientePr1Activity,paises)
            sprPais.adapter = paisAdapter
        }
        catalogViewModel.getPaises(task,token)
    }

    /*private fun getDepartamentos(task:String,token:String,cid:String){
        var deptos:ArrayList<Dep> = ArrayList()

        catalogViewModel.departmentsObserver().observe(this){r->
            r.data.forEach { d->
                deptos.add(Dep(d.codigo_departamento,d.departamento,d.id_departamento,d.id_pais))
            }

            val depAdapter = DepartmentSpinnerAdapter(this@CrearClientePr1Activity,deptos)
            sprDepartamento.adapter = depAdapter
        }

        catalogViewModel.getDepartamentos(task, token, cid)
    }*/

    private fun getDepartamentos(task:String,token:String,cid:String){
        departments.clear()
        idsgService.getDepartments(task,token,cid)
            .enqueue(object: Callback<Departments> {
                override fun onResponse(call: Call<Departments>, response: retrofit2.Response<Departments>) {
                   val departamento = response.body()
                    departamento!!.data.forEach {d->
                        departments.add(Dep(d.codigo_departamento,d.departamento,d.id_departamento,d.id_pais))
                    }

                    val depAdapter = DepartmentSpinnerAdapter(this@CrearClientePr1Activity,departments)
                    sprDepartamento.adapter = depAdapter
                }

                override fun onFailure(call: Call<Departments>, t: Throwable) {
                    Log.d("DEPARTAMENTOS",t.message!!)
                }

            })

    }




    private fun getAdvisers(task:String,token:String,idb:String){
        advisers.clear()
        catalogViewModel.advisersObserver().observe(this){result->
            result.data.forEach { a->
                advisers.add(AdviserData(a.id_empleado,a.apellido_empleado,a.nombre_empleado))
            }
            val adapter = AdvisersSpinnerAdapter(this@CrearClientePr1Activity,advisers)
            sprAdvisers.adapter = adapter
        }
        catalogViewModel.getAdvisers(task, token, idb)
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