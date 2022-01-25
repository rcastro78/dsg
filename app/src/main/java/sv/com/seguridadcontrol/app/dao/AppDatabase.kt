package sv.com.seguridadcontrol.app.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import sv.com.seguridadcontrol.app.modelos.TicketData

@Database(entities = arrayOf(MaterialesDAO::class,UsuarioDAO::class, TicketData::class), version = 1)

abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "dsg00a.db"
                )
                    .build()
            }
            return INSTANCE as AppDatabase
        }
    }

    abstract val iMaterialesDAO: MaterialesDAO.IMaterialesDAO
    abstract val iUsuarioDAO:UsuarioDAO.IUsuarioDAO
    abstract val iTicketDAO:TicketDAO.ITicketDAO
}