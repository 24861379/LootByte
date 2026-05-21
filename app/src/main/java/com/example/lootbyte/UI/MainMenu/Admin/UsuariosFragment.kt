package com.example.lootbyte.UI.MainMenu.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lootbyte.Adapter.UsuarioAdapter
import com.example.lootbyte.Model.Usuario
import com.example.lootbyte.R
import com.example.lootbyte.Repository.UsuarioRepository
import kotlinx.coroutines.launch


class UsuariosFragment : Fragment(R.layout.fragment_usuarios) {

    private val listaUsuarios = mutableListOf<Usuario>()
    private lateinit var UsuarioAdapter: UsuarioAdapter
    private val usuarioRepository = UsuarioRepository()

    override fun onResume(){
        super.onResume()
        (activity as? AdminActivity)?.actualizarHeader(R.layout.header_simple_admin)
        cargarUsuarios()
    }
//    private val listaUsuarios = listOf(
//        Usuario(1,"Usuario Demo", "Operador", R.drawable.usuario_demo),
//        Usuario(2,"Demo Usuario", "Operador", R.drawable.demo_usuario)
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private fun cargarUsuarios() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val usuarios = usuarioRepository.obtenerUsuarios()
                listaUsuarios.clear()
                listaUsuarios.addAll(usuarios)
                UsuarioAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun eliminarUsuario(usuario: Usuario) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                usuario.id_usuario?.let {
                    usuarioRepository.deleteUsuario(it)
                }
                cargarUsuarios()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_usuarios, container, false)
        val rvUsuarios = view.findViewById<RecyclerView>(R.id.rvUsuarios)
        rvUsuarios.layoutManager = GridLayoutManager(requireContext(), 1)
        UsuarioAdapter = UsuarioAdapter(listaUsuarios,
            onEliminar = { usuario ->
                AlertDialog.Builder(requireContext())
                .setTitle("Eliminar usuario")
                .setMessage("¿Desea eliminar este usuario?")
                .setPositiveButton("Eliminar") { _, _ ->
                    lifecycleScope.launch {
                        try {
                            usuario.id_usuario?.let{
                                usuarioRepository.deleteUsuario(it)
                                cargarUsuarios()
                                Toast.makeText(requireContext(), "Usuario eliminado", Toast.LENGTH_SHORT).show()
                            }
                        }catch (e: Exception){
                            e.printStackTrace()
                            Toast.makeText(requireContext(), "Error al eliminar usuario", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()
            }, onEditar = { usuario ->
                val fragment = UsuariosAddFragment()
                val bundle = Bundle()
                bundle.putString("id_usuario", usuario.id_usuario)
                fragment.arguments= bundle
                (activity as? AdminActivity)?.cargarFragment(fragment, true)
            }
        )
        rvUsuarios.adapter = UsuarioAdapter
        //me redirecciona a otro fragment
        view.findViewById<View>(R.id.btnAgregar).setOnClickListener {
            (activity as? AdminActivity)?.cargarFragment(UsuariosAddFragment(),  true)
        }



        return view
    }

}