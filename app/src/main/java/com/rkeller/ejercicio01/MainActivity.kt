package com.rkeller.ejercicio01

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.rkeller.ejercicio01.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ArrayAdapter.createFromResource(
            this,
            R.array.ListaCarreras,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter

        }

        binding.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("LOGTAG", "El item seleccionado tiene la posición $position")
            }
            override fun onNothingSelected(parent: AdapterView<*>){

            }
        }

    }


    private fun CorreoValido(mail: CharSequence) = (!TextUtils.isEmpty(mail) && Patterns.EMAIL_ADDRESS.matcher(mail).matches())


    fun clickContinuar(view: View) {

        if (validarDatos()) {
            Toast.makeText(this, resources.getString(R.string.DatosCorrectos), Toast.LENGTH_LONG)
                .show()
            val intent = Intent(this, MuestraFormulario::class.java)

            val bundle = Bundle()
            val persona = Persona(binding.etNombre.text.toString(),binding.etApellidos.text.toString(),calculaEdad(binding.etDate.text.toString()),calcularSignoZodiacal(binding.etDate.text.toString()),calcularSignoZodiacoChino(binding.etDate.text.toString()),binding.etCorreo.text.toString(),binding.etNumCuenta.text.toString().toInt(),binding.spinner.selectedItemPosition.toString().toInt())

            bundle.putParcelable("persona", persona)
            intent.putExtras(bundle)
            startActivity(intent)

        } else {
            Toast.makeText(this, resources.getString(R.string.DatosIncorrectos), Toast.LENGTH_LONG)
                .show()
        }
    }

    //ChatGPT
    private fun calculaEdad(FechaNacimiento: String): Int{
        val fechaNac: Date = SimpleDateFormat("dd/MM/yyyy").parse(FechaNacimiento)
        val calendarFechaNac = Calendar.getInstance()
        calendarFechaNac.time = fechaNac
        val calendarHoy = Calendar.getInstance()
        var edad = calendarHoy.get(Calendar.YEAR) - calendarFechaNac.get(Calendar.YEAR)
        if (calendarHoy.get(Calendar.DAY_OF_YEAR) < calendarFechaNac.get(Calendar.DAY_OF_YEAR)) {
            edad--
        }
        return edad
    }

    //Chat GPT
    fun calcularSignoZodiacal(fecha: String): String? {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaNacimiento = formatoFecha.parse(fecha)
        val calendar = Calendar.getInstance().apply {
            time = fechaNacimiento!!
        }
        val dia = calendar.get(Calendar.DAY_OF_MONTH)
        val mes = calendar.get(Calendar.MONTH)
        val signos = resources.getStringArray(R.array.SignoZodiaco)
        val diasInicioSigno = intArrayOf(20, 19, 21, 20, 21, 21, 22, 23, 23, 23, 22, 22)
        var indiceSigno = mes
        if (dia < diasInicioSigno[mes]) {
            indiceSigno--
            if (indiceSigno < 0) {
                indiceSigno = 11
            }
        }
        return signos[indiceSigno]
    }

    //chat GPT
    fun calcularSignoZodiacoChino(fecha: String): String? {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaNacimiento = formatoFecha.parse(fecha)
        val calendar = Calendar.getInstance().apply {
            time = fechaNacimiento!!
        }
        val year = calendar.get(Calendar.YEAR)
        val signos = resources.getStringArray(R.array.SignoZodiacoChino)
        val cicloZodiacoChino = (year - 1900) % 12
        return signos[cicloZodiacoChino]
    }



    private fun validarDatos(): Boolean {
        if (binding.etNombre.text.isEmpty()) {
            binding.etNombre.error = resources.getString(R.string.ValorRequerido)
            Toast.makeText(this, resources.getString(R.string.NombreValido), Toast.LENGTH_LONG)
                .show()
            return false
        } else if (binding.etApellidos.text.isEmpty()) {
            binding.etApellidos.error = resources.getString(R.string.ValorRequerido)
            Toast.makeText(this, resources.getString(R.string.ApellidosValido), Toast.LENGTH_LONG)
                .show()
            return false
        } else if (binding.etDate.text.isEmpty()){
            binding.etDate.error = resources.getString(R.string.ValorRequerido)
            Toast.makeText(
                this,
                resources.getString(R.string.FechaNacimientoValido),
                Toast.LENGTH_LONG
            ).show()
            return false
        } else if (!CorreoValido(binding.etCorreo.text)) {
            binding.etCorreo.error = resources.getString(R.string.CorreoValido)
            Toast.makeText(this, resources.getString(R.string.CorreoValido), Toast.LENGTH_LONG)
                .show()
            return false
        } else if (binding.etNumCuenta.text.isEmpty() or (binding.etNumCuenta.text.length != 9)) {
            binding.etNumCuenta.error = resources.getString(R.string.ValorRequerido)
            Toast.makeText(this, resources.getString(R.string.NumCuentaValido), Toast.LENGTH_LONG)
                .show()
            return false
        } else if (binding.spinner.selectedItemPosition == 0) {
            Toast.makeText(this, resources.getString(R.string.CarreraValido), Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    fun clickCalendario(view: View) {
        val Dialogfecha = DatePickerFragment{year, month, day -> mostrarResultado(year, month, day)}


        Dialogfecha.show(supportFragmentManager, "DatePicker")
    }

    private fun mostrarResultado(year: Int, month: Int, day: Int){
        binding.etDate.setText("$day/$month/$year")
    }

    class DatePickerFragment(val listener:(year:Int, month:Int, day:Int) -> Unit): DialogFragment(), DatePickerDialog.OnDateSetListener{

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog{
            val c = Calendar.getInstance()
            var year = c.get(Calendar.YEAR)
            var month = c.get(Calendar.MONTH)
            var day = c.get(Calendar.DAY_OF_MONTH)
            //var dayOfYear = c.get(Calendar.DAY_OF_YEAR)

            //val datePickerDialog = DatePickerDialog(requireActivity(),R.style.datePickerTheme,this, year, month, day)
            val datePickerDialog = DatePickerDialog(requireActivity(),this, year, month, day)
            val calendarMaxDate = Calendar.getInstance().apply {
                set(2023, 0, 1)}
            val maxDate = calendarMaxDate.timeInMillis
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.datePicker.maxDate = maxDate
            val calendarMinDate = Calendar.getInstance().apply {
                set(1900, 0, 1)}
            val minDate = calendarMinDate.timeInMillis
            datePickerDialog.datePicker.minDate = minDate

            return datePickerDialog

        }

        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
            listener(year,month+1,day)
        }
    }
}