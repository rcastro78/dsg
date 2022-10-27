package sv.com.seguridadcontrol.app

import android.app.Application
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class DSGApplication : Application() {
    val TAG: String = DSGApplication::class.java.getSimpleName()

    var mRequestQueue: RequestQueue? = null

    var mInstance: DSGApplication? = null

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    @Synchronized
    fun getInstance(): DSGApplication? {
        return mInstance
    }

    fun getRequestQueue(): RequestQueue {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext())
        }
        return mRequestQueue!!
    }

    fun <T> addToRequestQueue(req: Request<T>, tag: String?) {
        req.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        getRequestQueue().add(req)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        req.tag = TAG
        getRequestQueue().add(req)
    }

    fun cancelPendingRequests(tag: Any?) {
        if (mRequestQueue != null) {
            mRequestQueue!!.cancelAll(tag)
        }
    }


}