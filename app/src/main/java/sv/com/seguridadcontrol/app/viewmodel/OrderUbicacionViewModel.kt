package sv.com.seguridadcontrol.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.com.seguridadcontrol.app.networking.APIWS
import sv.com.seguridadcontrol.app.networking.IDSGTecnicosService

class OrderUbicacionViewModel: ViewModel(){
    var exitoUbicaData: MutableLiveData<String>
    private var idsgService: IDSGTecnicosService

    init {
        exitoUbicaData = MutableLiveData()
        idsgService = APIWS.getTecnicosService()!!
    }
    fun storeUbicacionObserver(): MutableLiveData<String> {
        return exitoUbicaData
    }
    fun storeUbicacionImage(img: String, imgType:String, orderId:String, task:String, token:String){
        idsgService.storeUbicacionImage(img, imgType,orderId, task, token)
            .enqueue(object: Callback<String>
            {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("UBICACION",response.toString())
                    Log.d("UBICACION-BODY",response.body().toString())
                    Log.d("UBICACION-MESSAGE",response.message())
                    if(response.body().toString().equals("Exito.")){

                    }


                    if(response!=null){
                        exitoUbicaData.postValue(response.body())

                    }else{
                        Log.d("UBICACION","response is null")
                    }
                    //Log.d("ORDENES","${response.body()?.status?.get(0)?.facility}")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    exitoUbicaData.postValue(null)
                    Log.d("ORDENES",t.localizedMessage)
                    Log.d("ORDENES",t.message!!)
                    Log.d("ORDENES",t.toString())
                }

            })
    }
}