package sv.com.seguridadcontrol.app.erp.clientes

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_crear_cliente_pr2.*
import sv.com.seguridadcontrol.app.R

class CrearClientePr2Activity : AppCompatActivity() {
    var quiereGPS:Boolean=false
    var cantidadGPS:Int=0
    var precioGps:String=""
    var descripGPS:String=""

    var quiereCamara:Boolean=false
    var cantidadCam:Int=0
    var precioCam:String=""
    var descripCam:String=""

    var quiereAlarma:Boolean=false
    var cantidadAlm:Int=0
    var precioAlm:String=""
    var descripAlm:String=""

    var quiereVigilancia:Boolean=false
    var cantidadVig:Int=0
    var precioVig:String=""
    var descripVig:String=""

    var quiereOtrosSvc:Boolean=false
    var cantidadOtros:Int=0
    var precioOtros:String=""
    var descripOtros:String=""
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cliente_pr2)
        val SHARED:String=getString(R.string.sharedpref)
        sharedPreferences = getSharedPreferences(SHARED, 0)

        val tf = Typeface.createFromAsset(assets,"fonts/Nunito-Bold.ttf")
        val toolbar =
            findViewById<Toolbar>(R.id.tool_bar) // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar)
        val lblToolbar = toolbar.findViewById<TextView>(R.id.lblToolbar)
        lblToolbar.text = "Crear cliente prospecto (2)"
        lblToolbar.typeface = tf



        chkGPS.setOnCheckedChangeListener { c, b ->
            if(c.isChecked){
                txtCantidadGPS.isEnabled=true
                txtPrecioGPS.isEnabled=true
                txtDescripcionGPS.isEnabled=true
            }else{
                txtCantidadGPS.isEnabled=false
                txtPrecioGPS.isEnabled=false
                txtDescripcionGPS.isEnabled=false
            }
        }

        chkAlarma.setOnCheckedChangeListener { c, b ->
            if(c.isChecked){
                txtCantidadAlm.isEnabled=true
                txtPrecioAlm.isEnabled=true
                txtDescripcionAlm.isEnabled=true
            }else{
                txtCantidadAlm.isEnabled=false
                txtPrecioAlm.isEnabled=false
                txtDescripcionAlm.isEnabled=false
            }
        }

        chkCamara.setOnCheckedChangeListener { c, b ->
            if(c.isChecked){
                txtCantidadCam.isEnabled=true
                txtPrecioCam.isEnabled=true
                txtDescripcionCam.isEnabled=true
            }else{
                txtCantidadCam.isEnabled=false
                txtPrecioCam.isEnabled=false
                txtDescripcionCam.isEnabled=false
            }
        }

        ChkVigilancia.setOnCheckedChangeListener { c, b ->
            if(c.isChecked){
                txtCantidadVig.isEnabled=true
                txtPrecioVig.isEnabled=true
                txtDescripcionVig.isEnabled=true
            }else{
                txtCantidadVig.isEnabled=false
                txtPrecioVig.isEnabled=false
                txtDescripcionVig.isEnabled=false
            }
        }

        ChkOtros.setOnCheckedChangeListener { c, b ->
            if(c.isChecked){
                txtCantidadOtrs.isEnabled=true
                txtPrecioOtrs.isEnabled=true
                txtDescripcionOtrs.isEnabled=true
            }else{
                txtCantidadOtrs.isEnabled=false
                txtPrecioOtrs.isEnabled=false
                txtDescripcionOtrs.isEnabled=false
            }
        }



        rlClientePr3.setOnClickListener {
            val intent = Intent(this@CrearClientePr2Activity,CrearClientePr3Activity::class.java)

            quiereGPS = chkGPS.isChecked
            quiereCamara = chkCamara.isChecked
            quiereAlarma = chkAlarma.isChecked
            quiereVigilancia = ChkVigilancia.isChecked
            quiereOtrosSvc = ChkOtros.isChecked

            if(quiereGPS){

                cantidadGPS = txtCantidadGPS.text.toString().toInt()
                precioGps = txtPrecioGPS.text.toString()
                descripGPS = txtDescripcionGPS.text.toString()
                intent.putExtra("quiereGPS",quiereGPS)
                intent.putExtra("quiereGPS",quiereGPS)
                intent.putExtra("cantidadGPS",cantidadGPS)
                intent.putExtra("precioGPS",precioGps)
                intent.putExtra("descripGPS",descripGPS)
            }
            if(quiereAlarma){
                cantidadAlm = txtCantidadAlm.text.toString().toInt()
                precioAlm = txtPrecioAlm.text.toString()
                descripAlm = txtDescripcionAlm.text.toString()
                intent.putExtra("quiereAlarma",quiereAlarma)
                intent.putExtra("cantidadAlm",cantidadAlm)
                intent.putExtra("precioAlm",precioAlm)
                intent.putExtra("descripAlm",descripAlm)
            }

            if(quiereCamara){
                cantidadCam = txtCantidadCam.text.toString().toInt()
                precioCam = txtPrecioCam.text.toString()
                descripCam = txtDescripcionCam.text.toString()
                intent.putExtra("quiereCamara",quiereCamara)
                intent.putExtra("cantidadCam",cantidadCam)
                intent.putExtra("precioCam",precioCam)
                intent.putExtra("descripCam",descripCam)
            }
            if(quiereVigilancia){
                cantidadVig = txtCantidadVig.text.toString().toInt()
                precioVig = txtPrecioVig.text.toString()
                descripVig = txtDescripcionVig.text.toString()
                intent.putExtra("quiereVigilancia",quiereVigilancia)
                intent.putExtra("cantidadVig",cantidadVig)
                intent.putExtra("precioVig",precioVig)
                intent.putExtra("descripVig",descripVig)
            }

            if(quiereOtrosSvc){
                cantidadOtros = txtCantidadOtrs.text.toString().toInt()
                precioOtros = txtPrecioOtrs.text.toString()
                descripOtros = txtDescripcionOtrs.text.toString()
                intent.putExtra("quiereOtrosSvc",quiereOtrosSvc)
                intent.putExtra("cantidadOtros",cantidadOtros)
                intent.putExtra("precioOtros",precioOtros)
                intent.putExtra("descripOtros",descripOtros)
            }

            startActivity(intent)
        }

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