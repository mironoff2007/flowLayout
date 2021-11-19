package com.mironov.flowlayout

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var flowLayout: FlowLayout

    private fun findViews() {
        flowLayout = findViewById(R.id.flowLayout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //Debug.waitForDebugger()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        addLayouts()
    }

    @SuppressLint( "SetTextI18n", "InflateParams", "UseCompatLoadingForDrawables")
    private fun addLayouts() {

        flowLayout.removeAllViews()
        for (i in 0..99) {
            val view: View = this.layoutInflater.inflate(R.layout.flow_group, null)
            val textView = view.findViewById<View>(R.id.tvText) as TextView
            val imageView = view.findViewById<View>(R.id.imageView) as ImageView


            textView.text = Tags.arr[i]
            view.tag = "unselected"

            //Set spacing here
            view.layoutParams=FlowLayout.LayoutParams(20,20)

            when {
                i<20 -> {imageView.setImageDrawable(resources.getDrawable(R.drawable.ic_1))}
                i<50 -> {imageView.setImageDrawable(resources.getDrawable(R.drawable.ic_2))}
                else -> {imageView.setImageDrawable(resources.getDrawable(R.drawable.ic_3))}
            }

            view.setBackgroundResource(R.drawable.shape_unselected_tag)

            flowLayout!!.addView(view)

            view.setOnClickListener {
                if (it.tag=="selected") {
                    it.tag= "unselected"
                    view.setBackgroundResource(R.drawable.shape_unselected_tag)
                    textView.setTextColor(resources.getColor(R.color.black))
                } else {
                    it.tag= "selected"
                    view.setBackgroundResource(R.drawable.shape_selected_tag)
                    textView.setTextColor(resources.getColor(R.color.white))
                }
            }
        }
    }
}