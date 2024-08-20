package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebase.databinding.ActivityInsertBinding
import com.example.firebase.model.Employee_Model
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private lateinit var binding : ActivityInsertBinding
class Insert_Activity : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbRef = FirebaseDatabase.getInstance().getReference("Employees")
        binding.btnSave.setOnClickListener{
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {
        val Name = binding.editName.text.toString()
        val Age = binding.editAge.text.toString()
        val Salary = binding.editSalary.text.toString()

        val Id = dbRef.push().key!!
        val employee = Employee_Model(Id, Name, Age, Salary)

        if(Name.isEmpty())
        {
            binding.editName.error = "Please enter your name"
            return
        }
        if(Age.isEmpty())
        {
            binding.editAge.error = "Please enter your name"
            return
        }
        if(Salary.isEmpty())
        {
            binding.editSalary.error = "Please enter your name"
            return
        }



        dbRef.child(Id).setValue(employee)
            .addOnCompleteListener {
            Toast.makeText(this, "Data Insert thành công", Toast.LENGTH_SHORT)
                .show()
                binding.editName.setText("")
                binding.editAge.setText("")
                binding.editSalary.setText("")
            }
            .addOnFailureListener {
                Toast.makeText(this, "Data Insert lỗi", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}