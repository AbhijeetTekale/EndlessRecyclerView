package com.example.endlessrecyclerview

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(){


    val numberList : MutableList<Inputs> = ArrayList()
    var products =  arrayOf("Mobile","earphones","laptop","powerbank","Desktop","charger","cable","battery","pendrive")
    var Values = arrayOf("10000","500","35000","700","25000","300","200","700","600")
    var page = 1
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

         rcyclView.addOnScrollListener(object:RecyclerView.OnScrollListener(){
             override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                 if(dy>0){
                     val VisibleItem = layoutManager.childCount
                     val pastvisible = layoutManager.findFirstCompletelyVisibleItemPosition()
                     val total = adapter.itemCount

                     if(!isLoading)
                         if((VisibleItem + pastvisible)>=total)
                         {
                             page++
                             getPage()
                         }
                 }

             }
         })
    }

    fun getPage()
    {
        isLoading = true
        val start = (page-1)*limit
        val end =(page)*limit

        for(i in start..end)
        {
            var a  = i%(products.size)
            numberList.add(Inputs(products[a],Values[a]))
        }
        Handler().postDelayed({
            if (::adapter.isInitialized) {
                adapter.notifyDataSetChanged()
            } else
                adapter = Adapter(this)
                rcyclView.adapter = adapter
            isLoading = false
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
            val value:Inputs = activity.numberList[position]
            holder.Name.text = value.Name
            holder.Mrp.text = value.Price
        }

        class ViewHolder(v:View):RecyclerView.ViewHolder(v)
        {
            val activity2: MainActivity = MainActivity()
            val Name = v.findViewById(R.id.txtProductName) as TextView
            val Mrp = v.findViewById(R.id.txtProductMRP) as TextView
            var amount=0
            init {
                itemView.setOnClickListener { v: View ->
                    Toast.makeText(v.context,"You Clicked ${activity2.products[adapterPosition]}",Toast.LENGTH_SHORT).show()
                    amount = Integer.parseInt(activity2.Values[adapterPosition])
                    amount = amount*100
                    val i = Intent(v.context,PaymentGateway::class.java)
                    i.putExtra("Price",amount)
                    itemView.context.startActivity(i)
                    }
                }
        }

    }

}

