package sv.com.seguridadcontrol.app.dao

import androidx.room.*
import sv.com.seguridadcontrol.app.modelos.Ticket
import sv.com.seguridadcontrol.app.modelos.TicketData
class TicketDAO{
    companion object {
        const val TABLE_NAME = "ticket"
    }
    @Dao
    interface ITicketDAO{

        @Query("SELECT * FROM $TABLE_NAME")
        fun getTickets(): List<TicketData>
        @Insert(onConflict=OnConflictStrategy.REPLACE)
        fun insert(ticket:TicketData)
        @Query("DELETE FROM $TABLE_NAME")
        fun delete()

    }
}