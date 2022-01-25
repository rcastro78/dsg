package sv.com.seguridadcontrol.app.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.modelos.Article
import sv.com.seguridadcontrol.app.modelos.MaterialArticle


class ArticlesMaterialsAdapter(var articlesList:ArrayList<MaterialArticle>):
    RecyclerView.Adapter<ArticlesMaterialsAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //var nomVehicle : TextView = view.findViewById(R.id.lbl_nombre_vehiculo)
        //val nomMake : TextView = view.findViewById(R.id.lbl_make_vehicle)

        val txtArticulo  : TextView = view.findViewById(R.id.txtArticuloArticulos)
        val txtProducto  : TextView = view.findViewById(R.id.txtProductoArticulos)
        val txtMarca  : TextView = view.findViewById(R.id.txtMarcaArticulos)
        val txtCategoria  : TextView = view.findViewById(R.id.txtCat)
        val txtEstado  : TextView = view.findViewById(R.id.txtEstadoArticulos)



    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_prov_articulos, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val a = articlesList[position]
        /*Glide.with(holder.imgBeast.getContext()).load(c.pkImage)
                .placeholder(R.drawable.pokeball01)
                .error(R.drawable.pokeball01)
                .circleCrop()
                .into(holder.imgBeast)*/
        //holder.nomVehicle.text=v.vendor_vehicle_model
        //holder.nomMake.text=v.vendor_vehicle_make
        val tf = Typeface.createFromAsset(holder.txtArticulo.context.getAssets(),"fonts/Nunito-Regular.ttf")


        holder.txtArticulo.text = a.article
        holder.txtMarca.text = a.brand
        holder.txtProducto.text = a.product
        holder.txtCategoria.text = a.branch
        holder.txtEstado.text = a.status


        holder.txtArticulo.typeface = tf
        holder.txtMarca.typeface = tf
        holder.txtProducto.typeface = tf
        holder.txtCategoria.typeface = tf
        holder.txtEstado.typeface = tf




    }

    override fun getItemCount(): Int {
        return articlesList.size
    }

}
