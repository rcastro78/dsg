package sv.com.seguridadcontrol.app.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import sv.com.seguridadcontrol.app.modelos.Usuario

interface IGPSService {

    @GET("dsg_web_services.php")
    fun getUserData(@Query("usr") usr: String,@Query("pwd") pwd: String): Call<Usuario>

}