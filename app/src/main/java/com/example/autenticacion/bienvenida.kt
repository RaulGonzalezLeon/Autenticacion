package com.example.autenticacion

import android.content.ContentValues.TAG
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.autenticacion.databinding.ActivityBienvenidaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class bienvenida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBienvenidaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val db = FirebaseFirestore.getInstance()

        /* Añadir un nuev coche conociendo su ID que es la matricula */

        binding.bGuardar.setOnClickListener {
            if( binding.tbMarca.text.isNotEmpty() && binding.tbModelo.text.isNotEmpty() &&
                binding.tbMatricula.text.isNotEmpty() && binding.tbColor.text.isNotEmpty()){
                db.collection("coches").document(binding.tbMatricula.text.toString())
                    .set(mapOf(
                        "color" to binding.tbColor.text.toString(),
                        "marca" to binding.tbMarca.text.toString(),
                        "modelo" to binding.tbModelo.text.toString()
                    ))

            }else{ Toast.makeText(this, "Algun campo esta vacio", Toast.LENGTH_SHORT).show()}
        }

        /* Eliminar un registro sabiendo su ID (matricula) */
        binding.bEliminar.setOnClickListener {
            db.collection("coches")
                .document(binding.tbMatricula.text.toString())
                .delete()
        }

        /*
        binding.bGuardar.setOnClickListener {
            /* Si ningun campo esta vacio */
            if( binding.tbMarca.text.isNotEmpty() && binding.tbModelo.text.isNotEmpty() &&
                binding.tbMatricula.text.isNotEmpty() && binding.tbColor.text.isNotEmpty()){
                db.collection("coches")
                    .add(mapOf(
                        "color" to binding.tbColor.text.toString(),
                        "marca" to binding.tbMarca.text.toString(),
                        "matricula" to binding.tbMatricula.text.toString(),
                        "modelo" to binding.tbModelo.text.toString()))
                    .addOnSuccessListener { documento ->
                        Toast.makeText(this, "Coche añadido con ID: ${documento.id}", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this, "Error en la inserccion", Toast.LENGTH_SHORT).show()
                    }

            }else{ Toast.makeText(this, "Algun campo esta vacio", Toast.LENGTH_SHORT).show()}
        }
        */


        binding.bEditar.setOnClickListener {
            db.collection("coches")
                .whereEqualTo("matricula", binding.tbMatricula.text.toString())
                .get().addOnSuccessListener {
                    it.forEach{
                        binding.tbMarca.setText(it.get("marca") as String?)
                        binding.tbModelo.setText(it.get("modelo") as String?)
                        binding.tbColor.setText(it.get("color") as String?)
                    }
                }
        }

        /*
        binding.bEliminar.setOnClickListener {
            db.collection("coches")
                .get()
                .addOnSuccessListener {
                    it.forEach{
                        it.reference.delete()
                    }
                }
        }
         */

        val extras = intent.extras
        val nombre = extras?.getString("nombreusuario")
        binding.textView.text = "Bienvenido, $nombre"


        binding.bcerrarSesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}