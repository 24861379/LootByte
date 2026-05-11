package com.example.lootbyte.UI.SeccionPagos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.example.lootbyte.R
class DatosDeEnvioFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titulo = requireActivity().findViewById<TextView>(R.id.tvTitulo)
        titulo?.text = "Volver al carrito"
//navega a MetodoPagoFragment
        view.findViewById<View>(R.id.btn_Metodo_pago).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MetodoPagoFragment())
                .addToBackStack(null)
                .commit()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_datos_de_envio, container, false)
    }


}