package com.shashi.imagegalary

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.individual_view.view.*
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var myPermission = ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
        if(myPermission == PackageManager.PERMISSION_GRANTED){
            readFiles()
        }
        else{
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1234)
        }
         fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
             if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                 readFiles()
             }
             else{
                 Toast.makeText(this@MainActivity,"You cann't access the file without storage Permission!",Toast.LENGTH_SHORT).show()
             }
        }
    } // onCreate
    fun readFiles(){
        var path = "/storage/sdcard0/WhatsApp/Media/WhatsApp Images/Sent/"
        var file = File(path)
        if(!file.exists()){
            path = "/storage/emulated/0/WhatsApp/Media/WhatsApp Images/Sent/"
            file = File(path)
        }
        var files = file.listFiles()
        var myAdapter = object : BaseAdapter(){
            override fun getCount(): Int = files.size

            override fun getItem(position: Int): Any =0

            override fun getItemId(position: Int): Long =0

            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                var myInflater = LayoutInflater.from(this@MainActivity)
                var myView: View = myInflater.inflate(R.layout.individual_view,null)

                file = files[position]
                var bmp : Bitmap = BitmapFactory.decodeFile(file.path)
                var compressed_bmp = ThumbnailUtils.extractThumbnail(bmp,200,150)
                myView.iview.setImageBitmap(compressed_bmp)
                myView.filename.text = file.name
                myView.filesize.text = file.length().toString()+" kb"
                return myView
            }

        }//
        galry.adapter = myAdapter
    }
}
