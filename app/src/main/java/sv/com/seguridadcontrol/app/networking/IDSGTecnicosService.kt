package sv.com.seguridadcontrol.app.networking

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import sv.com.seguridadcontrol.app.modelos.*

interface IDSGTecnicosService {

    @FormUrlEncoded
    @POST("index.php")
    fun iniciarSesion(@Field("usr") usr:String,@Field("pwd") pwd:String): Call<Login>

    @FormUrlEncoded
    @POST("index.php")
    fun getTicketProvisioning(@Field("user_id") userId:String,
                              @Field("task") task:String,
                              @Field("token") token:String): Call<TicketProvisioning>

    @FormUrlEncoded
    @POST("index.php")
    fun getArticleProvisioning(@Field("user_id") userId:String,
                               @Field("task") task:String,
                               @Field("token") token:String): Call<Article>



    @FormUrlEncoded
    @POST("index.php")
    fun getOrderProvisioning(
        @Field("user_id") userId:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<OrderProvisioning>

    @FormUrlEncoded
    @POST("index.php")
    fun getOrderList(
        @Field("ticket_id") ticketId:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<Ordenes>

    @FormUrlEncoded
    @POST("index.php")
    fun getOrderVerification(
        @Field("order_id") orderId:String,
        @Field("user_id") userId:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<OrderVerification>

    @FormUrlEncoded
    @POST("index.php")
    fun getOrderUserMaterials(
        @Field("order_id") order_id:String,
        @Field("user_id") user_id:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<OrderUserMaterial>


    @FormUrlEncoded
    @POST("index.php")
    fun getOrderDetailArticle(
        @Field("order_num") order_id:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<OrderDetailArticle>

    @FormUrlEncoded
    @POST("index.php")
    fun getOrderDetailArticleDe(
        @Field("order_num") order_id:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<OrderDetailArticle>


    @FormUrlEncoded
    @POST("index.php")
    fun getTicketsList(
        @Field("user_id") userId:String,
        @Field("type") type:String,
        @Field("start") start:String,
        @Field("quantity") quantity:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<Ticket>

    @FormUrlEncoded
    @POST("index.php")
    fun getTicketDetails(
        @Field("ticket_id") ticketId:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<TicketDetail>




    @FormUrlEncoded
    @POST("index.php")
    fun startOrder(
        @Field("order_id") orderId:String,
        @Field("user_id") userId:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<String>



    @FormUrlEncoded
    @POST("index.php")
    fun storeOrderFacilities(
        @Field("order_id") orderId:String,
        @Field("type") type:String,
        @Field("facility") facility:String,
        @Field("turn") turn:String,
        @Field("battery") battery:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<String>

    @FormUrlEncoded
    @POST("index.php")
    fun storeOrderMaterials(
        @Field("order_id") orderId:String,
        @Field("json_material_list") materials: String,
        @Field("task") task:String,
        @Field("token") token:String): Call<String>

    @FormUrlEncoded
    @POST("index.php")
    fun storeUbicacionImage(
        @Field("img") img:String,
        @Field("img_type") imgType: String,
        @Field("order_id") orderId:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<String>

    @FormUrlEncoded
    @POST("index.php")
    fun storeOrderComment(
        @Field("order_id") orderId:String,
        @Field("user_id") userId:String,
        @Field("comment") comment:String,
        @Field("date") date:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<String>

    @FormUrlEncoded
    @POST("index.php")
    fun storeOrderClientName(
        @Field("order_id") orderId:String,
        @Field("client_name") client:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<ResponseBody>
}