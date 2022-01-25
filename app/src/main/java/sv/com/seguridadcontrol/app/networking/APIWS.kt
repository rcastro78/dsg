package sv.com.seguridadcontrol.app.networking

class APIWS {
    companion object {
        private const val WEBSERVICE_TECNICOS_URL = "http://3.236.154.247/web/Webservice_App_android/"

        fun getTecnicosService(): IDSGTecnicosService? {
            return RetrofitWSClient.getClient(WEBSERVICE_TECNICOS_URL)?.create(IDSGTecnicosService::class.java)
        }

    }
}