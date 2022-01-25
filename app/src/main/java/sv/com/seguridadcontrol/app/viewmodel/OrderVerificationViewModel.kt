package sv.com.seguridadcontrol.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.com.seguridadcontrol.app.modelos.Ordenes
import sv.com.seguridadcontrol.app.modelos.OrderVerification
import sv.com.seguridadcontrol.app.networking.APIWS
import sv.com.seguridadcontrol.app.networking.IDSGTecnicosService
import java.util.*

class OrderVerificationViewModel:ViewModel() {
    var orderVerificationMutableData: MutableLiveData<OrderVerification>
    var exitoData: MutableLiveData<String>
    private var idsgService: IDSGTecnicosService

    init {
        orderVerificationMutableData = MutableLiveData()
        exitoData = MutableLiveData()
        idsgService = APIWS.getTecnicosService()!!
    }

    fun getOrderVerificationObserver():MutableLiveData<OrderVerification>{
        return orderVerificationMutableData
    }


    fun storeFacilitiesObserver():MutableLiveData<String>{
        return exitoData
    }

    fun storeComment(orderId:String,userId:String,comment:String,date:String,task:String,token: String){
        idsgService.storeOrderComment(orderId, userId, comment, date, task, token)
            .enqueue(object:Callback<String>
            {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response!=null){
                        exitoData.postValue(response.body())
                    }else{
                        Log.d("ORDENES","response is null")
                    }
                    //Log.d("ORDENES","${response.body()?.status?.get(0)?.facility}")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    exitoData.postValue(null)
                    Log.d("ORDENES",t.localizedMessage)
                }

            })
    }


    fun storeOrderFacilities(order_id:String,type:String,facility:String,
        turn:String,battery:String,task:String,token:String){
            idsgService.storeOrderFacilities(order_id,type,facility, turn, battery, task, token)
                .enqueue(object:Callback<String>
                {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if(response!=null){
                            exitoData.postValue(response.body())
                        }else{
                            Log.d("ORDENES","response is null")
                        }
                        //Log.d("ORDENES","${response.body()?.status?.get(0)?.facility}")

                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        exitoData.postValue(null)
                        Log.d("ORDENES",t.localizedMessage)
                    }

                })


    }


    fun getOrderVerificationDetail(order_id:String,user_id:String,task:String,token:String){
        idsgService.getOrderVerification(order_id,user_id,task,token)
            .enqueue(object:Callback<OrderVerification>
            {
                override fun onResponse(call: Call<OrderVerification>, response: Response<OrderVerification>) {
                    if(response!=null){
                        orderVerificationMutableData.postValue(response.body())
                    }else{
                        Log.d("ORDENES","response is null")
                    }
                    //Log.d("ORDENES","${response.body()?.status?.get(0)?.facility}")

                }

                override fun onFailure(call: Call<OrderVerification>, t: Throwable) {
                    orderVerificationMutableData.postValue(null)
                    Log.d("ORDENES",t.localizedMessage)
                }

            })
    }


}