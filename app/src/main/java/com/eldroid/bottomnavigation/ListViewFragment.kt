package com.eldroid.bottomnavigation

import android.app.AlertDialog
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.core.view.GestureDetectorCompat
import com.eldroid.listview.CustomAdapter

class ListViewFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var itemList: ArrayList<ItemModel>
    private lateinit var adapter: CustomAdapter
    private lateinit var gestureDetector: GestureDetectorCompat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        // Initialize UI components
        listView = view.findViewById(R.id.mainListview)

        // Populate itemList
        itemList = ArrayList()
        itemList.add(ItemModel("Palit groceries", R.drawable.ic_launcher_foreground, false))
        itemList.add(ItemModel("Tiwas project", R.drawable.ic_launcher_foreground, false))
        itemList.add(ItemModel("Dentist appointment", R.drawable.ic_launcher_foreground, false))
        itemList.add(ItemModel("Movie date", R.drawable.ic_launcher_foreground, false))
        itemList.add(ItemModel("Limpyo kwarto", R.drawable.ic_launcher_foreground, false))

        // Set adapter
        adapter = CustomAdapter(requireContext(), itemList)
        listView.adapter = adapter

        // Set add button listener
        val addButton: ImageView = view.findViewById(R.id.addButton)
        addButton.setOnClickListener {
            showAddItemDialog()
        }

        // Set gesture detector for double tap
        gestureDetector = GestureDetectorCompat(requireContext(), GestureListener())
        listView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }

        return view
    }

    // Gesture listener for single and double tap
    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            val position = listView.pointToPosition(e.x.toInt(), e.y.toInt())
            if (position != ListView.INVALID_POSITION) {
                Toast.makeText(
                    requireContext(),
                    "Single click on ${itemList[position].description}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            return super.onSingleTapConfirmed(e)
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            val position = listView.pointToPosition(e.x.toInt(), e.y.toInt())
            if (position != ListView.INVALID_POSITION) {
                showEditDeleteDialog(position)
            }
            return super.onDoubleTap(e)
        }
    }

    private fun showAddItemDialog() {
        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.dialog_add_item, null)

        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setView(dialogView)

        val descriptionET: EditText = dialogView.findViewById(R.id.descriptionET)
        val addBTN: Button = dialogView.findViewById(R.id.addBTN)
        val cancelBTN: ImageView = dialogView.findViewById(R.id.cancelBTN)

        val dialog = dialogBuilder.create()

        addBTN.setOnClickListener {
            val description = descriptionET.text.toString().trim()

            if (description.isNotEmpty()) {
                itemList.add(ItemModel(description, R.drawable.ic_launcher_foreground, false))
                adapter.notifyDataSetChanged()

                Toast.makeText(requireContext(), "Item added", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Please enter a description", Toast.LENGTH_SHORT).show()
            }
        }

        cancelBTN.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showEditDeleteDialog(position: Int) {
        val options = arrayOf("Edit", "Delete")
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Choose an action")
        dialogBuilder.setItems(options) { _, which ->
            when (which) {
                0 -> showEditItemDialog(position)
                1 -> deleteItem(position)
            }
        }
        dialogBuilder.show()
    }

    private fun showEditItemDialog(position: Int) {
        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.dialog_add_item, null)

        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setView(dialogView)

        val addHeader: TextView = dialogView.findViewById(R.id.addHeader)
        val descriptionET: EditText = dialogView.findViewById(R.id.descriptionET)
        val addBTN: Button = dialogView.findViewById(R.id.addBTN)
        val cancelBTN: ImageView = dialogView.findViewById(R.id.cancelBTN)

        val currentItem = itemList[position]

        addHeader.text = "Edit Item"
        addBTN.text = "Edit"

        descriptionET.setText(currentItem.description)

        val dialog = dialogBuilder.create()

        addBTN.setOnClickListener {
            val newDescription = descriptionET.text.toString().trim()

            if (newDescription.isNotEmpty()) {
                currentItem.description = newDescription
                adapter.notifyDataSetChanged()

                Toast.makeText(requireContext(), "Item updated", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Please enter a description", Toast.LENGTH_SHORT).show()
            }
        }

        cancelBTN.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun deleteItem(position: Int) {
        val item = itemList[position]
        itemList.removeAt(position)
        adapter.notifyDataSetChanged()

        Toast.makeText(requireContext(), "${item.description} deleted", Toast.LENGTH_SHORT).show()
    }
}
