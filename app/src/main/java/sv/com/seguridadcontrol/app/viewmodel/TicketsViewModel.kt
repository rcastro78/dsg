package sv.com.seguridadcontrol.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.com.seguridadcontrol.app.modelos.Ordenes
import sv.com.seguridadcontrol.app.modelos.Ticket
import sv.com.seguridadcontrol.app.networking.APIWS
import sv.com.seguridadcontrol.app.networking.IDSGTecnicosService

class TicketsViewModel: ViewModel() {
    var ticketsMutableData: MutableLiveData<Ticket>
    private var idsgService: IDSGTecnicosService
    init {
        ticketsMutableData = MutableLiveData()
        idsgService = APIWS.getTecnicosService()!!
    }

    fun getTicketsResultObserver():MutableLiveData<Ticket>{
        return ticketsMutableData
    }



    fun getTickets(userId:String,type:String,start:String,quantity:String,task:String,token:String){
        idsgService.getTicketsList(userId,type,start, quantity, task, token)
            .enqueue(object: Callback<Ticket> {
                override fun onResponse(call: Call<Ticket>, response: Response<Ticket>) {
                    if(response!=null){
                        try {
                            ticketsMutableData.postValue(response.body())
                        }catch (ex:Exception){
                            ticketsMutableData.postValue(null)
                        }
                    }else{
                        Log.d("TICKETS","response is null")
                    }


                }

                override fun onFailure(call: Call<Ticket>, t: Throwable) {
                    ticketsMutableData.postValue(null)
                    Log.d("TICKETS",t.localizedMessage)
                }

            })
    }



}