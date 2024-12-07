package com.example.userlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), Removable {

    private val users: MutableList<User> = mutableListOf()
    private var adapter: ArrayAdapter<User>? = null

    private lateinit var toolbarMain: Toolbar
    private lateinit var nameET: EditText
    private lateinit var ageET: EditText
    private lateinit var saveBTN: Button
    private lateinit var usersLV: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)
        title = "Каталог пользователей"

        nameET = findViewById(R.id.nameET)
        ageET = findViewById(R.id.ageET)
        saveBTN = findViewById(R.id.saveBTN)

        usersLV = findViewById(R.id.usersLV)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, users)
        usersLV.adapter = adapter

        saveBTN.setOnClickListener {
            if (nameET.text.isEmpty() || ageET.text.isEmpty()) return@setOnClickListener
            users.add(User(nameET.text.toString(), ageET.text.toString().toInt()))
            adapter!!.notifyDataSetChanged()
            nameET.text.clear()
            ageET.text.clear()
        }

        usersLV.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val user = adapter!!.getItem(position)
                val dialog = MyDialog()
                val args = Bundle()
                args.putParcelable("user", user)
                dialog.arguments = args
                dialog.show(supportFragmentManager, "custom")
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exitMenu) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun remove(user: User?) {
        adapter?.remove(user)
    }

}