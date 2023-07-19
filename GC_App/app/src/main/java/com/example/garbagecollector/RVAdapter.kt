package com.example.garbagecollector

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.garbagecollector.ORMDatabase.GCUsers
import com.example.garbagecollector.viewModel.GCViewModel

class RVAdapter(private val gcViewModel: GCViewModel, val context: RecViewFragment) : RecyclerView.Adapter<RVAdapter.ViewHolder>()
{
    private var allUser = ArrayList<GCUsers>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView? = itemView.findViewById(R.id.recViewName)
        val date : TextView? = itemView.findViewById(R.id.recViewDate)
        val time: TextView? = itemView.findViewById(R.id.recViewTime)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_view_background,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return allUser.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name?.text = allUser[position].name
        holder.date?.text = allUser[position].time
        holder.time?.text = allUser[position].date
    }
    fun updateList(newList:List<GCUsers>) {
        allUser.clear()
        allUser.addAll(newList)
        notifyDataSetChanged()
    }
}