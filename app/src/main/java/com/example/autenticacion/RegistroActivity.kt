package com.example.autenticacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.autenticacion.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = FirebaseFirestore.getInstance()
        title = "Nuevo Usuario"
        binding.bRegistrarse.setOnClickListener {
            /* Comprobamos que ningun campo este vacio */
            if(binding.regEmail.text.isNotEmpty() && binding.regPassword.text.isNotEmpty()
                && binding.regNombre.text.isNotEmpty() && binding.regApellidos.text.isNotEmpty()){
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        binding.regEmail.text.toString(),binding.regPassword.text.toString()
                    ).addOnCompleteListener {
                        if(it.isSuccessful){ /* Si se han registrado los datos satisfactoriamente */

                            db.collection("usuarios").document(binding.regEmail.text.toString())
                                .set(mapOf(
                                    "nombre" to binding.regNombre.text.toString(),
                                    "apellido" to binding.regApellidos.text.toString(),
                                ))


                            /* Accedemos a la pantalla InicioActivity para dar la biencenida al usuario */
                                val intent = Intent(this,bienvenida::class.java).apply{
                                    putExtra("nombreusuario",binding.regNombre.text.toString())
                                }
                            startActivity(intent)
                        }else{Toast.makeText(this,"Error al registro del nuevo usuario", Toast.LENGTH_SHORT).show()}
                    }

            }else {Toast.makeText(this, "Algun campo esta vacio", Toast.LENGTH_SHORT).show()}
        }
    }
}