package sv.com.seguridadcontrol.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import sv.com.seguridadcontrol.app.networking.APIWS
import sv.com.seguridadcontrol.app.networking.IDSGTecnicosService
import retrofit2.Callback
import retrofit2.Response
import sv.com.seguridadcontrol.app.modelos.*

class CatalogViewModel: ViewModel() {
    var countriesData: MutableLiveData<Countries>
    var departmentsData: MutableLiveData<Departments>
    var municipalitiesData: MutableLiveData<Municipalities>
    var advisersData: MutableLiveData<Advisers>
    private var idsgService: IDSGTecnicosService

    init {
        countriesData = MutableLiveData()
        departmentsData = MutableLiveData()
        municipalitiesData = MutableLiveData()
        advisersData = MutableLiveData()
        idsgService = APIWS.getTecnicosService()!!
    }

    fun countriesObserver(): MutableLiveData<Countries> {
        return countriesData
    }
    fun departmentsObserver(): MutableLiveData<Departments> {
        return departmentsData
    }
    fun municipalitiesObserver(): MutableLiveData<Municipalities> {
        return municipalitiesData
    }
    fun advisersObserver(): MutableLiveData<Advisers> {
        return advisersData
    }

    fun getPaises(task:String,token:String){
        idsgService.getCountries(task,token)
            .enqueue(object:Callback<Countries>{
                override fun onResponse(call: Call<Countries>, response: Response<Countries>) {
                    countriesData.postValue(response.body())
                }

                override fun onFailure(call: Call<Countries>, t: Throwable) {
                    countriesData.postValue(null)
                }

            })
    }



    fun clearDepartments(){
            if(departmentsData.hasObservers())
                departmentsData.value=null
    }

    fun getDepartamentos(task:String,token:String,cid:String){

        idsgService.getDepartments(task,token,cid)
            .enqueue(object:Callback<Departments>{
                override fun onResponse(call: Call<Departments>, response: Response<Departments>) {
                    departmentsData.postValue(response.body())
                }
                override fun onFailure(call: Call<Departments>, t: Throwable) {
                    departmentsData.postValue(null)
                }
            })
    }



    fun getMunicipios(task:String,token:String,mid:String){
        idsgService.getMunicipalities(task,token,mid)
            .enqueue(object:Callback<Municipalities>{
                override fun onResponse(call: Call<Municipalities>, response: Response<Municipalities>) {
                    municipalitiesData.postValue(response.body())
                }

                override fun onFailure(call: Call<Municipalities>, t: Throwable) {
                    municipalitiesData.postValue(null)
                }

            })
    }


    fun getAdvisers(task:String,token:String,id_branch:String){
        idsgService.getAdvisers(task,token,id_branch)
            .enqueue(object:Callback<Advisers>{
                override fun onResponse(call: Call<Advisers>, response: Response<Advisers>) {
                    advisersData.postValue(response.body())
                }

                override fun onFailure(call: Call<Advisers>, t: Throwable) {
                    advisersData.postValue(null)
                }

            })
    }


}