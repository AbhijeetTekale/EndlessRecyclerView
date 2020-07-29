package com.example.endlessrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val numberList : MutableList<String> = ArrayList()
    val page = 1
    var isLoading = false
    val limit = 20

    lateinit var adapter:Adapter
    lateinit var layoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager = LinearLayoutManager(this)
        rcyclView.layoutManager = layoutManager

        getPage()
    }

    fun getPage()
    {
        prgsBar.visibility = View.VISIBLE
        val start = (page-1)*limit
        val end =(page)*limit

        for(i in start..end)
        {
           numberList.add("Earphones")
        }
        Handler().postDelayed({
            if (::adapter.isInitialized) {
                adapter.notifyDataSetChanged()
            } else
                adapter = Adapter(this)
                rcyclView.adapter = adapter
            prgsBar.visibility = View.GONE
        },5000)
    }

    class Adapter(val activity: MainActivity) : RecyclerView.Adapter<Adapter.ViewHolder>()
    {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
            return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item,parent,false))
        }

        override fun getItemCount(): Int {
            return activity.numberList.size
        }

        override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
            holder.Name.text =activity.numberList[position]
        }

        class ViewHolder(v:View):RecyclerView.ViewHolder(v)
        {
            val Name = v.findViewById(R.id.txtProductName) as TextView

        }

    }
}

