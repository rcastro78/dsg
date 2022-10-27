package sv.com.seguridadcontrol.app.adapter

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.erp.autorizaciones.AuthTicketDetailActivity
import sv.com.seguridadcontrol.app.erp.autorizaciones.AutorizacionesActivity
import sv.com.seguridadcontrol.app.erp.tecnicos.OrdersActivity
import sv.com.seguridadcontrol.app.modelos.MaterialArticle
import sv.com.seguridadcontrol.app.modelos.TicketApprovRequest
import sv.com.seguridadcontrol.app.modelos.TicketData
import sv.com.seguridadcontrol.app.viewmodel.TicketsViewModel

class TicketsAuthAdapter (var ticketsList:ArrayList<TicketApprovRequest>, token: String):
    RecyclerView.Adapter<TicketsAuthAdapter.ViewHolder>(){
    var tk:String = token
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtTicketPriority  : TextView = view.findViewById(R.id.txtTicketPriority)
        val txtClient  : TextView = view.findViewById(R.id.txtClient)
        val txtEstadoTicket  : TextView = view.findViewById(R.id.txtEstadoTicket)
        val txtProgrammingDate  : TextView = view.findViewById(R.id.txtProgrammingDate)
        val txtTicketType  : TextView = view.findViewById(R.id.txtTicketCod)
        val txtTicketTipo  : TextView = view.findViewById(R.id.txtTicketTipo)
        val rlHeader : RelativeLayout = view.findViewById(R.id.rlHeader)
        val llBackgroundTicket : LinearLayout = view.findViewById(R.id.llBackgroundTicket)
        val btnDetalle:RelativeLayout = view.findViewById(R.id.btnDetalle)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ticket_auth, parent, false)
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
        //holder.txtTicketPriority.text = t.ticket_priority
        holder.txtClient.text = t.client_branch

            holder.txtEstadoTicket.text = "Esperando aprobación"



        holder.txtProgrammingDate.text = t.programming_date
        holder.txtTicketType.text = t.service_cod+"-"+t.ticket_type_cod+"-"+t.ticket_id
        holder.rlHeader.setBackgroundColor(Color.parseColor(t.ticket_color_type))

        holder.txtTicketTipo.text = t.ticket_type
        if(t.ticket_type.contains("Instalacion"))
            holder.txtTicketTipo.setBackgroundResource(R.drawable.button_border_pink)
        if(t.ticket_type.contains("Homologacion"))
            holder.txtTicketTipo.setBackgroundResource(R.drawable.button_border_cyan)

        holder.txtEstadoTicket.typeface = tf
        //holder.txtTicketProgress.typeface = tf
        holder.txtTicketPriority.typeface = tf
        holder.txtClient.typeface = tf
        //holder.txtTicketService.typeface = tf
        holder.txtProgrammingDate.typeface = tf
        holder.txtTicketType.typeface = tfbold
        holder.txtTicketTipo.typeface = tf
        holder.btnDetalle.setOnClickListener {
            //ticketVieModel = ViewModelProvider(AutorizacionesActivity()).get(TicketsViewModel::class.java)
            /*autorizar(uid,t.order_required,t.ticket_type_id,t.ticket_id,t.ticket_code,"1",
            "A","authorize_approve_ticket",tk)*/
            val intent = Intent(it.context, AuthTicketDetailActivity::class.java)
            intent.putExtra("ticketId",t.ticket_id)
            intent.putExtra("workId",t.work_id)
            intent.putExtra("ordenSolicitud",t.order_required)
            intent.putExtra("ticketCliente",t.client_branch)
            intent.putExtra("ticketServicio",t.service)
            intent.putExtra("ticketTypeId",t.ticket_type_cod)
            intent.putExtra("ticketTipoSolicitud",t.ticket_type)
            intent.putExtra("ticketCode",t.service_cod+"-"+t.ticket_type_cod+"-"+t.ticket_id)
            //intent.putExtra("ticketTypeId",t.ticket_type_cod)
            //intent.putExtra("ticketPrioridadId",t.ticket_priority_id)
           // intent.putExtra("ticketPrioridad",t.ticket_priority)
           // intent.putExtra("ticketPrioridadColor",t.priority_color)
            //intent.putExtra("ticketFechaHora",t.programming_date)
           // intent.putExtra("ticketObserv",t.client_reason)
           // intent.putExtra("ticketLugarTrabajo",t.country+", "+t.deparment+", "+t.municipality)
            it.context.startActivity(intent)
        }



        holder.llBackgroundTicket.setOnClickListener {



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
