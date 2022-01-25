package sv.com.seguridadcontrol.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.com.seguridadcontrol.app.modelos.Ordenes
import sv.com.seguridadcontrol.app.modelos.TicketDetail
import sv.com.seguridadcontrol.app.networking.APIWS
import sv.com.seguridadcontrol.app.networking.IDSGTecnicosService

class TicketDetailsViewModel: ViewModel() {
    private var idsgService: IDSGTecnicosService
    private var ticketDetails:MutableLiveData<TicketDetail>
    init {
        ticketDetails = MutableLiveData()
        idsgService = APIWS.getTecnicosService()!!
    }
    fun getTicketDetailsObserver():MutableLiveData<TicketDetail>{
        return ticketDetails
    }

    fun getTicketDetails(ticket_id:String,task:String,token:String){
        idsgService.getTicketDetails(ticket_id,task,token)
            .enqueue(object: Callback<TicketDetail> {
                override fun onResponse(call: Call<TicketDetail>, response: Response<TicketDetail>) {
                    if(response!=null){
                        ticketDetails.postValue(response.body())
                    }else{
                        Log.d("ORDENES","response is null")
                    }


                }

                override fun onFailure(call: Call<TicketDetail>, t: Throwable) {
                    ticketDetails.postValue(null)
                    Log.d("ORDENES",t.localizedMessage)
                }

            })

    }

}