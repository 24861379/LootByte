package com.example.lootbyte.UI.MainMenu.Admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lootbyte.R
import com.example.lootbyte.databinding.FragmentPedidosBinding

class PedidosFragment : Fragment() {

    private var _binding: FragmentPedidosBinding? = null
    private val binding get() = _binding!!

//    override fun onResume() {
//        super.onResume()
//        (activity as? AdminActivity)
//            ?.actualizarHeader(R.layout.header_simple)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPedidosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Datos de ejemplo para Admin
        val listaPedidos = listOf(
            Pedido(1, "Usuario Demo", "Ver pedido(s)", 3),
            Pedido(2, "Usuario Demo", "Ver pedido(s)", 1),
            Pedido(3, "Usuario Demo", "Ver pedido(s)", 5),
            Pedido(4, "Usuario Demo", "Ver pedido(s)", 2),
            Pedido(5, "Usuario Demo", "Ver pedido(s)", 4)
        )

        val adapter = PedidosAdapter(listaPedidos)

        binding.recyclerPedidos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerPedidos.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}