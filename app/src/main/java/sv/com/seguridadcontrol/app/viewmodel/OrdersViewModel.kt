package sv.com.seguridadcontrol.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sv.com.seguridadcontrol.app.networking.APIWS
import sv.com.seguridadcontrol.app.networking.IDSGTecnicosService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.com.seguridadcontrol.app.modelos.Ordenes

class OrdersViewModel: ViewModel(){
var ordersMutableData:MutableLiveData<Ordenes>
var orderStartResultData:MutableLiveData<String>
var ticketCompletedResultData:MutableLiveData<String>

    private var idsgService: IDSGTecnicosService
    init {
        ordersMutableData = MutableLiveData()
        orderStartResultData = MutableLiveData()
        ticketCompletedResultData = MutableLiveData()
        idsgService = APIWS.getTecnicosService()!!
    }

    fun getOrdersResultObserver():MutableLiveData<Ordenes>{
        return ordersMutableData
    }

    fun getStartOrderObserver():MutableLiveData<String>{
        return orderStartResultData
    }
    fun ticketCompletedObserver():MutableLiveData<String>{
        return ticketCompletedResultData
    }


    fun ticketCompleted(ticket_id: String,user_id:String,comment:String,task:String,token: String,date:String){
        idsgService.ticketCompleted(ticket_id,user_id,comment, task, token,date)
            .enqueue(object:Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response!=null){
                        ticketCompletedResultData.postValue(response.body())
                    }else{
                        Log.d("TICKET","response is null")
                    }


                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    ticketCompletedResultData.postValue(null)
                    Log.d("TICKET",t.localizedMessage)
                }

            })
    }

    fun startOder(order_id:String,user_id:String,task:String,token: String){
        idsgService.startOrder(order_id,user_id,task,token)
            .enqueue(object:Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response!=null){
                        orderStartResultData.postValue(response.body())
                    }else{
                        Log.d("ORDENES","response is null")
                    }


                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    orderStartResultData.postValue(null)
                    Log.d("ORDENES",t.localizedMessage)
                }

            })
    }



    fun getOrdenes(ticket_id:String, task:String, token:String){
        idsgService.getOrderList(ticket_id,task,token)
        .enqueue(object:Callback<Ordenes>{
                override fun onResponse(call: Call<Ordenes>, response: Response<Ordenes>) {
                    if(response!=null){
                        ordersMutableData.postValue(response.body())
                    }else{
                        Log.d("ORDENES","response is null")
                    }
                    Log.d("ORDENES","${response.body()?.order?.get(0)?.employed_lastname}")


                }

                override fun onFailure(call: Call<Ordenes>, t: Throwable) {
                    ordersMutableData.postValue(null)
                    Log.d("ORDENES",t.localizedMessage)
                }

            })


    }

}