package com.example.lootbyte.UI.Auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lootbyte.UI.MainMenu.Cliente.HomeFragment
import com.example.lootbyte.databinding.FragmentLoginBinding
import com.example.lootbyte.R
import com.example.lootbyte.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // BOTÓN INICIAR SESIÓN
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(requireContext(), "Iniciando sesión...", Toast.LENGTH_SHORT).show()

            // Redirigir al Home y actualizar el BottomNav de MainActivity
            (requireActivity() as? MainActivity)?.let { mainActivity ->
                mainActivity.isLoggedIn = true // <--- Marcamos sesión como iniciada
                mainActivity.findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_nav)
                    .selectedItemId = R.id.nav_inicio
            }
        }

        // ==================== BOTÓN REGISTRARSE ====================
        binding.btnRegister.setOnClickListener {
            Toast.makeText(requireContext(), "Abriendo registro...", Toast.LENGTH_SHORT).show()

            // Versión más fuerte de navegación
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RegisterFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
