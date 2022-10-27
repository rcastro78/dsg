package sv.com.seguridadcontrol.app.adapter

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.modelos.ClientSpr
import java.util.ArrayList

class ClientSpinnerAdapter  (activity: Activity, items: ArrayList<ClientSpr>?) : BaseAdapter() {
    var activity: Activity? = null
    var items: ArrayList<ClientSpr>? = null
    var item: ClientSpr? = null
    val holder = ViewHolder()
    val TAG = "AlumnosAdapter"
    var tf: Typeface? = null
    var tfBold: Typeface? = null

    init{
        this.activity = activity
        this.items = items
        tf = Typeface.createFromAsset(activity.assets, "fonts/Nunito-Regular.ttf")
    }

    override fun getCount(): Int {
        return items!!.size
    }

    override fun getItem(position: Int): Any? {
        return items!![position]
    }

    override fun getItemId(arg0: Int): Long {
        // TODO Auto-generated method stub
        return 0
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        item = items!![position]
        var holder: ViewHolder?
        if (convertView == null) {
            val inflater =
                activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.item_spinner, null)
            holder = ViewHolder()
            holder.lblItem = convertView!!.findViewById<TextView>(R.id.itemSpinner)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.lblItem!!.typeface = tf
        holder.lblItem!!.text = item!!.nombre
        return convertView
    }

    class ViewHolder {
        var lblItem: TextView? = null
    }
}

