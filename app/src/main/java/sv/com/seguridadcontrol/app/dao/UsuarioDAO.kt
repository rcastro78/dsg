package sv.com.seguridadcontrol.app.dao

import androidx.room.*

@Entity(tableName = UsuarioDAO.TABLE_NAME)
class UsuarioDAO(
    @PrimaryKey val id_usuario: Int,
    @ColumnInfo(name = "tipo_usuario")  val tipo_usuario: Int,
    @ColumnInfo(name = "usuario") val usuario: String,
    @ColumnInfo(name = "token") val token: String?,
) {
    companion object {
        const val TABLE_NAME = "usuarios"
    }

    @Dao
    interface IUsuarioDAO{
        @Query("SELECT * FROM $TABLE_NAME WHERE usuario=:usuario")
        fun login(usuario: String):List<UsuarioDAO>
        @Query("INSERT INTO $TABLE_NAME(id_usuario,tipo_usuario,usuario,token) VALUES(:id_usuario,:tipo_usuario,:usuario,:token)")
        fun insert(id_usuario: Int,tipo_usuario:Int,usuario: String,token: String)

    }

}