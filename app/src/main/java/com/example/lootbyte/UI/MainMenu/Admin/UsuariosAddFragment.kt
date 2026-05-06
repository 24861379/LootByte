package com.example.lootbyte.UI.MainMenu.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.lootbyte.R
import com.example.lootbyte.UI.SeccionPagos.MetodoPagoFragment

class UsuariosAddFragment : Fragment() {

    override fun onResume() {
        super.onResume()

        (activity as? AdminActivity)
            ?.actualizarHeader(R.layout.header_simple)

        requireActivity()
            .findViewById<TextView>(R.id.tvTitulo)
            ?.text = "Crear usuario"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titulo = requireActivity().findViewById<TextView>(R.id.tvTitulo)
        titulo?.text = "Crear usuario"
//navega a MetodoPagoFragment
//        view.findViewById<View>(R.id.btn_Metodo_pago).setOnClickListener {
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, MetodoPagoFragment())
//                .addToBackStack(null)
//                .commit()
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_usuarios_add, container, false)
    }


}