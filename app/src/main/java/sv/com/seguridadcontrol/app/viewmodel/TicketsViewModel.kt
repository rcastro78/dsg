package sv.com.seguridadcontrol.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.com.seguridadcontrol.app.modelos.ApprovalResponse
import sv.com.seguridadcontrol.app.modelos.ApprovalTicket
import sv.com.seguridadcontrol.app.modelos.Ticket
import sv.com.seguridadcontrol.app.modelos.TicketApprovRequest
import sv.com.seguridadcontrol.app.networking.APIWS
import sv.com.seguridadcontrol.app.networking.IDSGTecnicosService

class TicketsViewModel: ViewModel() {
    var ticketsMutableData: MutableLiveData<Ticket>
    var ticketsApprovMutableData: MutableLiveData<ApprovalTicket>
    var exitoApproveData: MutableLiveData<ApprovalResponse>
    private var idsgService: IDSGTecnicosService
    init {
        ticketsMutableData = MutableLiveData()
        exitoApproveData = MutableLiveData()
        ticketsApprovMutableData = MutableLiveData()
        idsgService = APIWS.getTecnicosService()!!
    }

    fun getTicketsResultObserver():MutableLiveData<Ticket>{
        return ticketsMutableData
    }

    fun getTicketsApprovalResultObserver():MutableLiveData<ApprovalTicket>{
        return ticketsApprovMutableData
    }

    fun approveTicketsResultObserver():MutableLiveData<ApprovalResponse>{
        return exitoApproveData
    }



    fun approveTicket(userId:String,
                      orderId:String,
                      ticketOrdenId:String,
                      ticketId:String,
                      codigoTarea:String,
                      numSolicitud:String,
                      approvalOption:String,
                      task:String,
                      token:String){
        idsgService.approveTicket(userId, orderId, ticketOrdenId, ticketId,
            codigoTarea, numSolicitud, approvalOption, task, token)
            .enqueue(object: Callback<ApprovalResponse>
            {
                override fun onResponse(call: Call<ApprovalResponse>, response: Response<ApprovalResponse>) {

                    if(response!=null){
                        exitoApproveData.postValue(response.body())
                        Log.d("APPROVAL",response.body()!!.message)
                    }else{
                        exitoApproveData.postValue(null)
                        Log.d("APPROVAL","respuesta nula")
                    }
                    //Log.d("ORDENES","${response.body()?.status?.get(0)?.facility}")

                }

                override fun onFailure(call: Call<ApprovalResponse>, t: Throwable) {
                    exitoApproveData.postValue(null)
                    Log.e("APPROVAL",t.message!!)
                }

            })

    }

    fun getTicketsToApprove(type:String,task:String,token:String){
        idsgService.getTicketsApprovalList(type, task, token)
            .enqueue(object: Callback<ApprovalTicket> {
                override fun onResponse(call: Call<ApprovalTicket>, response: Response<ApprovalTicket>) {
                    if(response!=null){
                        try {
                            ticketsApprovMutableData.postValue(response.body())
                        }catch (ex:Exception){
                            ticketsApprovMutableData.postValue(null)
                        }
                    }else{
                        Log.d("TICKETS","response is null")
                    }


                }

                override fun onFailure(call: Call<ApprovalTicket>, t: Throwable) {
                    ticketsApprovMutableData.postValue(null)
                    Log.d("TICKETS",t.localizedMessage)
                }

            })
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