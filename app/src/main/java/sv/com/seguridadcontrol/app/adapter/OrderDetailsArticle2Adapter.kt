package sv.com.seguridadcontrol.app.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.modelos.Unit

class OrderDetailsArticle2Adapter  (var lstArticulos:ArrayList<Unit>):
    RecyclerView.Adapter<OrderDetailsArticle2Adapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //var nomVehicle : TextView = view.findViewById(R.id.lbl_nombre_vehiculo)
        //val nomMake : TextView = view.findViewById(R.id.lbl_make_vehicle)

        val txtProduct  : TextView = view.findViewById(R.id.txtProduct)
        val txtCategory  : TextView = view.findViewById(R.id.txtCategory)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_articulo_orden, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val m = lstArticulos[position]
        /*Glide.with(holder.imgBeast.getContext()).load(c.pkImage)
                .placeholder(R.drawable.pokeball01)
                .error(R.drawable.pokeball01)
                .circleCrop()
                .into(holder.imgBeast)*/
        //holder.nomVehicle.text=v.vendor_vehicle_model
        //holder.nomMake.text=v.vendor_vehicle_make
        val tf = Typeface.createFromAsset(holder.txtCategory.context.getAssets(),"fonts/Nunito-Regular.ttf")



        holder.txtProduct.text = m.product + " - articulo:" + m.article
        holder.txtCategory.text = m.category



        holder.txtProduct.typeface = tf
        holder.txtCategory.typeface = tf





    }

    override fun getItemCount(): Int {
        return lstArticulos.size
    }

}
