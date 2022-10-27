package sv.com.seguridadcontrol.app.dao

import androidx.room.*

@Entity(tableName=OrdenesDAO.TABLE_NAME)
class OrdenesDAO(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "orden_id")  val orden_id: String,
    @ColumnInfo(name = "ticket_id")  val ticket_id: String,
    @ColumnInfo(name = "progress")  val progress: String,
    @ColumnInfo(name = "order_date")  val order_date: String,
    @ColumnInfo(name = "order_start")  val order_start: String,
    @ColumnInfo(name = "order_end")  val order_end: String,
    @ColumnInfo(name = "order_color")  val order_color: String
    ) {
    companion object {
        const val TABLE_NAME = "ordenes_ticket"
    }
    @Dao
    interface IOrdenesDAO{
        @Query("SELECT * FROM $TABLE_NAME WHERE ticket_id=:ticket_id")
        fun getOrdenesTicket(ticket_id: String?): List<OrdenesDAO>
        @Insert(onConflict=OnConflictStrategy.REPLACE)
        fun insert(orden:OrdenesDAO)
        @Query("DELETE FROM $TABLE_NAME")
        fun delete()
    }
}
