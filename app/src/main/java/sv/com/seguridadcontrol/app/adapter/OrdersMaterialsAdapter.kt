package sv.com.seguridadcontrol.app.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.modelos.OrderMaterials

class OrdersMaterialsAdapter (var lstMateriales:ArrayList<OrderMaterials>):
    RecyclerView.Adapter<OrdersMaterialsAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //var nomVehicle : TextView = view.findViewById(R.id.lbl_nombre_vehiculo)
        //val nomMake : TextView = view.findViewById(R.id.lbl_make_vehicle)

        val txtOrdenMaterial  : TextView = view.findViewById(R.id.txtOrdenMaterial)
        val txtOrdenMaterialCantidad  : TextView = view.findViewById(R.id.txtOrdenMaterialCantidad)
        val txtOrdenMaterialUnidad  : TextView = view.findViewById(R.id.txtOrdenMaterialUnidad)



    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_orden_materiales, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val m = lstMateriales[position]
        /*Glide.with(holder.imgBeast.getContext()).load(c.pkImage)
                .placeholder(R.drawable.pokeball01)
                .error(R.drawable.pokeball01)
                .circleCrop()
                .into(holder.imgBeast)*/
        //holder.nomVehicle.text=v.vendor_vehicle_model
        //holder.nomMake.text=v.vendor_vehicle_make
        val tf = Typeface.createFromAsset(holder.txtOrdenMaterialUnidad.context.getAssets(),"fonts/Nunito-Regular.ttf")


        holder.txtOrdenMaterial.text = m.materials
        holder.txtOrdenMaterialCantidad.text = m.materials_quantity
        holder.txtOrdenMaterialUnidad.text = m.unity

        holder.txtOrdenMaterial.typeface = tf
        holder.txtOrdenMaterialCantidad.typeface = tf
        holder.txtOrdenMaterialUnidad.typeface = tf





    }

    override fun getItemCount(): Int {
        return lstMateriales.size
    }

}
