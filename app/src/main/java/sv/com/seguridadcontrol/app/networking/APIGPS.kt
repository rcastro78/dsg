package sv.com.seguridadcontrol.app.networking

class APIGPS {
    companion object {
        private const val WEBSERVICE_GPS_URL = "http://webservices.proteccionycontrol.com/"
        fun getGPSService(): IGPSService? {
            return RetrofitGPSClient.getClient(WEBSERVICE_GPS_URL)?.create(IGPSService::class.java)
        }

    }
}