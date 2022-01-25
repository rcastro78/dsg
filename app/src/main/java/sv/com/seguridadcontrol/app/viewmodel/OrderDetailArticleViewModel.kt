package sv.com.seguridadcontrol.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.com.seguridadcontrol.app.modelos.OrderDetailArticle
import sv.com.seguridadcontrol.app.modelos.OrderUserMaterial
import sv.com.seguridadcontrol.app.networking.APIWS
import sv.com.seguridadcontrol.app.networking.IDSGTecnicosService
import java.util.*

class OrderDetailArticleViewModel:ViewModel() {
    private var idsgService: IDSGTecnicosService
    private var orderDetailArticlesData : MutableLiveData<OrderDetailArticle>
    private var orderDetailArticlesDeData : MutableLiveData<OrderDetailArticle>
    init {
        orderDetailArticlesData = MutableLiveData()
        orderDetailArticlesDeData = MutableLiveData()
        idsgService = APIWS.getTecnicosService()!!
    }

    fun getOrderDetailArticlesObserver(): MutableLiveData<OrderDetailArticle> {
        return orderDetailArticlesData
    }
    fun getOrderDetailArticlesDeObserver(): MutableLiveData<OrderDetailArticle> {
        return orderDetailArticlesDeData
    }


    fun getOrderDetailArticles(order_num:String,task:String,token:String){
        idsgService.getOrderDetailArticle(order_num, task, token)
            .enqueue(object: Callback<OrderDetailArticle> {
                override fun onResponse(call: Call<OrderDetailArticle>, response: Response<OrderDetailArticle>) {
                    if(response!=null){
                        orderDetailArticlesData.postValue(response.body())
                        //orderMaterialsData.postValue(null)

                    }else{
                        orderDetailArticlesData.postValue(null)
                    }

                }

                override fun onFailure(call: Call<OrderDetailArticle>, t: Throwable) {
                    orderDetailArticlesData.postValue(null)
                    Log.d("ERROR-DSG",t.localizedMessage)
                }

            })
    }
    fun getOrderDetailArticlesDe(order_num:String,task:String,token:String){
        idsgService.getOrderDetailArticleDe(order_num, task, token)
            .enqueue(object: Callback<OrderDetailArticle> {
                override fun onResponse(call: Call<OrderDetailArticle>, response: Response<OrderDetailArticle>) {
                    if(response!=null){
                        orderDetailArticlesDeData.postValue(response.body())
                        //orderMaterialsData.postValue(null)

                    }else{
                        orderDetailArticlesDeData.postValue(null)
                    }

                }

                override fun onFailure(call: Call<OrderDetailArticle>, t: Throwable) {
                    orderDetailArticlesData.postValue(null)
                    Log.d("ERROR-DSG",t.localizedMessage)
                }

            })
    }

}