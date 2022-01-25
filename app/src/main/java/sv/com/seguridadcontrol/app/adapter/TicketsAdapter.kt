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
import sv.com.seguridadcontrol.app.erp.tecnicos.OrdersActivity
import sv.com.seguridadcontrol.app.modelos.MaterialArticle
import sv.com.seguridadcontrol.app.modelos.TicketData

class TicketsAdapter (var ticketsList:ArrayList<TicketData>):
    RecyclerView.Adapter<TicketsAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgPriority  : ImageView = view.findViewById(R.id.imgPriority)
        val txtTicketPriority  : TextView = view.findViewById(R.id.txtTicketPriority)
        val txtClient  : TextView = view.findViewById(R.id.txtClient)
        val txtEstadoTicket  : TextView = view.findViewById(R.id.txtEstadoTicket)
        val txtProgrammingDate  : TextView = view.findViewById(R.id.txtProgrammingDate)
        val txtTicketType  : TextView = view.findViewById(R.id.txtTicketType)
        val rlHeader : RelativeLayout = view.findViewById(R.id.rlHeader)
        val llBackgroundTicket : LinearLayout = view.findViewById(R.id.llBackgroundTicket)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ticket, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val t = ticketsList[position]
        /*Glide.with(holder.imgBeast.getContext()).load(c.pkImage)
                .placeholder(R.drawable.pokeball01)
                .error(R.drawable.pokeball01)
                .circleCrop()
                .into(holder.imgBeast)*/
        //holder.nomVehicle.text=v.vendor_vehicle_model
        //holder.nomMake.text=v.vendor_vehicle_make
        val tf = Typeface.createFromAsset(holder.rlHeader.context.getAssets(),"fonts/Nunito-Regular.ttf")
        val tfbold = Typeface.createFromAsset(holder.rlHeader.context.getAssets(),"fonts/Nunito-Bold.ttf")


        //holder.txtTicketCode.text = t.ticket_code
        //holder.txtTicketProgress.text = t.ticket_progress
        holder.txtTicketPriority.text = t.ticket_priority
        holder.txtClient.text = t.client_reason+" "+t.client_branch
        holder.txtEstadoTicket.text = "Programada"
        holder.txtProgrammingDate.text = t.programming_date
        holder.txtTicketType.text = t.ticket_code
        holder.rlHeader.setBackgroundColor(Color.parseColor(t.ticket_color_type))
        holder.imgPriority.setColorFilter(Color.parseColor(t.priority_color))


        holder.txtEstadoTicket.typeface = tf
        //holder.txtTicketProgress.typeface = tf
        holder.txtTicketPriority.typeface = tf
        holder.txtClient.typeface = tf
        //holder.txtTicketService.typeface = tf
        holder.txtProgrammingDate.typeface = tf
        holder.txtTicketType.typeface = tfbold

        holder.llBackgroundTicket.setOnClickListener {
            val intent = Intent(it.context, OrdersActivity::class.java)
            intent.putExtra("ticketId",t.ticket_id)
            intent.putExtra("ticketCod",t.ticket_code)
            intent.putExtra("ticketColor",t.ticket_color_type)
            it.context.startActivity(intent)
            /*
            *  val intent = Intent(this@MainActivity,TicketsHoyActivity::class.java)
            startActivity(intent)
            * */

        }


    }

    override fun getItemCount(): Int {
        return ticketsList.size
    }

}
