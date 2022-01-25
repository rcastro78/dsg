package sv.com.seguridadcontrol.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.com.seguridadcontrol.app.modelos.OrderUserMaterial
import sv.com.seguridadcontrol.app.networking.APIWS
import sv.com.seguridadcontrol.app.networking.IDSGTecnicosService

class OrderMaterialsViewModel:ViewModel(){
    private var idsgService: IDSGTecnicosService
    private var orderMaterialsData : MutableLiveData<OrderUserMaterial>
    private var orderMaterialsStoreData : MutableLiveData<String>
    init {
        orderMaterialsData = MutableLiveData()
        orderMaterialsStoreData = MutableLiveData()
        idsgService = APIWS.getTecnicosService()!!
    }

    fun getOrderMaterialsObserver():MutableLiveData<OrderUserMaterial>{
        return orderMaterialsData
    }
    fun getOrderMaterialsStoreObserver():MutableLiveData<String>{
        return orderMaterialsStoreData
    }

    fun storeOrderMaterials(order_id:String,json_materials_list:String,task:String,token:String){
        idsgService.storeOrderMaterials(order_id,json_materials_list,task,token)
            .enqueue(object: Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response!=null){
                        orderMaterialsStoreData.postValue(response.body())
                        //orderMaterialsData.postValue(null)

                    }else{
                        orderMaterialsStoreData.postValue(null)
                    }

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    orderMaterialsStoreData.postValue(null)
                    Log.d("ERROR-DSG",t.localizedMessage)
                }

            })
    }


    fun getOrderMaterials(order_id:String,user_id:String,task:String,token:String){
        idsgService.getOrderUserMaterials(order_id, user_id, task, token)
            .enqueue(object: Callback<OrderUserMaterial> {
                override fun onResponse(call: Call<OrderUserMaterial>, response: Response<OrderUserMaterial>) {
                    if(response!=null){
                        orderMaterialsData.postValue(response.body())
                        //orderMaterialsData.postValue(null)

                    }else{
                        orderMaterialsData.postValue(null)
                    }

                }

                override fun onFailure(call: Call<OrderUserMaterial>, t: Throwable) {
                    orderMaterialsData.postValue(null)
                    Log.d("ERROR-DSG",t.localizedMessage)
                }

            })
    }

}