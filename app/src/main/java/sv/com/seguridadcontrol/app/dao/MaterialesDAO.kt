package sv.com.seguridadcontrol.app.dao

import androidx.annotation.NonNull
import androidx.room.*

//,primaryKeys = ["orden_id", "id_material"]

@Entity(tableName = MaterialesDAO.TABLE_NAME)
class MaterialesDAO(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "orden_id")  val orden_id: String,
    @ColumnInfo(name = "id_material") val id_material: String,
    @ColumnInfo(name = "nombre_material") val nombre_material: String?,
    @ColumnInfo(name = "cantidad") val cantidad: String?,
    @ColumnInfo(name = "unidad") val unidad: String?,
)
{
    companion object {
        const val TABLE_NAME = "orden_materiales"
    }

    @Dao
    interface IMaterialesDAO{
        @Query("SELECT * FROM $TABLE_NAME WHERE orden_id=:orden_id")
        fun getMateriales(orden_id: String?): List<MaterialesDAO>
        @Query("SELECT * FROM $TABLE_NAME WHERE orden_id=:orden_id AND id_material=:id_material")
        fun getMaterialOrden(orden_id: String?,id_material:String?): List<MaterialesDAO>
        @Query("INSERT INTO $TABLE_NAME(orden_id,id_material,nombre_material,cantidad,unidad) VALUES(:orden_id,:id_material,:nombre_material,:cantidad,:unidad)")
        fun insert(orden_id:String,id_material:String,nombre_material:String,cantidad:String,unidad:String)
        @Query("UPDATE $TABLE_NAME SET cantidad=:cantidad WHERE orden_id=:orden_id AND id_material=:id_material")
        fun edit(orden_id:String,id_material:String,cantidad:String)
        @Query("DELETE FROM $TABLE_NAME WHERE orden_id=:orden_id AND id_material=:id_material")
        fun delete(orden_id:String,id_material:String)

    }
}