package sv.com.seguridadcontrol.app

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import sv.com.seguridadcontrol.app.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private var sharedPreferences: SharedPreferences? = null
    var userName:String=""
    var userPwd:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }





        setContentView(R.layout.activity_main)
        val SHARED:String=getString(R.string.sharedpref)
        sharedPreferences = getSharedPreferences(SHARED, 0)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        userName = sharedPreferences?.getString("userName","")!!
        userPwd = sharedPreferences?.getString("userPwd","")!!
        txtUsuario.setText(userName)
        txtClave.setText(userPwd)
        //var userName:String = txtUsuario.text.toString()
        //var userPwd:String = txtClave.text.toString()
        //userName="t.temporal"
        //userPwd="12345"
        //7f642e7bff6909fac7b055ec70129e47aa95342a04f1550dc15ed11cdff32ce2
        //8

        //emerson.martinez
        //302d6b3a2ecd683c26e1f731897271ca757aa48fd3d802c53ff3a681108ffd1f
        btnLogin.setOnClickListener {
            userName = txtUsuario.text.toString()
            userPwd = txtClave.text.toString()
            login(userName,userPwd)
        }
    }


    fun login(usr:String,pwd:String){
        val viewModel = mainViewModel
        viewModel.getUserDataResultObserver().observe(this) { usuario ->
            if (usuario != null) {
                var editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                editor.putString("userId", usuario.login[0].userID)
                editor.putString("userPwd", pwd)
                editor.putString("token", usuario.login[0].token)
                editor.putString("userName", usuario.login[0].user_name)
                editor.putString("userLastName", usuario.login[0].user_lastname)
                editor.putString("ticketTypeId", usuario.login[0].ticket_type_id)
                editor.putString("userType", usuario.login[0].userType)
                editor.putString("idUserType", usuario.login[0].id_user_type)

                editor.apply()

                val intent = Intent(this@MainActivity, MenuPrincipalActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "No puedes iniciar sesión", Toast.LENGTH_LONG)
                    .show()
            }
        }

        viewModel.getUserData(usr,pwd)
    }

}