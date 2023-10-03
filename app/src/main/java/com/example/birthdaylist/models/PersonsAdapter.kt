package com.example.birthdaylist.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaylist.R

// Adapter class borrowed from: https://github.com/andersbor/kotlinViewModelMasterDetail3/tree/master
class PersonsAdapter(
    private val items: List<Person>,
    private val onItemClicked: (position: Int) -> Unit
) :
    RecyclerView.Adapter<PersonsAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item, viewGroup, false)
        return MyViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textViewPersonName.text = items[position].name

        val birthDate: String = items[position].birthDayOfMonth.toString()
        val birthMonth: String = items[position].birthMonth.toString()
        val birthYear: String = items[position].birthYear.toString()
        val dateStr = "$birthDate/$birthMonth - $birthYear"
        viewHolder.textViewPersonBirthday.text = dateStr

        viewHolder.textViewPersonAge.text = items[position].age.toString()

    }

    class MyViewHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textViewPersonName: TextView = itemView.findViewById(R.id.list_item_name)
        val textViewPersonBirthday: TextView = itemView.findViewById(R.id.list_item_birthday)
        val textViewPersonAge: TextView = itemView.findViewById(R.id.list_item_age)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = bindingAdapterPosition
            onItemClicked(position)
        }
    }
}