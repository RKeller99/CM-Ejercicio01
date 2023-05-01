package com.rkeller.ejercicio01

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.rkeller.ejercicio01.databinding.ActivityMuestraFormularioBinding

class MuestraFormulario : AppCompatActivity() {

    private lateinit var binding: ActivityMuestraFormularioBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMuestraFormularioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        if (bundle != null) {

            var persona: Persona? = null

            persona = if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
                bundle.getParcelable<Persona>("persona", Persona::class.java)
            }else{
                bundle.getParcelable<Persona>("persona")
            }

            if(persona != null){
                binding.tvNombrecompleto2.text = "${persona.nombre} ${persona.apellidos}"
                //Realmente nunca se va a usar el singular por la restricción que le di al DatePicker
                binding.tvEdad2.text = resources.getQuantityString(R.plurals.edadRes,persona.edad,persona.edad)
                //binding.tvEdad2.text = "${persona.edad} "
                binding.tvSignozodiacal2.text = "${persona.signo}"
                binding.tvSignozodiacalchino2.text = "${persona.signoChino}"
                binding.tvCorreo2.text = "${persona.correo}"
                binding.tvNumCuenta2.text = "${persona.numCuenta}"


                if (persona.carrera==1){
                    binding.ivCarrera.setImageResource(R.drawable.ing_aero)
                }else if (persona.carrera==2){
                    binding.ivCarrera.setImageResource(R.drawable.ing_ambiental)
                }else if (persona.carrera==3){
                    binding.ivCarrera.setImageResource(R.drawable.ing_civil)
                }else if (persona.carrera==4){
                    binding.ivCarrera.setImageResource(R.drawable.ing_compu)
                }else if (persona.carrera==5){
                    binding.ivCarrera.setImageResource(R.drawable.ing_electrica)
                }else if (persona.carrera==6){
                    binding.ivCarrera.setImageResource(R.drawable.ing_geofisica)
                }else if (persona.carrera==7){
                    binding.ivCarrera.setImageResource(R.drawable.ing_geologica)
                }else if (persona.carrera==8){
                    binding.ivCarrera.setImageResource(R.drawable.ing_geomatica)
                }else if (persona.carrera==9){
                    binding.ivCarrera.setImageResource(R.drawable.ing_industrial)
                }else if (persona.carrera==10){
                    binding.ivCarrera.setImageResource(R.drawable.ing_mecanica)
                }else if (persona.carrera==11){
                    binding.ivCarrera.setImageResource(R.drawable.ing_mecatronica)
                }else if (persona.carrera==12){
                    binding.ivCarrera.setImageResource(R.drawable.ing_minas)
                }else if (persona.carrera==13){
                    binding.ivCarrera.setImageResource(R.drawable.ing_petrolera)
                }else if (persona.carrera==14){
                    binding.ivCarrera.setImageResource(R.drawable.ing_sistbiomedicos)
                }else if (persona.carrera==15){
                    binding.ivCarrera.setImageResource(R.drawable.ing_telecom)
                }else{
                    binding.ivCarrera.setImageResource(R.drawable.gifrobot)
                }
            }
        }

    }

    fun clickRegresar(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}