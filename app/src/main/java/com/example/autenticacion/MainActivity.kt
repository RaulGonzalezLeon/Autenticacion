package com.example.autenticacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.autenticacion.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    public lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.IniciarSesion.setOnClickListener{
            login()
        }

        binding.registrarse.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }
    private fun login(){
        /* Si el correo y el password no son campos vacios */
        if (binding.Correo.text.isNotEmpty() && binding.passwd.text.isNotEmpty()){
            /* Iniciamos sesion con el metodo signIn y enviamos a FireBase el correo y la contraseña */
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                binding.Correo.text.toString(),
                binding.passwd.text.toString()
            )

                .addOnCompleteListener {
                    /* Si la autenticacion tuvo exito */
                    if (it.isSuccessful){
                        val intent = Intent(this,bienvenida::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "Correo o password incorrecto", Toast.LENGTH_SHORT).show()
                    }
                }
        }else {Toast.makeText(this, "Algun campo esta vacio", Toast.LENGTH_SHORT).show()}
    }
}