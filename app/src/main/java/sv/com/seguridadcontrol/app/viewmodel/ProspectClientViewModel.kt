package sv.com.seguridadcontrol.app.viewmodel
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.com.seguridadcontrol.app.modelos.CreacionProspectoResponse
import sv.com.seguridadcontrol.app.modelos.ProspectClient
import sv.com.seguridadcontrol.app.modelos.ProspectFollowResponse
import sv.com.seguridadcontrol.app.networking.APIWS
import sv.com.seguridadcontrol.app.networking.IDSGTecnicosService

class ProspectClientViewModel:ViewModel() {
    private var idsgService: IDSGTecnicosService
    private var prospectClientData: MutableLiveData<CreacionProspectoResponse>
    private var prospectClients: MutableLiveData<ProspectClient>
    private var followResponse: MutableLiveData<ProspectFollowResponse>
    init {
        prospectClientData= MutableLiveData()
        prospectClients = MutableLiveData()
        followResponse = MutableLiveData()
        idsgService = APIWS.getTecnicosService()!!
    }
    fun createProspectClientObserver():MutableLiveData<CreacionProspectoResponse>{
        return prospectClientData
    }
    fun getProspectClientsObserver():MutableLiveData<ProspectClient>{
        return prospectClients
    }
    fun followResponseObserver():MutableLiveData<ProspectFollowResponse>{
        return followResponse
    }

    fun  followProspectClient(task:String,
                              token:String,
                              usuario_id:String,
                              data: String){
        idsgService.followProspectClient(task, token, usuario_id, data)
            .enqueue(object: Callback<ProspectFollowResponse> {
                override fun onResponse(call: Call<ProspectFollowResponse>, response: Response<ProspectFollowResponse>) {
                    Log.d("PROSPECTO",response.toString())
                    if(response!=null){
                        followResponse.postValue(response.body())
                    }else{
                        Log.d("PROSPECTO","response is null")
                    }


                }

                override fun onFailure(call: Call<ProspectFollowResponse>, t: Throwable) {
                    followResponse.postValue(null)
                    Log.d("PROSPECTO",t.localizedMessage)
                }

            })
    }

    fun getProspectClients(task:String,
                           token:String,
                           user_level:String,
                           user_id:String){

        idsgService.getProspectClient(task, token, user_level, user_id)
            .enqueue(object: Callback<ProspectClient> {
                override fun onResponse(call: Call<ProspectClient>, response: Response<ProspectClient>) {
                    if(response!=null){
                        prospectClients.postValue(response.body())
                    }else{
                        Log.d("PROSPECTO","response is null")
                    }


                }

                override fun onFailure(call: Call<ProspectClient>, t: Throwable) {
                    prospectClientData.postValue(null)
                    Log.d("PROSPECTO",t.localizedMessage)
                }

            })


    }

    fun createProspectClient(userId:String,
                             task:String,
                             token:String,
                             data:String,
                             dataGps:String,
                             dataCam:String,
                             dataAlm:String,
                             dataVig:String,
                             dataOtrs:String,
                             dataTerms:String){

        idsgService.createProspectClient(userId, task, token, data, dataGps, dataCam,
            dataAlm, dataVig, dataOtrs, dataTerms)
            .enqueue(object: Callback<CreacionProspectoResponse> {
                override fun onResponse(call: Call<CreacionProspectoResponse>, response: Response<CreacionProspectoResponse>) {
                    if(response!=null){
                        prospectClientData.postValue(response.body())
                    }else{
                        Log.d("QUIERE_PROSPECTO","response is null")
                    }


                }

                override fun onFailure(call: Call<CreacionProspectoResponse>, t: Throwable) {
                    prospectClientData.postValue(null)
                    Log.d("PROSPECTO",t.localizedMessage)
                }

            })


    }

}