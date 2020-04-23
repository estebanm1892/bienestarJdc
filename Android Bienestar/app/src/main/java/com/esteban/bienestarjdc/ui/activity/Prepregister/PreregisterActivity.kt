package com.esteban.bienestarjdc.ui.activity.Prepregister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.data.Prepregistrer
import com.esteban.bienestarjdc.network.MyApi
import kotlinx.android.synthetic.main.activity_preregister.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreregisterActivity : AppCompatActivity() {

    lateinit var option : Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preregister)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Preinscripción"

        val context = this

        option = findViewById(R.id.semester)

        val options = arrayOf("I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X")

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
                        btn_add.setOnClickListener {
                            val newPreregister = Prepregistrer()
                            newPreregister.name = name.text.toString()
                            newPreregister.document = document.text.toString()
                            newPreregister.email = email.text.toString()
                            /*
                            newPreregister.activity_id = idActivitie
                             */
                            newPreregister.academic_program = academic_program.text.toString()
                            newPreregister.semester = options.get(position)

                            if (validateInfo(name) && validateInfo(document) && validateInfo(email) && validateInfo(academic_program)){
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
