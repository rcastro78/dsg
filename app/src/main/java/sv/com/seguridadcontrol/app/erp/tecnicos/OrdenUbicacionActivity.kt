package sv.com.seguridadcontrol.app.erp.tecnicos

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.pes.androidmaterialcolorpickerdialog.ColorPicker
import com.rm.freedrawview.PathRedoUndoCountChangeListener
import kotlinx.android.synthetic.main.activity_orden_ubicacion.*
import sv.com.seguridadcontrol.app.R

import android.graphics.Bitmap
import com.rm.freedrawview.FreeDrawView.DrawCreatorListener
import android.os.Environment
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import okhttp3.MediaType
import sv.com.seguridadcontrol.app.viewmodel.OrderUbicacionViewModel
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import okhttp3.RequestBody

import okhttp3.MultipartBody
import java.io.ByteArrayOutputStream


class OrdenUbicacionActivity : AppCompatActivity() {
    private var sharedPreferences: SharedPreferences? = null
    var orderNum:String=""
    private lateinit var orderUbicacionViewModel : OrderUbicacionViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orden_ubicacion)
        val SHARED:String=getString(R.string.sharedpref)
        sharedPreferences = getSharedPreferences(SHARED, 0)
        orderUbicacionViewModel= ViewModelProvider(this).get(OrderUbicacionViewModel::class.java)
        val token:String? = sharedPreferences!!.getString("token","")
        val ticketId:String? = sharedPreferences!!.getString("ticketId","")
        val orderId:String? = intent.getStringExtra("orderId")
        val orderStart:String? = intent.getStringExtra("orderStart")
        val orderEnd:String? = intent.getStringExtra("orderEnd")
        orderNum = intent.getStringExtra("orderNum")!!
        val tfb = Typeface.createFromAsset(assets,"fonts/Nunito-Bold.ttf")
        val tf = Typeface.createFromAsset(assets,"fonts/Nunito-Regular.ttf")
        val toolbar =
            findViewById<Toolbar>(R.id.tool_bar) // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar)
        val lblToolbar = toolbar.findViewById<TextView>(R.id.lblToolbar)
        lblToolbar.text = "Ubicación"


        val cp = ColorPicker(this, 255, 0, 0)
        var selectedColor=0;
        fdvUbicacion.setPathRedoUndoCountChangeListener(object : PathRedoUndoCountChangeListener {
            override fun onUndoCountChanged(undoCount: Int) {

            }

            override fun onRedoCountChanged(redoCount: Int) {
                // The redoCount is the number of path removed that can be redrawn
            }
        })


       /* imbPaleta.setOnClickListener {
          cp.show()
            selectedColor = cp.color
        }

        imbDibujar.setOnClickListener {
            cp.dismiss()
            fdvUbicacion.paintColor=selectedColor
        }*/



        imbDeshacer.setOnClickListener{
           fdvUbicacion.undoLast()
        }
        imbRehacer.setOnClickListener{
            fdvUbicacion.redoLast()
        }
        btnGuardaUbicacion.typeface = tf
        btnGuardaUbicacion.setOnClickListener {
            fdvUbicacion.getDrawScreenshot(object : DrawCreatorListener {
                override fun onDrawCreated(b: Bitmap) {
                    //saveImage(b,"$orderId-$ticketId-ubicacion")
                    val bos = ByteArrayOutputStream()
                    b.compress(Bitmap.CompressFormat.PNG,90,bos)
                    val byteArray = bos.toByteArray()
                    val img = Base64.encodeToString(byteArray,Base64.DEFAULT)
                    storeImage(img,"O",orderId!!,"orders_stored_img",token!!)

                }

                override fun onDrawCreationError() {
                    // Something went wrong creating the bitmap, should never
                    // happen unless the async task has been canceled
                }
            })
            //val b:Bitmap = getBitmapFromView(fdvUbicacion)
            //Toast.makeText(applicationContext,"datos: ${b.width} x ${b.height} tam: ${b.byteCount}",Toast.LENGTH_LONG).show()

        }



    }


    private fun storeImage(img:String,imgType:String,orderId:String,task:String,token:String){
        orderUbicacionViewModel.storeUbicacionObserver().observe(this,{result->

            if(result.contains("Exito.")){
                Toast.makeText(applicationContext,"Ubicación guardada correctamente",Toast.LENGTH_LONG).show()
                val intent = Intent(this@OrdenUbicacionActivity, OrdenTrabajoActivity::class.java)
                intent.putExtra("orderId",orderId)
                intent.putExtra("orderNum",orderNum)
                intent.putExtra("ubicacion",1)
                val editor : Editor = sharedPreferences!!.edit()
                editor.putInt("ubicacion",1)
                editor.apply()
                startActivity(intent)

            }
        })
        orderUbicacionViewModel.storeUbicacionImage(img,imgType,orderId,task,token)


    }


/*
    private fun saveImage(finalBitmap: Bitmap, image_name: String) {
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File(root)
        myDir.mkdirs()
        val fname = "Image-$image_name.jpg"
        val file = File(myDir, fname)
        if (file.exists()) file.delete()
        Log.i("LOAD", root + fname)



        try {
            val out = FileOutputStream(file)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
*/

}