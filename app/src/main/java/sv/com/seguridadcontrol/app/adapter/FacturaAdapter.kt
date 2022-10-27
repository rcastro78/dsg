package sv.com.seguridadcontrol.app.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.dao.AppDatabase
import sv.com.seguridadcontrol.app.modelos.Factura
import sv.com.seguridadcontrol.app.utilities.Constants

class FacturaAdapter(var lstFactura:ArrayList<Factura>,var c:Context) :
    RecyclerView.Adapter<FacturaAdapter.ViewHolder>(){
    private lateinit var db: AppDatabase

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtDetaNumFactura  : TextView = view.findViewById(R.id.txtDetaNumFactura)
        val txtDetaMontoFactura  : TextView = view.findViewById(R.id.txtDetaMontoFactura)
        val txtDetaFechaFactura  : TextView = view.findViewById(R.id.txtDetaFechaFactura)
        val txtDetaFormaPagoFactura  : TextView = view.findViewById(R.id.txtDetaFormaPagoFactura)
        val imbRemoverFactura  : ImageButton = view.findViewById(R.id.imbRemoverFactura)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_factura, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val f = lstFactura[position]
        val tf = Typeface.createFromAsset(holder.txtDetaNumFactura.context.getAssets(),"fonts/Nunito-Regular.ttf")
        holder.txtDetaNumFactura.text = "No. factura: "+f.numFactura
        holder.txtDetaMontoFactura.text = "Monto: "+Constants.MONEDA+f.monto
        holder.txtDetaFechaFactura.text = "Fecha de factura: "+f.fecha
        holder.txtDetaFormaPagoFactura.text = "Forma de pago: "+f.formaPago
        holder.txtDetaNumFactura.typeface = tf
        holder.txtDetaMontoFactura.typeface = tf
        holder.txtDetaFechaFactura.typeface = tf
        holder.txtDetaFormaPagoFactura.typeface = tf

        holder.imbRemoverFactura.setOnClickListener{
            db = AppDatabase.getInstance(c)
            CoroutineScope(Dispatchers.IO).launch {
                db.iFacturaDAO.delete(f.numFactura)
            }
            lstFactura.removeAt(position)
            notifyItemRemoved(position)
        }

    }




    override fun getItemCount(): Int {
        return lstFactura.size
    }

}