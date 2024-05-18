package com.darleyleal.crud_usuarios.viewmodel

import androidx.lifecycle.ViewModel
import com.darleyleal.crud_usuarios.model.Usuario

class MainViewModel: ViewModel() {

    private var listaDeUsuarios = mutableListOf<Usuario>()
    var listagemDeEmails = listaDeUsuarios

    fun adicionarNovoUsuario(usuario: Usuario) {
        listaDeUsuarios.add(usuario)
    }

    fun editarDadosDoUsuario(novoEmail: String, novaSenha: String, position: Int) {
        listaDeUsuarios[position].email = novoEmail
        listaDeUsuarios[position].senha = novaSenha
    }

    fun removerUsuario(position: Int) {
        listaDeUsuarios.removeAt(position)
    }
}