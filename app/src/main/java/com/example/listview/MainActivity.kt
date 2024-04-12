package com.example.listview

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var ageEditText: EditText

    private lateinit var saveButton: Button
    private lateinit var userListView: ListView

    private val userList = ArrayList<User>()
    private lateinit var arrayAdapter: ArrayAdapter<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        nameEditText = findViewById(R.id.nameEditText)
        ageEditText = findViewById(R.id.ageEditText)
        saveButton = findViewById(R.id.saveButton)
        userListView = findViewById(R.id.userListView)

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, userList)
        userListView.adapter = arrayAdapter

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val age = ageEditText.text.toString()
            val user = User(name, age.toInt())
            userList.add(user)
            arrayAdapter.notifyDataSetChanged()
            nameEditText.text = null
            ageEditText.text = null // Очистка полей ввода после сохранения
        }

        userListView.setOnItemClickListener { parent, view, position, id ->
            showDeleteDialog(position)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDeleteDialog(position: Int) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Подтвердите удаление")
        dialogBuilder.setMessage("Вы уверены, что хотите удалить этого пользователя?")

        dialogBuilder.setPositiveButton("Да") { _, _ ->
            userList.removeAt(position)
            arrayAdapter.notifyDataSetChanged()
        }

        dialogBuilder.setNegativeButton("Нет") { _, _ ->
            // Ничего не делаем
        }

        val dialog = dialogBuilder.create()
        dialog.show()
    }
}

data class User(val name: String, val age: Int) {
    override fun toString(): String {
        return "$name, $age"
    }
}
