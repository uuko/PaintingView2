package com.example.paintingview2

import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import java.io.File
import java.io.FileOutputStream

class PaintItActivity : AppCompatActivity() {

    private  var timeSet =0
    private lateinit var  paintView: PPView
    private val Pen = 1
    private val Eraser = 2
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paint)
        paintView = findViewById(R.id.paintView)
        var water: Button =findViewById(R.id.water)
        var turn : Button =findViewById(R.id.turn)
        var eraser : Button =findViewById(R.id.eraser)
        var back : Button =findViewById(R.id.back)
        var go : Button =findViewById(R.id.go)
        var save : Button =findViewById(R.id.save)
        var next : Button =findViewById(R.id.next)
        var prgress_width: SeekBar =findViewById(R.id.widthprogress)
        var eraser_width: SeekBar =findViewById(R.id.eraserprogress)
        var transparentbar: SeekBar =findViewById(R.id.transparenctbar)
        var colorRed: Button =findViewById(R.id.red)
        var colorYellow: Button =findViewById(R.id.yellow)
        var chColorLayout: View =findViewById(R.id.chColorLayout)
        var orange: View =findViewById(R.id.orange);
        var green: View =findViewById(R.id.green);
        var blue: View =findViewById(R.id.blue);
        var black: View =findViewById(R.id.black);
        var horse: ImageView =findViewById(R.id.horse);

        var drawable = horse.getDrawable();
        var isMode=false
        if(drawable != null){
            isMode=true
        }else{
            isMode=false
        }



        turn.setOnClickListener {
            //            var intent=Intent(this,PickerPhotoActivity::class.java)
//            startActivity(intent)
        }
        var cl= Color.BLACK
        var clickit=false
        var clickNext=false
        eraser.isEnabled=false
        water.setOnClickListener {
            //            horse.setImageDrawable(this.getDrawable(R.drawable.ic_unicorn))

            isMode=true

        }
        save.setOnClickListener {
            viewSaveToImage(paintView)
        }
        var a=10
        var trans=255
        var percent=0
        go.setOnClickListener {
            eraser_width.visibility= View.GONE
            clickit=!clickit
            if (clickit){
                eraser.isEnabled=false
                eraser.setBackgroundResource(R.drawable.eraser)
                go.setBackgroundResource(R.drawable.ic_paintbrush_sl)
                transparentbar.visibility= View.VISIBLE
                prgress_width.visibility= View.VISIBLE
                chColorLayout.visibility= View.VISIBLE
            }
            else{
                eraser.isEnabled=true
                eraser.setBackgroundResource(R.drawable.eraser)
                go.setBackgroundResource(R.drawable.ic_paintbrush)
                transparentbar.visibility= View.GONE
                prgress_width.visibility= View.GONE
                chColorLayout.visibility= View.GONE
            }


            paintView.setColor(cl,trans)
            paintView.setWidth(a)
            prgress_width.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    a=p0!!.progress
                    paintView.setWidth(a)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }
                override fun onStopTrackingTouch(p0: SeekBar?) {

                }
            })
            transparentbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    percent=p0!!.progress
                    trans=(256/100)*percent
                    paintView.setColor(cl,trans)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }


            } )
            colorRed.setOnClickListener {
                cl= Color.RED
                paintView.setColor(cl,trans)
                Log.d("color","red"+cl+trans)
            }
            colorYellow.setOnClickListener {
                cl= Color.YELLOW
                paintView.setColor(cl,trans)
            }

            black.setOnClickListener {
                cl= Color.BLACK
                paintView.setColor(cl,trans)
            }


            orange.setOnClickListener {
                cl= Color.MAGENTA
                paintView.setColor(cl,trans)
            }
            green.setOnClickListener {
                cl= Color.GREEN
                paintView.setColor(cl,trans)
            }

            blue.setOnClickListener {
                cl= Color.BLUE
                paintView.setColor(cl,trans)
            }
            Log.d("color","activity"+cl+"tt"+trans)
        }

        back.setOnClickListener {
            paintView.back()
        }

        next.setOnClickListener {
            paintView.next()
        }
        var progress=0
        eraser.setOnClickListener {

            clickNext=!clickNext

            if (clickNext){
                eraser.setBackgroundResource(R.drawable.eraser_slected)
                go.setBackgroundResource(R.drawable.ic_paintbrush)
                go.isEnabled=false
                prgress_width.visibility= View.GONE
                chColorLayout.visibility= View.GONE
                transparentbar.visibility= View.GONE
                eraser_width.visibility= View.VISIBLE
                paintView.setWidth(progress)
                eraser_width.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                        progress=p0!!.progress
                        paintView.setWidth(progress)
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {

                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {

                    }
                })

            }else{
                eraser.setBackgroundResource(R.drawable.eraser)
                go.setBackgroundResource(R.drawable.ic_paintbrush)
                go.isEnabled=true
                prgress_width.visibility= View.GONE
                eraser_width.visibility= View.GONE
            }
            paintView.eraserIt(isMode)

        }






    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
//        wave.setImageRadius(head.width / 2)
    }



    fun viewSaveToImage(view: PPView) {


        val cachebmp = view.loadBitmapFromView()


        val fos: FileOutputStream



        try {
            var appDir =  File(Environment.getExternalStorageDirectory(), "Boohee");
            val inflater = LayoutInflater.from(this)
            val v = inflater.inflate(R.layout.alertdialog_use, null)
            var fileName="";
            //語法一：new AlertDialog.Builder(主程式類別).XXX.XXX.XXX;
            AlertDialog.Builder(this)
                .setTitle("輸入新檔案名稱或是用預設名稱(test.png)")
                .setView(v)
                .setPositiveButton("確定", DialogInterface.OnClickListener { dialog, which ->
                    val editText = v.findViewById(R.id.editText1) as EditText
                    fileName=editText.text.toString()
                    Toast.makeText(
                        applicationContext, "新檔案名稱" +
                                editText.text.toString(), Toast.LENGTH_SHORT
                    ).show()
                })
                .show()
            if (fileName.isEmpty()){fileName="test"}
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            val file = File(appDir, fileName+".PNG")
            fos = FileOutputStream(file)
            cachebmp.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        view.destroyDrawingCache()
    }



}


