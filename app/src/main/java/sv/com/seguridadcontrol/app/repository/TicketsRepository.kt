package sv.com.seguridadcontrol.app.repository
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import sv.com.seguridadcontrol.app.modelos.Ticket
import sv.com.seguridadcontrol.app.networking.IDSGTecnicosService
import sv.com.seguridadcontrol.app.dao.AppDatabase
import sv.com.seguridadcontrol.app.networking.LoadingState

class TicketsRepository(private val context: Context, private val service:IDSGTecnicosService) {


    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    private val _data = MutableLiveData<Ticket>()
    val data: LiveData<Ticket>
        get() = _data


    fun getTickets(userId:String,type:String,start:String,quantity:String,task:String,token:String)
    :LiveData<Ticket>{

        var tickets = service.getTicketsList(userId, type, start, quantity, task, token) as LiveData<Ticket>

        return tickets
    }

}