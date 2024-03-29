package com.example.memeshareapp

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target



class MainActivity : AppCompatActivity() {
    var currImageUrl:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()
    }

    private fun loadMeme(){

            val pb=findViewById<ProgressBar>(R.id.progressBar)
           val memeimage=findViewById<ImageView>(R.id.memeImage)
            pb.visibility=View.VISIBLE;
        // Instantiate the RequestQueue.
       // val queue = Volley.newRequestQueue(this)

        val url = "https://meme-api.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                currImageUrl=response.getString("url")
                Glide.with(this).load(currImageUrl).listener(object:RequestListener<Drawable>{

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                     pb.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                       pb.visibility = View.GONE
                        return false
                    }

                }).into(memeimage)

            },
            {

            })

    // Add the request to the RequestQueue.
      //  queue.add(jsonObjectRequest)
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun shareMeme(view: View) {

        val intent= Intent(Intent.ACTION_SEND)
        intent.type="jpg/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, Checkout this cool meme I got from Reddit $currImageUrl")

        val chooser=Intent.createChooser(intent,"Share this meme using....")
        startActivity(chooser)
    }

    fun nextMeme(view: View) {
        loadMeme()
    }


}