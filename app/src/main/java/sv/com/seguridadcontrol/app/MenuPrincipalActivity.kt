package sv.com.seguridadcontrol.app

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_menu_principal.*
import sv.com.seguridadcontrol.app.erp.tecnicos.ERPPrincipalActivity


class MenuPrincipalActivity: AppCompatActivity() {
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)
        val SHARED:String=getString(R.string.sharedpref)
        sharedPreferences = getSharedPreferences(SHARED, 0)
        imbERP.setOnClickListener {
            val intent = Intent(this@MenuPrincipalActivity,ERPPrincipalActivity::class.java)
            startActivity(intent)
        }
    }
}