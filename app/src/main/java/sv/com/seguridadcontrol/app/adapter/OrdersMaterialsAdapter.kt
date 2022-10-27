package sv.com.seguridadcontrol.app.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.dao.AppDatabase
import sv.com.seguridadcontrol.app.erp.tecnicos.OrdenMaterialesActivity
import sv.com.seguridadcontrol.app.modelos.OrderMaterials

class OrdersMaterialsAdapter (var lstMateriales:ArrayList<OrderMaterials>,var orderId:String,var c:Context):
    RecyclerView.Adapter<OrdersMaterialsAdapter.ViewHolder>(){
    private lateinit var db: AppDatabase

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //var nomVehicle : TextView = view.findViewById(R.id.lbl_nombre_vehiculo)
        //val nomMake : TextView = view.findViewById(R.id.lbl_make_vehicle)

        val txtOrdenMaterial  : TextView = view.findViewById(R.id.txtOrdenMaterial)
        val txtOrdenMaterialCantidad  : TextView = view.findViewById(R.id.txtOrdenMaterialCantidad)
        val txtOrdenMaterialUnidad  : TextView = view.findViewById(R.id.txtOrdenMaterialUnidad)
        val imbDelete:ImageView  = view.findViewById(R.id.imbDelete)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_orden_materiales, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val m = lstMateriales[position]
        val tf = Typeface.createFromAsset(holder.txtOrdenMaterialUnidad.context.getAssets(),"fonts/Nunito-Regular.ttf")


        holder.txtOrdenMaterial.text = m.materials
        holder.txtOrdenMaterialCantidad.text = m.materials_quantity
        holder.txtOrdenMaterialUnidad.text = m.unity

        holder.txtOrdenMaterial.typeface = tf
        holder.txtOrdenMaterialCantidad.typeface = tf
        holder.txtOrdenMaterialUnidad.typeface = tf

        holder.imbDelete.setOnClickListener {
            db = AppDatabase.getInstance(c)
            CoroutineScope(Dispatchers.IO).launch {
                db.iMaterialesDAO.delete(orderId,m.materials_id)
            }
            //eliminar item de una lista
            lstMateriales.removeAt(position)
            notifyItemRemoved(position)
       }
    }

    override fun getItemCount(): Int {
        return lstMateriales.size
    }

}
