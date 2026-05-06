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
import java.text.NumberFormat
import java.util.Locale


class MetodoTarjetaFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titulo = requireActivity().findViewById<TextView>(R.id.tvTitulo)
        titulo?.text = "Volver al método de pago"

        val cuotas = listOf("1 cuota", "2 cuotas", "3 cuotas", "4 cuotas", "5 cuotas", "6 cuotas", "7 cuotas", "8 cuotas", "9 cuotas", "10 cuotas", "11 cuotas", "12 cuotas")

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            cuotas
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = view.findViewById<Spinner>(R.id.spinerCuotas)
        spinner.adapter = adapter


        val total = arguments?.getDouble("total_compra")?:0.0
        val formatoCOP = NumberFormat.getNumberInstance(Locale("es", "CO"))
        val tvTotal = view.findViewById<TextView>(R.id.tv_total_pagar)
        tvTotal.text = "$ ${formatoCOP.format(total)}"

        //redirige al otro fragment
        view.findViewById<View>(R.id.btn_Realizar_pago).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PagoExitosoFragment())
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
        return inflater.inflate(R.layout.fragment_metodo_tarjeta, container, false)
    }

}