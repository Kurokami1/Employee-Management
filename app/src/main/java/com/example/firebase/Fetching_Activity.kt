package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.databinding.ActivityFetchingBinding
import com.example.firebase.model.Employee_Model
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private lateinit var binding: ActivityFetchingBinding
class Fetching_Activity : AppCompatActivity() {
    private lateinit var ds: ArrayList<Employee_Model>
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFetchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvEmp.layoutManager = LinearLayoutManager(this)
        binding.rvEmp.setHasFixedSize(true)
        ds = arrayListOf<Employee_Model>()
        Get_Employee_Information()

    }

    private fun Get_Employee_Information() {
        binding.rvEmp.visibility = View.GONE
        binding.txtLoading.visibility = View.VISIBLE
        dbRef = FirebaseDatabase.getInstance().getReference("Employees")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                ds.clear()
                if(snapshot.exists())
                {
                    for(empSnap in snapshot.children)
                    {
                        val empData = empSnap.getValue(Employee_Model::class.java)
                        ds.add(empData!!)
                    }
                    val nAdapter = Employee_Adapter(ds)
                    binding.rvEmp.adapter = nAdapter

                    nAdapter.setOnItemClickListener(object : Employee_Adapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@Fetching_Activity, Employee_Details::class.java)
                            intent.putExtra("Id", ds[position].Id)
                            intent.putExtra("Name", ds[position].Name)
                            intent.putExtra("Salary", ds[position].Salary)
                            intent.putExtra("Age", ds[position].Age)
                            startActivity(intent)
                        }
                    })

                    binding.rvEmp.visibility = View.VISIBLE
                    binding.txtLoading.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}