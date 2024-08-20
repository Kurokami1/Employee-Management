package com.example.firebase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.firebase.databinding.EmpListItemBinding
import com.example.firebase.model.Employee_Model

private lateinit var bindingEmp: EmpListItemBinding


class Employee_Adapter(private val ds:ArrayList<Employee_Model> ):RecyclerView.Adapter<Employee_Adapter.ViewHolder>() {
//    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//
//
//          val itemView = LayoutInflater.from(parent.context)
//              .inflate(R.layout.emp_list_item, parent, false)
//        return ViewHolder(itemView)
//    }
    private lateinit var mListener: onItemClickListener
  interface onItemClickListener{
      fun onItemClick(position: Int)
  }
  fun setOnItemClickListener(clickListener: onItemClickListener){
      mListener = clickListener
  }

  inner class ViewHolder(val binding: EmpListItemBinding, clickListener: onItemClickListener) : RecyclerView.ViewHolder(bindingEmp.root){
      init {
          itemView.setOnClickListener {
              clickListener.onItemClick(adapterPosition)
          }
      }
  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
          bindingEmp = EmpListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bindingEmp, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.apply {
                bindingEmp.txtEmpName.text = ds[position].Name
            }
    }

    override fun getItemCount(): Int {
        return ds.size

    }
}
