package sv.com.seguridadcontrol.app.networking

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import sv.com.seguridadcontrol.app.modelos.*

interface IDSGTecnicosService {


    @FormUrlEncoded
    @POST("index.php")
    fun iniciarSesion(@Field("usr") usr:String, @Field("pwd") pwd:String): Call<Login>

    //Catalogos
    @FormUrlEncoded
    @POST("index.php")
    fun getCountries(@Field("task") task:String,
                     @Field("token") token:String):Call<Countries>

    @FormUrlEncoded
    @POST("index.php")
    fun getDepartments(@Field("task") task:String,
                       @Field("token") token:String,
                       @Field("country_id") c_id:String):Call<Departments>

    @FormUrlEncoded
    @POST("index.php")
    fun getMunicipalities(@Field("task") task:String,
                       @Field("token") token:String,
                       @Field("id_municipality") m_id:String):Call<Municipalities>

    @FormUrlEncoded
    @POST("index.php")
    fun getAdvisers(@Field("task") task:String,
                          @Field("token") token:String,
                          @Field("id_branch_office") id_branch:String):Call<Advisers>


    @FormUrlEncoded
    @POST("index.php")
    fun getBranches(@Field("task") task:String,
                    @Field("token") token:String,
                    @Field("id_branch_office") id_branch:String):Call<Branch>

    @FormUrlEncoded
    @POST("index.php")
    fun getClients(@Field("task") task:String,
                    @Field("token") token:String,
                    @Field("param") param:String,
                    @Field("id_branch_office") id_branch:String):Call<Client>


    @FormUrlEncoded
    @POST("index.php")
    fun getProspectClient(@Field("task") task:String,
                    @Field("token") token:String,
                    @Field("user_level") user_level:String,
                    @Field("user_id") user_id:String):Call<ProspectClient>
    @FormUrlEncoded
    @POST("index.php")
    fun followProspectClient(@Field("task") task:String,
                          @Field("token") token:String,
                          @Field("usuario_id") user_id:String,
                          @Field("data") data:String):Call<ProspectFollowResponse>

    @FormUrlEncoded
    @POST("index.php")
    fun getServicesClient(@Field("task") task:String,
                   @Field("token") token:String,
                   @Field("id_customer") id_customer:String):Call<ServicesClient>


    //Listado de tickets completados
//Este endpoint requiere el id del usuario y por medio de ello trae un listado con los ticket que ese usuario haya completado
    @FormUrlEncoded
    @POST("index.php")
    fun getTicketsList(
        @Field("user_id") userId:String,
        @Field("type") type:String,
        @Field("start") start:String,
        @Field("quantity") quantity:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<Ticket>


    //Tickets esperando aprobación
    @FormUrlEncoded
    @POST("index.php")
    fun getTicketsApprovalList(
        @Field("type") type:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<ApprovalTicket>


    //Aprobar ticket

    @FormUrlEncoded
    @POST("index.php")
    fun approveTicket(@Field("usuario_id") userId:String,
                      @Field("orden_solicitud") ordenSolicitud:String,
                      @Field("tipo_orden_id") tipoOrdenId:String,
                      @Field("ticket_id") ticketId:String,
                      @Field("codigo_tarea") codigoTarea:String,
                      @Field("numero_solicitud") numSolicitud:String,
                      @Field("approval_option") approvalOption:String,
                      @Field("task") task:String,
                      @Field("token") token:String): Call<ApprovalResponse>


    @FormUrlEncoded
    @POST("index.php")
    fun createProspectClient(@Field("usuario_id") userId:String,
                             @Field("task") task:String,
                             @Field("token") token:String,
                             @Field("data") data:String,
                             @Field("data_gps") dataGps:String,
                             @Field("data_cam") dataCam:String,
                             @Field("data_alar") dataAlm:String,
                             @Field("data_vig") dataVig:String,
                             @Field("data_otros") dataOtrs:String,
                             @Field("data_terms") dataTerms:String):Call<CreacionProspectoResponse>

   @FormUrlEncoded
    @POST("index.php")
    fun createPayment( @Field("task") task:String,
                       @Field("token") token:String,
                       @Field("data") data:String,
                       @Field("data_payment") data_payment:String):Call<PaymentResponse>


    //Iniciando la orden
//Se da inicio a la orden desde la app
    @FormUrlEncoded
    @POST("index.php")
    fun startOrder(
        @Field("order_id") orderId:String,
        @Field("user_id") userId:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<String>

    @FormUrlEncoded
    @POST("index.php")
    fun ticketCompleted(@Field("order_id") ticketId:String,
                          @Field("user_id") userId:String,
                          @Field("comment") comment:String,
                          @Field("task") task:String,
                          @Field("token") token:String,
                        @Field("date") date:String):Call<String>
    /*
    * Mostrando el detalle de la orden
    Este endpoint es el encargado de mostrar el detalle individual de cada orden de trabajo por medio del id de la orden
    * * */
    @FormUrlEncoded
    @POST("index.php")
    fun orderShowIndividualOrder(
        @Field("order_id") orderId:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<Order>


//Almacenando un comentario para la orden
//Agregando un comentario a la orden por medio del id de la orden al SP sp_ordenes_trabajos_completar

    @FormUrlEncoded
    @POST("index.php")
    fun storeOrderComment(
        @Field("order_id") orderId:String,
        @Field("user_id") userId:String,
        @Field("comment") comment:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<String>


    //Almacenando un comentario para la orden y finalizando la orden
//Agregando el último comentario a la orden y se finaliza la orden al mismo tiempo sp_ordenes_trabajos_completar_offline

    @FormUrlEncoded
    @POST("index.php")
    fun storeOrderCommentOffline(
        @Field("order_id") orderId:String,
        @Field("user_id") userId:String,
        @Field("comment") comment:String,
        @Field("date") date:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<String>

    //Guardando los materiales para la orden
//Se guarda el listado de materiales utilizados para la orden de trabajo sp_ordenes_trabajos_materiales_agregar
    @FormUrlEncoded
    @POST("index.php")
    fun storeOrderMaterials(
        @Field("order_id") orderId:String,
        @Field("json_material_list") materials: String,
        @Field("task") task:String,
        @Field("token") token:String): Call<String>

    //Listado de aprovisionamiento o materiales
//Mostrando el listado del aprovisionamiento de materiales según el técnico y ejecutando el SP sp_ordenes_trabajos_materiales_l
//istado
    @FormUrlEncoded
    @POST("index.php")
    fun getOrderUserMaterials(
        @Field("order_id") order_id:String,
        @Field("user_id") user_id:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<OrderUserMaterial>

//Listado de aprovisionamiento de los articulos por usuario
//TODO: falta el resultado del metodo cuando es correcto



    //Listado de aprovisionamiento
    @FormUrlEncoded
    @POST("index.php")
    fun getOrderProvisioning(
        @Field("user_id") userId:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<OrderProvisioning>


    //Agregando el nombre del cliente
    //Se realiza un update a la tabla de las ordenes con el nombre del cliente en el SP sp_ordenes_trabajos_editar_nombre_firma_c
    //liente
    @FormUrlEncoded
    @POST("index.php")
    fun storeOrderClientName(
        @Field("order_id") orderId:String,
        @Field("client_name") client:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<ResponseBody>




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
    fun getTicketDetails(
        @Field("ticket_id") ticketId:String,
        @Field("task") task:String,
        @Field("token") token:String): Call<TicketDetail>




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


}