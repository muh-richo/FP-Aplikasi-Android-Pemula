package com.example.submissionmakanan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private lateinit var ivFoto: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvDescription: TextView

    private lateinit var rvFoods: RecyclerView
    private var list = ArrayList<Food>()

    companion object {
        const val EXTRA_NAME = "extra_name"
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ivFoto = findViewById(R.id.iv_food)
        tvName = findViewById(R.id.tv_food_name)
        tvDescription = findViewById(R.id.tv_food_description)

        rvFoods = findViewById(R.id.rv_food_column)

        val food =
            if (Build.VERSION.SDK_INT >= 33) {
                intent.getParcelableExtra<Food>(EXTRA_NAME, Food::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra<Food>(EXTRA_NAME)
            }

        getData(food)
        list.addAll(listFoods)
        showRecyclerList()
    }

    private fun getData(food: Food?) {
        if (food != null) {
            Glide.with(this)
                .load(food.photo)
                .into(ivFoto)
            tvName.text = food.name
            tvDescription.text = food.description
        }
    }

    private val listFoods: ArrayList<Food>
        get() {
            val dataName = resources.getStringArray(R.array.data_name)
            val dataDescription = resources.getStringArray(R.array.data_description)
            val dataPhoto = resources.getStringArray(R.array.data_photo)
            val listFood = ArrayList<Food>()
            for (i in dataName.indices) {
                val food = Food(dataName[i], dataDescription[i], dataPhoto[i])
                listFood.add(food)
            }
            return listFood
        }

    private fun showSelectedFood(food: Food) {
        val intentDetail = Intent(this, DetailActivity::class.java)
        intentDetail.putExtra(DetailActivity.EXTRA_NAME, food)
        startActivity(intentDetail)
    }

    private fun showRecyclerList() {
        rvFoods.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listFoodAdapter = ListDetailFoodAdapter(list)
        rvFoods.adapter = listFoodAdapter

        listFoodAdapter.setOnItemClickCallback(object : ListDetailFoodAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Food) {
                showSelectedFood(data)
            }
        })
    }
}