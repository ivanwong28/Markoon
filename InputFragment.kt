package e.windows10.markoon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class InputFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser
        val inputimage = view.findViewById<ImageView>(R.id.input_image)
        super.onViewCreated(view, savedInstanceState)
        inputimage.setOnClickListener {
            intentgalery()
        }

        val etnamatoko = view.findViewById<EditText>(R.id.namatokoinput)
        val etalamat = view.findViewById<EditText>(R.id.alamattokoinput)
        val eturlalamat = view.findViewById<EditText>(R.id.urlalamatinput)
        val btnupload = view.findViewById<Button>(R.id.button_upload)

        btnupload.setOnClickListener{
            val namatoko = etnamatoko.text.toString().trim()
            val alamat= etalamat.text.toString().trim()
            val urlalamat = eturlalamat.text.toString().trim()

            if (namatoko.isEmpty()){
                etnamatoko.error = "Isi Nama Tokonya!"
                return@setOnClickListener
            }

            if (alamat.isEmpty()){
                etalamat.error = "Isi Nama Tokonya!"
                return@setOnClickListener
            }

            if (urlalamat.isEmpty()){
                eturlalamat.error = "Isi Nama Tokonya!"
                return@setOnClickListener
            }

            val ref = FirebaseDatabase.getInstance().getReference("Toko")
            val tokoid = ref.push().key
            val toko = Toko(tokoid,namatoko,alamat, urlalamat)

            if (tokoid != null) {
                ref.child(tokoid).setValue(toko).addOnCompleteListener {
                    Toast.makeText(context,"Data berhasil ditambahkan",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


    private fun intentgalery() {

    }


}