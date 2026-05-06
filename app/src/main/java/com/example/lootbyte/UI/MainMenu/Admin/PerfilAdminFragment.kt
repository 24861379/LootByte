package com.example.lootbyte.UI.MainMenu.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lootbyte.R

class PerfilAdminFragment : Fragment() {
//esto se lo dejo a su criterio
    override fun onResume() {
        super.onResume()
        (activity as? AdminActivity)
            ?.actualizarHeader(R.layout.header_simple)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil_admin, container, false)
    }


}