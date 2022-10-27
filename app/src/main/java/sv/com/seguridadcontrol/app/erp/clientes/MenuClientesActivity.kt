package sv.com.seguridadcontrol.app.erp.clientes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_menu_clientes.*
import sv.com.seguridadcontrol.app.R

class MenuClientesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_clientes)
        btnCrearCliente.setOnClickListener {
            val intent = Intent(this@MenuClientesActivity,CrearClientePr1Activity::class.java)
            startActivity(intent)
        }

        btnFollowClientes.setOnClickListener {
            val intent = Intent(this@MenuClientesActivity,FollowProspectActivity::class.java)
            startActivity(intent)
        }

        btnVolver.setOnClickListener {
           onBackPressed()
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