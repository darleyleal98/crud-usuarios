package com.darleyleal.crud_usuarios.model

class Usuario(var email: String, var senha: String) {
    override fun toString(): String {
        return "Email: $email"
    }
}