package sv.com.seguridadcontrol.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.com.seguridadcontrol.app.modelos.*
import sv.com.seguridadcontrol.app.networking.APIWS
import sv.com.seguridadcontrol.app.networking.IDSGTecnicosService

class RegistrarCobroViewModel:ViewModel() {
    private var idsgService: IDSGTecnicosService
    private var branchData:MutableLiveData<Branch>
    private var clientData:MutableLiveData<Client>
    private var servicesClientData:MutableLiveData<ServicesClient>
    private var paymentResponseData:MutableLiveData<PaymentResponse>
    init {
        idsgService = APIWS.getTecnicosService()!!
        branchData = MutableLiveData()
        clientData = MutableLiveData()
        servicesClientData = MutableLiveData()
        paymentResponseData = MutableLiveData()
    }

    fun getBranchesObserver():MutableLiveData<Branch>{
        return branchData
    }
    fun getClientsObserver():MutableLiveData<Client>{
        return clientData
    }
    fun getServicesObserver():MutableLiveData<ServicesClient>{
        return servicesClientData
    }
    fun getPaymentResultObserver():MutableLiveData<PaymentResponse>{
        return paymentResponseData
    }

    fun getBranches(task:String,
                    token:String,
                    id_branch:String){
        idsgService.getBranches(task, token, id_branch)
            .enqueue(object: Callback<Branch> {
                override fun onResponse(call: Call<Branch>, response: Response<Branch>) {
                    if(response!=null){
                        branchData.postValue(response.body())
                    }else{

                    }
                }

                override fun onFailure(call: Call<Branch>, t: Throwable) {
                    branchData.postValue(null)
                }

            })

    }

    fun getClients(task:String,
                    token:String,
                   param:String,
                    id_branch:String){
        idsgService.getClients(task, token, param,id_branch)
            .enqueue(object: Callback<Client> {
                override fun onResponse(call: Call<Client>, response: Response<Client>) {
                    clientData.postValue(null)
                    if(response!=null){
                        clientData.postValue(response.body())
                    }else{

                    }
                }

                override fun onFailure(call: Call<Client>, t: Throwable) {
                    clientData.postValue(null)
                }

            })

    }

    fun getServicesClient(task:String,
                   token:String,
                   id_customer:String){
        idsgService.getServicesClient(task, token, id_customer)
            .enqueue(object: Callback<ServicesClient> {
                override fun onResponse(call: Call<ServicesClient>, response: Response<ServicesClient>) {
                    if(response!=null){
                        servicesClientData.postValue(response.body())
                    }else{

                    }
                }

                override fun onFailure(call: Call<ServicesClient>, t: Throwable) {
                    servicesClientData.postValue(null)
                }

            })

    }


    fun makePayment(task:String,
                    token:String,
                    data:String,
                    data_payment:String){
        idsgService.createPayment(task, token, data, data_payment)
            .enqueue(object: Callback<PaymentResponse> {
                override fun onResponse(call: Call<PaymentResponse>, response: Response<PaymentResponse>) {
                    if(response!=null){
                        paymentResponseData.postValue(response.body())
                    }else{

                    }
                }

                override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                    paymentResponseData.postValue(null)
                }

            })

    }

}