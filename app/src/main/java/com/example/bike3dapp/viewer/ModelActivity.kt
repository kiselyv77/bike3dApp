package com.example.bike3dapp.viewer

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bike3dapp.R
import com.example.bike3dapp.databinding.ActivityModelBinding
import com.example.bike3dapp.rcView.Adapter
import com.example.bike3dapp.rcView.ItemModel
import org.andresoviedo.android_3d_model_engine.camera.CameraController
import org.andresoviedo.android_3d_model_engine.collision.CollisionController
import org.andresoviedo.android_3d_model_engine.controller.TouchController
import org.andresoviedo.android_3d_model_engine.services.LoaderTask
import org.andresoviedo.android_3d_model_engine.services.SceneLoader
import org.andresoviedo.android_3d_model_engine.view.ModelRenderer.ViewEvent
import org.andresoviedo.android_3d_model_engine.view.ModelSurfaceView
import org.andresoviedo.util.android.AndroidURLStreamHandlerFactory
import org.andresoviedo.util.android.ContentUtils
import org.andresoviedo.util.event.EventListener
import java.net.URI
import java.net.URL
import java.util.*

class ModelActivity : AppCompatActivity(), EventListener {


    init {
        System.setProperty("java.protocol.handler.pkgs", "org.andresoviedo.util.android")
        URL.setURLStreamHandlerFactory(AndroidURLStreamHandlerFactory())
    }

    /**
     * The file to load. Passed as input parameter
     */
    private var paramUri: URI? = null

    /**
     * Background GL clear color. Default is light gray
     */
    private val backgroundColor = floatArrayOf(0.0f, 0.0f, 0.0f, 1.0f)
    private var gLView: ModelSurfaceView? = null
    private var touchController: TouchController? = null
    var scene: SceneLoader? = null
    private var gui: ModelViewerGUI? = null
    var mNavController:  NavController? = null

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        ContentUtils.provideAssets(this)
        Log.i("ModelActivity", "Loading activity...")
        super.onCreate(savedInstanceState)
        val binding = ActivityModelBinding.inflate(layoutInflater)
        val attrib = this.window.attributes
        attrib.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        this.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        this.window.decorView.systemUiVisibility =
            this.window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        mNavController = navHostFragment.navController
        paramUri = URI(URI.create("android://$packageName/assets/models/bike.dae").toString())

        // Create our 3D scenario
        Log.i("ModelActivity", "Loading Scene...")

        try {
            Log.i("ModelActivity", "Loading GLSurfaceView...")
            setContentView(binding.root)
            val root = binding.parent
            scene = SceneLoader(this, paramUri, 2)
            gLView = ModelSurfaceView(this, backgroundColor, scene)
            gLView!!.addListener(this)
            val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutParams.height = 800
            scene!!.setView(gLView)
            root.addView(gLView, 0, layoutParams)
            
        } catch (e: Exception) {
            Log.e("ModelActivity", e.message, e)
            Toast.makeText(this, """ Error loading OpenGL view:${e.message} """.trimIndent(), Toast.LENGTH_LONG).show()
        }
        try {
            Log.i("ModelActivity", "Loading TouchController...")
            touchController = TouchController(this)
            touchController!!.addListener(this)
        } catch (e: Exception) { Log.e("ModelActivity", e.message, e)
            Toast.makeText(this, """Error loading TouchController:${e.message}""".trimIndent(), Toast.LENGTH_LONG).show()
        }

        // load model
        scene!!.init()
        Log.i("ModelActivity", "Finished loading")
    }

    override fun onEvent(event: EventObject): Boolean {
        if (event is ViewEvent) {
            val viewEvent = event
            if (viewEvent.code == ViewEvent.Code.SURFACE_CHANGED) {
                touchController!!.setSize(viewEvent.width, viewEvent.height)
                gLView!!.setTouchController(touchController)

                // process event in GUI
                if (gui != null) {
                    gui!!.setSize(viewEvent.width, viewEvent.height)
                    gui!!.isVisible = true
                }
            }
        }
        return true
    }
}
