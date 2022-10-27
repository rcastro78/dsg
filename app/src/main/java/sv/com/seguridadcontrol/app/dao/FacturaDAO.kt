package sv.com.seguridadcontrol.app.dao

import androidx.room.*

@Entity(tableName = FacturaDAO.TABLE_NAME)
class FacturaDAO(@PrimaryKey(autoGenerate = true) val uid: Int,
                 @ColumnInfo(name = "num_factura")  val num_factura: String,
                 @ColumnInfo(name = "monto")  val monto: String,
                 @ColumnInfo(name = "fecha")  val fecha: String,
                 @ColumnInfo(name = "forma_pago")  val forma_pago: Int,
                 @ColumnInfo(name = "procesada")  val procesada: Int,
                 @ColumnInfo(name = "empresa")  val empresa: Int,
                 @ColumnInfo(name = "cliente")  val cliente: Int,
                 @ColumnInfo(name = "servicio")  val servicio: Int) {


    companion object {
        const val TABLE_NAME = "facturas"
    }
@Dao
    interface IFacturaDAO{
        @Query("SELECT * FROM $TABLE_NAME WHERE cliente=:cliente AND empresa=:empresa AND procesada=0")
        fun getFacturas(cliente: Int?,empresa:Int?): List<FacturaDAO>
        @Query("INSERT INTO $TABLE_NAME(num_factura,monto,fecha,forma_pago,procesada,empresa,cliente,servicio) VALUES(:num_factura,:monto,:fecha," +
                ":forma_pago,:procesada,:empresa,:cliente,:servicio)")
        fun guardarFactura(num_factura: String,monto: String,fecha: String,forma_pago:Int?,procesada:Int?,empresa:Int?,cliente:Int?,servicio:Int?)
        @Query("DELETE FROM $TABLE_NAME WHERE num_factura=:num_factura")
        fun delete(num_factura: String)
    }

}