package sv.com.seguridadcontrol.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.com.seguridadcontrol.app.networking.APIWS
import sv.com.seguridadcontrol.app.networking.IDSGTecnicosService

class OrderClientViewModel: ViewModel() {
    var exitoNombreData: MutableLiveData<String>
    private var idsgService: IDSGTecnicosService

    init {
        exitoNombreData = MutableLiveData()
        idsgService = APIWS.getTecnicosService()!!
    }
    fun storeClientNameObserver(): MutableLiveData<String> {
        return exitoNombreData
    }
    fun storeClienName(order_id:String, clientName:String, task:String, token:String){
        idsgService.storeOrderClientName(order_id,clientName, task, token)
            .enqueue(object: Callback<ResponseBody>
            {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Log.d("ORDENES",response.body()!!.string())
                    if(response!=null){
                        //exitoUbicaData.postValue(response.body())
                        Log.d("ORDENES",response.toString())
                    }else{
                        Log.d("ORDENES","response is null")
                    }
                    //Log.d("ORDENES","${response.body()?.status?.get(0)?.facility}")

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    exitoNombreData.postValue(null)
                    Log.d("ORDENES",t.localizedMessage)
                    Log.d("ORDENES",t.message!!)
                    Log.d("ORDENES",t.toString())
                }

            })
    }
}