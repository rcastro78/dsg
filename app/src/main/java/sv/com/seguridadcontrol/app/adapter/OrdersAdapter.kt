package sv.com.seguridadcontrol.app.adapter

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.erp.tecnicos.*
import sv.com.seguridadcontrol.app.modelos.Orden
import sv.com.seguridadcontrol.app.modelos.Ordenes
import sv.com.seguridadcontrol.app.modelos.Order
import sv.com.seguridadcontrol.app.utilities.CircularTextView

class OrdersAdapter(private var ordersList:ArrayList<Orden>):
    RecyclerView.Adapter<OrdersAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btnOrderDetails : Button = view.findViewById(R.id.btnOrderDetails)
        val txtCodigoOrden  : TextView = view.findViewById(R.id.txtCodigoOrden)
        val txtOrderNum2  : CircularTextView = view.findViewById(R.id.txtOrderNum2)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_2, parent, false)
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
        val tf = Typeface.createFromAsset(holder.txtCodigoOrden.context.getAssets(),"fonts/Nunito-Regular.ttf")
        val tfb = Typeface.createFromAsset(holder.txtCodigoOrden.context.getAssets(),"fonts/Nunito-Bold.ttf")


        holder.txtCodigoOrden.text = o.order_id
        holder.txtOrderNum2.text = o.order_num

        holder.txtOrderNum2.setStrokeWidth(1)
        holder.txtOrderNum2.setStrokeColor(o.order_color)
        holder.txtOrderNum2.setSolidColor("#FFFFFF")


        holder.txtCodigoOrden.typeface = tf
        holder.txtOrderNum2.typeface = tfb
        /*holder.txtOrderId.typeface = tf
        holder.txtOrderProgress.typeface = tf
        holder.txtOrderDate.typeface = tf
        holder.txtOrderStart.typeface = tf
        holder.txtOrderEnd.typeface = tf*/

        /*holder.btnArticles.setOnClickListener {

            val intent = Intent(it.context, OrdenDetalleArticuloActivity::class.java)
            intent.putExtra("orderId",o.order_id)
            it.context.startActivity(intent)
        }*/

        holder.btnOrderDetails.setOnClickListener {
            /*val intent = Intent(it.context, OrdersActivity::class.java)
            intent.putExtra("ticketId",t.ticket_id)
            it.context.startActivity(intent)*/
            //Toast.makeText(it.context,"Id de la orden: (Mat.)"+o.order_id,Toast.LENGTH_LONG).show()
            val intent = Intent(it.context, Orders2Activity::class.java)
            intent.putExtra("orderId",o.order_id)
            intent.putExtra("orderNum",o.order_num)
            intent.putExtra("orderStart",o.order_start)
            intent.putExtra("orderEnd",o.order_end)

            it.context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

}
