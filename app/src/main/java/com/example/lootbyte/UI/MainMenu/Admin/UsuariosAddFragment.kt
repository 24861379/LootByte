package com.example.lootbyte.UI.MainMenu.Admin

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.lootbyte.Model.Rol
import com.example.lootbyte.Model.Usuario
import com.example.lootbyte.R
import com.example.lootbyte.Repository.RolRepository
import com.example.lootbyte.Repository.UsuarioRepository
import com.example.lootbyte.SupabaseClient
import com.example.lootbyte.utils.PasswordUtils
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.launch
import java.util.UUID

class UsuariosAddFragment : Fragment() {

    private lateinit var spRoles: Spinner
    private lateinit var listaRoles: List<Rol>

    private val usuarioRepository = UsuarioRepository()
    private val rolRepository = RolRepository()
    private var usuarioEditar: Usuario? = null

    private var imageUri: Uri? = null

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) imageUri = uri
    }

    override fun onResume() {
        super.onResume()
        (activity as? AdminActivity)?.actualizarHeader(R.layout.header_simple)
        val titulo = if (arguments?.getString("id_usuario") != null) "Editar usuario" else "Crear usuario"
        requireActivity().findViewById<TextView>(R.id.tvTitulo)?.text = titulo
    }

    private fun cargarRoles() {
        lifecycleScope.launch {
            try {
                listaRoles = rolRepository.obtenerRoles().filter {
                    it.nombre_rol == "Administrador" || it.nombre_rol == "Operador"
                }
                val nombresRoles = listaRoles.map { it.nombre_rol }
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombresRoles)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spRoles.adapter = adapter

                usuarioEditar?.let { usuario ->
                    val posicion = listaRoles.indexOfFirst { it.id_rol == usuario.id_rol_FK }
                    if (posicion != -1) spRoles.setSelection(posicion)
                }
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    private suspend fun subirImagen(uri: Uri): String {
        val bytes = requireContext().contentResolver.openInputStream(uri)?.readBytes()
        val nombreArchivo = "${UUID.randomUUID()}.jpg"
        SupabaseClient.client.storage.from("img_usuarios").upload(path = nombreArchivo, data = bytes!!)
        return SupabaseClient.client.storage.from("img_usuarios").publicUrl(nombreArchivo)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_usuarios_add, container, false)
        spRoles = view.findViewById(R.id.sp_rol_usuario)
        cargarRoles()
        val btnFoto = view.findViewById<View>(R.id.btn_foto_usuario)
        btnFoto.setOnClickListener { pickImage.launch("image/*") }
        val btnAgregar = view.findViewById<View>(R.id.btn_agregar_usuario)

        // EDITAR
        val idUsuario =
            arguments?.getString("id_usuario")

        if (idUsuario != null) {
            lifecycleScope.launch {
                try {
                    usuarioEditar = usuarioRepository.obtenerUsuarios().find { it.id_usuario == idUsuario }
                    usuarioEditar?.let { usuario ->
                        view.findViewById<EditText>(R.id.et_Nombre_completo_usuario).setText(usuario.nombre_completo)
                        view.findViewById<EditText>(R.id.et_email_usuario).setText(usuario.correo)
                        view.findViewById<EditText>(R.id.et_phone_usuario).setText(usuario.celular)
                        view.findViewById<EditText>(R.id.et_Address_usuario).setText(usuario.direccion)
                        view.findViewById<EditText>(R.id.et_city_usuario).setText(usuario.ciudad)
                    }
                } catch (e: Exception) { e.printStackTrace() }
            }
        }

        btnAgregar.setOnClickListener {

            val nombre = view.findViewById<EditText>(R.id.et_Nombre_completo_usuario).text.toString()
            val correo = view.findViewById<EditText>(R.id.et_email_usuario).text.toString()
            val password = view.findViewById<EditText>(R.id.et_password_usuario).text.toString()
            val celular = view.findViewById<EditText>(R.id.et_phone_usuario).text.toString()
            val direccion = view.findViewById<EditText>(R.id.et_Address_usuario).text.toString()
            val ciudad = view.findViewById<EditText>(R.id.et_city_usuario).text.toString()
            if (listaRoles.isEmpty()){
                Toast.makeText(requireContext(), "Cargando roles...", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val rolSeleccionado = listaRoles[spRoles.selectedItemPosition]

            if (
                nombre.isEmpty()
                || correo.isEmpty()
                || celular.isEmpty()
                || direccion.isEmpty()
                || ciudad.isEmpty()
            ) {

                Toast.makeText(
                    requireContext(),
                    "Complete todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
                try {
                    val passwordEncriptado =
                        if (password.isNotEmpty()) {
                            PasswordUtils.hashPassword(password)
                        }else{
                            usuarioEditar?.password_hash ?: ""
                        }
                    val fotoUrl =
                        if (imageUri != null) {
                            subirImagen(imageUri!!)
                        } else {
                            usuarioEditar?.foto_perfil ?: ""
                        }

                    // CREAR
                    if (usuarioEditar == null) {
                        if (password.isEmpty()){
                            Toast.makeText(
                                requireContext(),
                                "Ingrese una contraseña",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@launch
                        }

                        if (imageUri == null) {

                            Toast.makeText(
                                requireContext(),
                                "Seleccione una imagen",
                                Toast.LENGTH_SHORT
                            ).show()

                            return@launch
                        }
                        val usuario = Usuario(
                            nombre_completo = nombre,
                            correo = correo,
                            password_hash = passwordEncriptado,
                            celular = celular,
                            direccion = direccion,
                            ciudad = ciudad,
                            foto_perfil = fotoUrl,
                            id_rol_FK = rolSeleccionado.id_rol
                        )

                        usuarioRepository
                            .insertUsuario(usuario)

                        Toast.makeText(
                            requireContext(),
                            "Usuario creado",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {// ACTUALIZAR

                        val usuarioActualizado =
                            Usuario(
                                id_usuario = usuarioEditar?.id_usuario,
                                nombre_completo = nombre,
                                correo = correo,
                                password_hash = passwordEncriptado,
                                celular = celular,
                                direccion = direccion,
                                ciudad = ciudad,
                                foto_perfil = fotoUrl,
                                id_rol_FK = rolSeleccionado.id_rol
                            )

                        usuarioRepository.actualizarUsuario(usuarioActualizado)

                        Toast.makeText(
                            requireContext(),
                            "Usuario actualizado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    requireActivity()
                        .onBackPressedDispatcher
                        .onBackPressed()

                } catch (e: Exception) {
                    e.printStackTrace()

                    Toast.makeText(
                        requireContext(),
                        "Error al guardar usuario",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
        return view
    }
}
