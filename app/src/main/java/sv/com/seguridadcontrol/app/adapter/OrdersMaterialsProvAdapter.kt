package sv.com.seguridadcontrol.app.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sv.com.seguridadcontrol.app.R

import sv.com.seguridadcontrol.app.modelos.ProvisionamientoMateriales

class OrdersMaterialsProvAdapter(var ordersList:ArrayList<ProvisionamientoMateriales>):
    RecyclerView.Adapter<OrdersMaterialsProvAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //var nomVehicle : TextView = view.findViewById(R.id.lbl_nombre_vehiculo)
        //val nomMake : TextView = view.findViewById(R.id.lbl_make_vehicle)

        val txtMaterial  : TextView = view.findViewById(R.id.txtMaterial)
        val txtMarca  : TextView = view.findViewById(R.id.txtMarca)
        val txtCantidad  : TextView = view.findViewById(R.id.txtCantidad)
        val txtUM  : TextView = view.findViewById(R.id.txtUM)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_prov_material, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val o = ordersList[position]
        /*Glide.with(holder.imgBeast.getContext()).load(c.pkImage)
                .placeholder(R.drawable.pokeball01)
                .error(R.drawable.pokeball01)
                .circleCrop()
                .into(holder.imgBeast)*/
        //holder.nomVehicle.text=v.vendor_vehicle_model
        //holder.nomMake.text=v.vendor_vehicle_make
        val tf = Typeface.createFromAsset(holder.txtUM.context.getAssets(),"fonts/Nunito-Regular.ttf")


        holder.txtMaterial.text = o.product
        holder.txtMarca.text = o.brand
        holder.txtCantidad.text = o.quantity
        holder.txtUM.text = o.unity


        holder.txtMaterial.typeface = tf
        holder.txtMarca.typeface = tf
        holder.txtCantidad.typeface = tf
        holder.txtUM.typeface = tf




    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

}
