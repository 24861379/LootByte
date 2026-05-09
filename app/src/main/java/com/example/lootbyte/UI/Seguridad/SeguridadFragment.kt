package com.example.lootbyte.UI.MainMenu.Cliente.Seguridad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lootbyte.R

// Imports CORRECTOS
import com.example.lootbyte.UI.MainMenu.Cliente.Seguridad.CambiarContrasenaFragment
import com.example.lootbyte.UI.MainMenu.Cliente.Seguridad.VerificacionDosPasosFragment
import com.example.lootbyte.UI.MainMenu.Cliente.Seguridad.AlertasSeguridadFragment

class SeguridadFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_seguridad, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cambiar Contraseña
        view.findViewById<View>(R.id.cardCambiarContrasena)?.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CambiarContrasenaFragment())
                .addToBackStack(null)
                .commit()
        }

        // Verificación de dos pasos
        view.findViewById<View>(R.id.cardVerificacionDosPasos)?.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, VerificacionDosPasosFragment())
                .addToBackStack(null)
                .commit()
        }

        // Alertas de seguridad
        view.findViewById<View>(R.id.cardAlertasSeguridad)?.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AlertasSeguridadFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}