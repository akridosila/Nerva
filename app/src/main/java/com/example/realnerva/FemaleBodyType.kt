package com.example.realnerva

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class FemaleBodyType : AppCompatActivity() {

    private lateinit var arFragment: ArFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.female_body)

        arFragment = supportFragmentManager.findFragmentById(R.id.sceneView1) as ArFragment

        loadModel()
    }

    private fun loadModel() {
        ModelRenderable.builder()
            .setSource(this, R.raw.model2)
            .build()
            .thenAccept { renderable ->
                val node = TransformableNode(arFragment.transformationSystem)
                node.renderable = renderable
                arFragment.arSceneView.scene.addChild(node)
                node.select()
            }
            .exceptionally { throwable ->
                // Handle the exception
                null
            }
    }
}