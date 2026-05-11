package com.example.lootbyte.UI.MainMenu.Cliente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.lootbyte.MainActivity
import com.example.lootbyte.R
import com.example.lootbyte.databinding.FragmentHomeBinding
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar Tabs
        val tabLayout = requireActivity().findViewById<TabLayout>(R.id.tab_categorias)
        tabLayout?.let {
            if (it.tabCount == 0) {
                it.addTab(it.newTab().setText("Todo"))
                it.addTab(it.newTab().setText("Mouse"))
                it.addTab(it.newTab().setText("Teclados"))
                it.addTab(it.newTab().setText("Pantallas"))
                it.addTab(it.newTab().setText("Audífonos"))
            }
        }

        // Configurar clic en producto para navegar a ProductoFragment
        binding.cardProducto1.setOnClickListener {
            (activity as? MainActivity)?.let { mainActivity ->
                // Usamos la función cargarFragment de MainActivity para cambiar al producto
                mainActivity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ProductoFragment())
                    .addToBackStack(null)
                    .commit()
                
                // Actualizar el header para el producto
                actualizarHeaderProducto(mainActivity)
            }
        }
    }

    private fun actualizarHeaderProducto(mainActivity: MainActivity) {
        val headerContainer = mainActivity.findViewById<ViewGroup>(R.id.header_container)
        headerContainer?.removeAllViews()
        mainActivity.layoutInflater.inflate(R.layout.header_simple, headerContainer, true)
        
        // Configurar botón volver del header simple
        val btnBack = headerContainer?.findViewById<ImageView>(R.id.btn_back)
        btnBack?.setOnClickListener {
            mainActivity.supportFragmentManager.popBackStack()
            // Al volver, el BottomNav debería sincronizarse si es necesario, 
            // pero como MainActivity maneja el estado, aquí solo volvemos.
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}