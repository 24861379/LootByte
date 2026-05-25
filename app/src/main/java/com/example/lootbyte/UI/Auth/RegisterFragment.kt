package com.example.lootbyte.UI.Auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lootbyte.MainActivity
import com.example.lootbyte.R
import com.example.lootbyte.databinding.FragmentRegisterBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val name = binding.etFullName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()

            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, completa los campos obligatorios", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Cuenta creada para $name", Toast.LENGTH_SHORT).show()
                
                // Al registrarse, lo mandamos al Home y sincronizamos el menú
                (requireActivity() as? MainActivity)?.let { mainActivity ->
                    mainActivity.findViewById<BottomNavigationView>(R.id.bottom_nav)
                        .selectedItemId = R.id.nav_inicio
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
