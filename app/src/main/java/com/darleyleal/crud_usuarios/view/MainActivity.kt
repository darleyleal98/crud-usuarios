package com.darleyleal.crud_usuarios.view

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.darleyleal.crud_usuarios.R
import com.darleyleal.crud_usuarios.databinding.ActivityMainBinding
import com.darleyleal.crud_usuarios.model.Usuario
import com.darleyleal.crud_usuarios.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var positionItem = -1
    private var viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, viewModel.listagemDeEmails
        )

        binding.listUsuarios.setOnItemClickListener { _, _, position, _ ->
            binding.inputEmail.setText(viewModel.listagemDeEmails[position].email)
            binding.editPassword.setText(viewModel.listagemDeEmails[position].senha)
            positionItem = position
        }

        binding.buttonInserir.setOnClickListener {
            val email = binding.inputEmail.text.toString().trim()
            val senha = binding.editPassword.text.toString().trim()

            when {
                email.isEmpty() || senha.isEmpty() -> {
                    Toast.makeText(
                        this,
                        "Por favor, preencha os campos antes de continuar!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                email.length <= 2 -> {
                    Toast.makeText(
                        this,
                        "O e-mail deve ter mais de 2 caracteres!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                !email.contains('@', true) -> {
                    Toast.makeText(
                        this, "E-mail inválido, verifique e tente novamente!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                senha.length <= 6 -> {
                    Toast.makeText(
                        this, "A senha deve ter mais de 6 caracteres!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    val usuario = Usuario(email, senha)
                    viewModel.adicionarNovoUsuario(usuario)
                    Toast.makeText(
                        this, "Usuário adicionado com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.listUsuarios.adapter = adapter
                    adapter.notifyDataSetChanged()
                    binding.inputEmail.text?.clear()
                    binding.editPassword.text?.clear()
                }
            }
        }

        binding.buttonRemover.setOnClickListener {
            if (positionItem >= 0) {
                viewModel.removerUsuario(positionItem)
                adapter.notifyDataSetChanged()

                binding.inputEmail.text?.clear()
                binding.editPassword.text?.clear()

                Toast.makeText(
                    this, "Usuário removido com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            positionItem = -1
        }

        binding.buttonEditar.setOnClickListener {
            if (positionItem >= 0) {
                viewModel.editarDadosDoUsuario(
                    binding.inputEmail.text.toString(),
                    binding.editPassword.text.toString(), positionItem
                )

                Toast.makeText(
                    this, "Atualização feita com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()

                adapter.notifyDataSetChanged()
                binding.inputEmail.text?.clear()
                binding.editPassword.text?.clear()
            }
        }
    }
}