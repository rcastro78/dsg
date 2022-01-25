package sv.com.seguridadcontrol.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.com.seguridadcontrol.app.modelos.OrderProvisioning
import sv.com.seguridadcontrol.app.networking.APIWS
import sv.com.seguridadcontrol.app.networking.IDSGTecnicosService

class OrdersProvisioningViewModel:ViewModel() {
    var ordersProvisioningMutableData : MutableLiveData<OrderProvisioning>
    private var idsgService: IDSGTecnicosService
    init {
        ordersProvisioningMutableData = MutableLiveData()
        idsgService = APIWS.getTecnicosService()!!
    }

    fun getOrderProvisioningResultObserver(): MutableLiveData<OrderProvisioning> {
        return ordersProvisioningMutableData
    }

    fun getOrdersProvisioningMaterials(user_id:String,task:String,token:String){
        idsgService.getOrderProvisioning(user_id,task,token)
            .enqueue(object: Callback<OrderProvisioning> {
                override fun onResponse(call: Call<OrderProvisioning>, response: Response<OrderProvisioning>) {
                    if(response!=null){
                        ordersProvisioningMutableData.postValue(response.body())
                    }

                }

                override fun onFailure(call: Call<OrderProvisioning>, t: Throwable) {
                    ordersProvisioningMutableData.postValue(null)
                    Log.d("ERROR-DSG",t.localizedMessage)
                }

            })
    }

}