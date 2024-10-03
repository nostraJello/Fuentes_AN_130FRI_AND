package com.eldroid.listview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.eldroid.bottomnavigation.ItemModel
import com.eldroid.bottomnavigation.R

class CustomAdapter(context: Context, private val itemList: ArrayList<ItemModel>) :
    ArrayAdapter<ItemModel>(context, 0, itemList) {

    private val toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_listview, parent, false)

        val itemImage = view.findViewById<ImageView>(R.id.itemImage)
        val itemDescription = view.findViewById<TextView>(R.id.itemDescription)
        val itemCheckbox = view.findViewById<CheckBox>(R.id.itemCheckbox)

        itemImage.setImageResource(item?.imageResId ?: 0)
        itemDescription.text = item?.description
        itemCheckbox.isChecked = item?.isChecked ?: false

        itemCheckbox.setOnCheckedChangeListener { _, isChecked ->
            item?.isChecked = isChecked
            if (isChecked) {
                itemList.removeAt(position)
                notifyDataSetChanged()

                toast.setText("${item?.description} is done.")
                toast.show()
            }
        }

        return view
    }
}

