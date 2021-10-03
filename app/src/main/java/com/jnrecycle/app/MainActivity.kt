package com.jnrecycle.app

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter


class MainActivity : AppCompatActivity() {

    private lateinit var list: RecyclerView
    private lateinit var pref: SharedPreferences
    val listArr = ArrayList<Map.Entry<String?,Any?>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pref = getSharedPreferences("pushlog", MODE_PRIVATE)


        list =findViewById<RecyclerView>(R.id.list)
        val layoutManager = LinearLayoutManager(this,
            RecyclerView.VERTICAL,false)
        list.layoutManager =layoutManager

        list.adapter = object: Adapter<ViewHolder>() {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.list_item,parent,false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val v = listArr[position].value as String
                val vs = v.split(';')
                holder.txt.text = vs[0]
                holder.description.text = vs[1]
                holder.item.setBackgroundColor(if(position % 2==0) Color.LTGRAY else Color.WHITE)
                holder.item.setOnClickListener {
                    val intentNew =  Intent(Intent.ACTION_VIEW)
                    intentNew.setData(Uri.parse(listArr[position].key))
                    startActivity(intentNew)
                }
            }

            override fun getItemCount(): Int {
                return listArr.size
            }
        }
    }

    override fun onStart() {
        super.onStart()

        listArr.clear()
        pref.all.forEach { listArr.add(it) }
        listArr.sortByDescending { (it.value as String).split(';')[2] }
        list.adapter!!.notifyDataSetChanged()
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt: TextView = itemView.findViewById(R.id.txt)
        val description:TextView = itemView.findViewById(R.id.description)
        val item:View = itemView.findViewById(R.id.item)
    }





}