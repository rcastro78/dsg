package sv.com.seguridadcontrol.app.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.modelos.Unit

class OrdersDetailArticleAdapter  (var lstArticulos:ArrayList<Unit>):
    RecyclerView.Adapter<OrdersDetailArticleAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //var nomVehicle : TextView = view.findViewById(R.id.lbl_nombre_vehiculo)
        //val nomMake : TextView = view.findViewById(R.id.lbl_make_vehicle)

        val txtOrdenDetalleArticulo  : TextView = view.findViewById(R.id.txtOrdenDetalleArticulo)
        val txtOrdenCategoriaArticulo  : TextView = view.findViewById(R.id.txtOrdenCategoriaArticulo)
        val txtOrdenProductoArticulo  : TextView = view.findViewById(R.id.txtOrdenProductoArticulo)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_detail_order_article, parent, false)
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
        val tf = Typeface.createFromAsset(holder.txtOrdenDetalleArticulo.context.getAssets(),"fonts/Nunito-Regular.ttf")


        holder.txtOrdenDetalleArticulo.text = m.article
        holder.txtOrdenCategoriaArticulo.text = m.category
        holder.txtOrdenProductoArticulo.text = m.product



        holder.txtOrdenDetalleArticulo.typeface = tf
        holder.txtOrdenCategoriaArticulo.typeface = tf
        holder.txtOrdenProductoArticulo.typeface = tf





    }

    override fun getItemCount(): Int {
        return lstArticulos.size
    }

}
