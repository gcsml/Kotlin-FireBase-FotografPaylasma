package com.gokhancsml.fotografpaylasma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.gokhancsml.fotografpaylasma.databinding.ActivityHaberlerActivtyBinding
import com.gokhancsml.fotografpaylasma.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.rpc.context.AttributeContext.Auth

class HaberlerActivty : AppCompatActivity() {
    private lateinit var binding: ActivityHaberlerActivtyBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseFirestore
    private lateinit var recyclerViewAdapter: HaberRecylerAdapter

    var postlistesi = ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHaberlerActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        verileriAl()

        var layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = HaberRecylerAdapter(postlistesi)
        binding.recyclerView.adapter = recyclerViewAdapter

    }

    fun verileriAl(){
        database.collection("post").orderBy("tarih",Query.Direction.DESCENDING).addSnapshotListener { snapshot, exception ->
            if(exception != null ){
                Toast.makeText(this,exception.localizedMessage,Toast.LENGTH_LONG )
            }else {
                if (snapshot != null){
                    if (!snapshot.isEmpty){

                       val documents = snapshot.documents

                        postlistesi.clear()

                        for (document in documents){
                           val kullaniciEmail = document.get("kullaniciemail") as String
                           val kullaniciYorumu = document.get("kullaniciyorum") as String
                           val gorselUrl = document.get("gorselurl") as String

                            val indirilenPost = Post(kullaniciEmail,kullaniciYorumu,gorselUrl)
                            postlistesi.add(indirilenPost)
                        }
                            recyclerViewAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.secenekelr_menusu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.fotograf_paylas){
            //fotoğraf paylaşma aktivitesine gidilecek

            val intent = Intent(this,FotografPaylasmaActivity::class.java)
            startActivity(intent)

        }else if (item.itemId == R.id.cikis_yap){
            auth.signOut()
            val intent = Intent(this,KullaniciActivity::class.java)
            startActivity(intent)
            finish()
        }


        return super.onOptionsItemSelected(item)
    }
}