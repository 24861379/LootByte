package com.example.lootbyte.UI.MainMenu.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lootbyte.Adapter.UsuarioAdapter
import com.example.lootbyte.Model.Usuario
import com.example.lootbyte.R


class UsuariosFragment : Fragment(R.layout.fragment_usuarios) {

    override fun onResume(){
        super.onResume()
        (activity as? AdminActivity)?.actualizarHeader(R.layout.header_simple_admin)
    }
    private val listaUsuarios = listOf(
        Usuario(1,"Usuario Demo", "Operador", R.drawable.usuario_demo),
        Usuario(2,"Demo Usuario", "Operador", R.drawable.demo_usuario)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_usuarios, container, false)
        val rvUsuarios = view.findViewById<RecyclerView>(R.id.rvUsuarios)
        rvUsuarios.layoutManager = GridLayoutManager(requireContext(), 1)
        rvUsuarios.adapter = UsuarioAdapter(listaUsuarios)
//me redirecciona a otro fragment
        view.findViewById<View>(R.id.btnAgregar).setOnClickListener {
            (activity as? AdminActivity)?.cargarFragment(UsuariosAddFragment(),  true)
        }

        return view
    }

}