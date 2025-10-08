package com.example.recyclerviewapp.di

import com.example.recyclerviewapp.network.RetrofitClient
import com.example.recyclerviewapp.repository.UsuarioRepository
import com.example.recyclerviewapp.viewmodel.UsuarioViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { RetrofitClient.instance }

    single { UsuarioRepository(get()) }

    viewModel { UsuarioViewModel(get()) }

}