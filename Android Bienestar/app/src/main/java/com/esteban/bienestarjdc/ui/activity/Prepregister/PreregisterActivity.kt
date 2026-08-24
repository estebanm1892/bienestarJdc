package com.esteban.bienestarjdc.ui.activity.Prepregister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.esteban.bienestarjdc.data.Prepregistrer
import com.esteban.bienestarjdc.databinding.ActivityPreregisterBinding
import com.esteban.bienestarjdc.network.MyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreregisterActivity : AppCompatActivity() {

    lateinit var option : Spinner
    private lateinit var binding: ActivityPreregisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreregisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Preinscripción"

        val context = this

        option = binding.semester

        val options = arrayOf("Docente", "Administrativo", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X")

        option.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options)

        intent.extras?.let {
            if (it.containsKey(PUB_ITEM_ID)){
                val idActivitie: Int = intent.getIntExtra(PUB_ITEM_ID, 0)

                option.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        Toast.makeText(context, "Por favor selecciona un semestre.", Toast.LENGTH_SHORT).show()
                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        binding.btnAdd.setOnClickListener {
                            val newPreregister = Prepregistrer()
                            newPreregister.name = binding.name.text.toString()
                            newPreregister.document = binding.document.text.toString()
                            newPreregister.email = binding.email.text.toString()
                            newPreregister.phone = binding.phone.text.toString()
                            /*
                            newPreregister.activity_id = idActivitie
                             */
                            newPreregister.academic_program = binding.academicProgram.text.toString()
                            newPreregister.semester = options.get(position)

                            if (validateInfo(binding.name) && validateInfo(binding.document) && validateInfo(binding.email) && validateInfo(binding.academicProgram) && validateInfo(binding.phone)){
                                val apiService = MyApi.RetrofitObject()
                                val requestCall = apiService.addPreregister(newPreregister, idActivitie)

                                requestCall.enqueue(object : Callback<Prepregistrer> {
                                    override fun onFailure(call: Call<Prepregistrer>, t: Throwable) {
                                        Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show()
                                    }

                                    override fun onResponse(
                                        call: Call<Prepregistrer>,
                                        response: Response<Prepregistrer>
                                    ) {
                                        if (response.isSuccessful) {
                                            finish()
                                            Toast.makeText(context, "Has realizado tu preiniscripción correctamente. " +
                                                    "Acércate a la Unidad de Bienestar Universitario para más información.", Toast.LENGTH_LONG).show()
                                        } else {
                                            Toast.makeText(context, "Error al ingresar la información.", Toast.LENGTH_LONG).show()
                                        }
                                    }

                                })
                            }else{
                                Toast.makeText(context, "Por favor ingresa todos los datos.", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }

                }
            }
        }

    }

    private fun validateInfo(data: EditText):Boolean{
        return !data.text.isNullOrEmpty()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val PUB_ITEM_ID = "id"
    }
}
