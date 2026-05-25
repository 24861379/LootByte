package com.example.lootbyte.UI.Auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.lootbyte.MainActivity
import com.example.lootbyte.Model.Usuario
import com.example.lootbyte.R
import com.example.lootbyte.Repository.RolRepository
import com.example.lootbyte.Repository.UsuarioRepository
import com.example.lootbyte.SupabaseClient
import com.example.lootbyte.databinding.FragmentRegisterBinding
import com.example.lootbyte.utils.PasswordUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val usuarioRepository = UsuarioRepository()
    private val rolRepository = RolRepository()

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
            val password = binding.etPassword.text.toString().trim()
            val address = binding.etAddress.text.toString().trim()
            val city = binding.etCity.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || city.isEmpty() || phone.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val roles = rolRepository.obtenerRoles()
                    val rolCliente = roles.find { it.nombre_rol == "Cliente" }
                    
                    if (rolCliente == null) {
                        Toast.makeText(requireContext(), "Error: El rol 'Cliente' no existe en la base de datos", Toast.LENGTH_LONG).show()
                        return@launch
                    }

                    SupabaseClient.client.auth.signUpWith(Email) {
                        this.email = email
                        this.password = password
                    }
                   val authId = SupabaseClient.client.auth.currentUserOrNull()?.id

                    val passwordHash = PasswordUtils.hashPassword(password)
                    val nuevoUsuario = Usuario(
                        id_usuario = authId,
                        nombre_completo = name,
                        correo = email,
                        password_hash = passwordHash,
                        celular = phone,
                        direccion = address,
                        ciudad = city,
                        id_rol_FK = rolCliente.id_rol
                    )

                    usuarioRepository.insertUsuario(nuevoUsuario)

                    Toast.makeText(requireContext(), "Cuenta creada exitosamente", Toast.LENGTH_SHORT).show()

                    (requireActivity() as? MainActivity)?.let { mainActivity ->
                        mainActivity.findViewById<BottomNavigationView>(R.id.bottom_nav)
                            .selectedItemId = R.id.nav_inicio
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Error al registrarse: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
