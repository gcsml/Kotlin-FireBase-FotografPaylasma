package com.gokhancsml.fotografpaylasma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.gokhancsml.fotografpaylasma.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class KullaniciActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        //kullanıcı giriş yaptıysa tekrar sormamak için
        val guncelkullanici= auth.currentUser
        if (guncelkullanici != null){
            val intent = Intent(this,HaberlerActivty::class.java)
            startActivity(intent)
            finish()
        }


    }

    fun girisYap(view: View) {

        auth.signInWithEmailAndPassword(binding.emailText.text.toString(),binding.passwordText.text.toString()).addOnCompleteListener {task ->

           if(task.isSuccessful){

               val guncelkullanici = auth.currentUser!!.email.toString()
               Toast.makeText(this,"Hoşgeldin : ${guncelkullanici}",Toast.LENGTH_LONG).show()

               val intent = Intent(this,HaberlerActivty::class.java)
               startActivity(intent)
               finish()
           }
        }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }




    }

    fun kayitOl(view: View) {

        val email = binding.emailText.text.toString()
        val sifre = binding.passwordText.text.toString()


        auth.createUserWithEmailAndPassword(email, sifre).addOnCompleteListener { task->

            if(task.isSuccessful){
                //diğer activitieye gidelim
                val intent =Intent(this@KullaniciActivity,HaberlerActivty::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { exception->
            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }
        }
    }
