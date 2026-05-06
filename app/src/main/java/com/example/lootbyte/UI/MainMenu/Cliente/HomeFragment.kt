package com.example.lootbyte.UI.MainMenu.Cliente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lootbyte.R
import com.google.android.material.tabs.TabLayout


class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Buscamos en la Actividad porque el header está en el header_container de MainActivity
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
    }


}