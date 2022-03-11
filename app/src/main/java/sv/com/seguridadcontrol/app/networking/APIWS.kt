package sv.com.seguridadcontrol.app.networking

class APIWS {
    companion object {
        private const val WEBSERVICE_TECNICOS_URL = "http://54.83.96.193/web/Webservice_App_android/"

        fun getTecnicosService(): IDSGTecnicosService? {
            return RetrofitWSClient.getClient(WEBSERVICE_TECNICOS_URL)?.create(IDSGTecnicosService::class.java)
        }

    }
}