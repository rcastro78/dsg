package sv.com.seguridadcontrol.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.com.seguridadcontrol.app.modelos.Article
import sv.com.seguridadcontrol.app.networking.APIWS
import sv.com.seguridadcontrol.app.networking.IDSGTecnicosService

class ArticleProvisioningViewModel:ViewModel() {
    var articleProvisioningData: MutableLiveData<Article>
    private var idsgService: IDSGTecnicosService
    init {
        articleProvisioningData = MutableLiveData()
        idsgService = APIWS.getTecnicosService()!!
    }
    fun getArticleProvisioningResultObserver(): MutableLiveData<Article> {
        return articleProvisioningData
    }

    fun getArticles(user_id:String,task:String,token:String){
        idsgService.getArticleProvisioning(user_id,task,token)
            .enqueue(object: Callback<Article> {
                override fun onResponse(call: Call<Article>, response: Response<Article>) {
                    if(response!=null){
                        articleProvisioningData.postValue(response.body())
                    }

                }

                override fun onFailure(call: Call<Article>, t: Throwable) {
                    articleProvisioningData.postValue(null)
                    Log.d("ERROR-DSG",t.localizedMessage)
                }

            })
    }


}