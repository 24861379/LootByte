package com.example.lootbyte.UI.SeccionPagos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.example.lootbyte.R


class MetodoPagoFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titulo = requireActivity().findViewById<TextView>(R.id.tvTitulo)
        titulo?.text = "Volver al envio"

        val btnpse = view.findViewById<View>(R.id.btn_Pago_PSE)
        val spinner = view.findViewById<Spinner>(R.id.spinner_PSE_Bancos)
        val bancos = listOf(
            "Seleccione un banco",
            "Bancolombia",
            "Davivienda",
            "Daviplata",
            "BBVA",
            "Banco de Bogotá",
            "Banco Popular",
            "Banco Caja Social",
            "Scotiabank Colpatria",
            "Banco AV Villas",
            "Nequi"
        )

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, bancos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        btnpse.setOnClickListener {
            spinner.visibility = View.VISIBLE
        }

        view.findViewById<View>(R.id.btn_tarjeta_credito).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MetodoTarjetaFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_metodo_pago, container, false)
    }

}