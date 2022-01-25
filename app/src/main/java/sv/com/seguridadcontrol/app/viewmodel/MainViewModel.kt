package sv.com.seguridadcontrol.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sv.com.seguridadcontrol.app.modelos.Usuario
import sv.com.seguridadcontrol.app.networking.APIGPS
import sv.com.seguridadcontrol.app.networking.IGPSService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.com.seguridadcontrol.app.modelos.Login
import sv.com.seguridadcontrol.app.networking.APIWS
import sv.com.seguridadcontrol.app.networking.IDSGTecnicosService

class MainViewModel: ViewModel() {
    var userResultData: MutableLiveData<Login>
    private var idsgService: IDSGTecnicosService
    init {
        userResultData = MutableLiveData()
        idsgService = APIWS.getTecnicosService()!!
    }

    fun getUserDataResultObserver():MutableLiveData<Login>{
        return userResultData
    }

    fun getUserData(usr:String,pwd:String){
        val call = idsgService.iniciarSesion(usr,pwd)
        call.enqueue(object:Callback<Login>{
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                if(response!=null){
                    userResultData.postValue(response.body())
                }

            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                userResultData.postValue(null)
                Log.d("ERROR-DSG",t.localizedMessage)
            }

        })
    }


}