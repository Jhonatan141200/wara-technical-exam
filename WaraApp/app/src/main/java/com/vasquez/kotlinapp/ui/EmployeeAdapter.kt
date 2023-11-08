package com.vasquez.kotlinapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vasquez.kotlinapp.databinding.RowEmployeeUpBinding
import com.vasquez.kotlinapp.model.Employee

/**
 * @author Vasquez Reyna J
 */
class EmployeeAdapter(
    private var employees: List<Employee>,
    private val itemCallback: (employee: Employee) -> Unit?
) : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val viewBinding = RowEmployeeUpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = employees.size

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(employees[position])
    }

    fun update(data: List<Employee>) {
        employees = data
        notifyDataSetChanged()
    }

    inner class EmployeeViewHolder(private val viewBinding: RowEmployeeUpBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(employee: Employee) {
            viewBinding.tvName.text = employee.firstName + " " + employee.lastName
            viewBinding.tvDni.text = employee.dni
            viewBinding.tvEdad.text = employee.age.toString()
            viewBinding.root.setOnClickListener {
                itemCallback(employee)
            }
        }
    }
}