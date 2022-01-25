package sv.com.seguridadcontrol.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.com.seguridadcontrol.app.modelos.TicketProvisioning
import sv.com.seguridadcontrol.app.networking.APIWS
import sv.com.seguridadcontrol.app.networking.IDSGTecnicosService

class TicketProvisioningViewModel:ViewModel(){
    var ticketProvisioningData: MutableLiveData<TicketProvisioning>
    private var idsgService: IDSGTecnicosService
    init {
        ticketProvisioningData = MutableLiveData()
        idsgService = APIWS.getTecnicosService()!!
    }

    fun getTicketProvisioningResultObserver(): MutableLiveData<TicketProvisioning> {
        return ticketProvisioningData
    }

    fun getTicketProvisioning(user_id:String,task:String,token:String){
        idsgService.getTicketProvisioning(user_id,task,token)
            .enqueue(object: Callback<TicketProvisioning> {
                override fun onResponse(call: Call<TicketProvisioning>, response: Response<TicketProvisioning>) {
                    if(response!=null){
                        ticketProvisioningData.postValue(response.body())
                    }

                }

                override fun onFailure(call: Call<TicketProvisioning>, t: Throwable) {
                    ticketProvisioningData.postValue(null)
                    Log.d("ERROR-DSG",t.localizedMessage)
                }

            })
    }

}