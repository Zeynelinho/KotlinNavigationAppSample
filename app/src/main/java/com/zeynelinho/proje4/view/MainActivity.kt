package com.zeynelinho.proje4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.zeynelinho.proje4.R
import com.zeynelinho.proje4.adapter.PlaceAdapter
import com.zeynelinho.proje4.databinding.ActivityMainBinding
import com.zeynelinho.proje4.model.Place
import com.zeynelinho.proje4.roomDB.PlaceDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val database = Room.databaseBuilder(applicationContext,PlaceDatabase::class.java,"Places").build()
        val dao = database.placeDao()

        compositeDisposable.add(dao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))



    }

    private fun handleResponse (placeList : List<Place>) {

        val placeAdapter = PlaceAdapter(placeList)
        binding.recyclerView.adapter = placeAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {


        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.place_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.add_place) {

            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("info"  ,"new")
            startActivity(intent)

        }


        return super.onOptionsItemSelected(item)
    }


}