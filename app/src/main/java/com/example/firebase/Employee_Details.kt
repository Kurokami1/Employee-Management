package com.example.firebase

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.firebase.databinding.ActivityEmployeeDetailsBinding
import com.example.firebase.databinding.UpdateDialogBinding
import com.example.firebase.model.Employee_Model
import com.google.firebase.database.FirebaseDatabase

private lateinit var binding : ActivityEmployeeDetailsBinding
private lateinit var bindingUpdate : UpdateDialogBinding

class Employee_Details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeDetailsBinding.inflate(layoutInflater)
        bindingUpdate= UpdateDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setValue()
        binding.btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("Id").toString()
            )
        }

        binding.btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("Id").toString(),
                intent.getStringExtra("Name").toString()
            )
        }
    }

    private fun openUpdateDialog(Id: String, Name: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)
        mDialog.setView(mDialogView)

//        bindingUpdate.txtName.setText(Name)
//        bindingUpdate.txtAge.setText(intent.getStringExtra("Age").toString())
//        bindingUpdate.txtSalary.setText(intent.getStringExtra("Salary").toString())
        val empName = mDialogView.findViewById<EditText>(R.id.txtName)
        val empAge = mDialogView.findViewById<EditText>(R.id.txtAge)
        val empSalary = mDialogView.findViewById<EditText>(R.id.txtSalary)
        val btnUpdate = mDialogView.findViewById<Button>(R.id.btnUpdate)
        empName.setText(Name)
        empAge.setText(binding.txtSetAge.text.toString())
        empSalary.setText(binding.txtSetSalary.text.toString())
        mDialog.setTitle("Updating Record")
        val alertDialog = mDialog.create()
        alertDialog.show()
            btnUpdate.setOnClickListener{
            updateEmpData(
                Id,
                empName.text.toString(),
                empAge.text.toString(),
                empSalary.text.toString()
            )
            Toast.makeText(this@Employee_Details, "Employee's Data cập nhập thành công", Toast.LENGTH_SHORT).show()
            binding.txtSetName.setText(empName.text.toString())
            binding.txtSetAge.setText(empAge.text.toString())
            binding.txtSetSalary.setText(empSalary.text.toString())
            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(id: String, Name: String, Age: String, Salary: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val empInfo = Employee_Model(id, Name, Age, Salary)
        dbRef.setValue(empInfo)
    }


    private fun deleteRecord(Id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(Id)
        val mTask = dbRef.removeValue()
        mTask.addOnSuccessListener {
            Toast.makeText(this@Employee_Details, "Employee's data đã xóa", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@Employee_Details, Fetching_Activity::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this@Employee_Details, "Employee's data đã xóa thất bại", Toast.LENGTH_SHORT).show()

        }
    }

    private fun setValue() {
        binding.txtSetId.text = intent.getStringExtra("Id")
        binding.txtSetAge.text = intent.getStringExtra("Age")
        binding.txtSetSalary.text = intent.getStringExtra("Salary")
        binding.txtSetName.text = intent.getStringExtra("Name")
    }
}