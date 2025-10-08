package com.example.recyclerviewapp.di

import com.example.recyclerviewapp.network.RetrofitClient
import com.example.recyclerviewapp.repository.UsuarioRepository
import com.example.recyclerviewapp.repository.UsuarioRepositoryInterface
import com.example.recyclerviewapp.viewmodel.DetailsViewModel
import com.example.recyclerviewapp.viewmodel.UsuarioViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { RetrofitClient.instance }

    single<UsuarioRepositoryInterface> { UsuarioRepository(get()) }

    viewModel { UsuarioViewModel(get()) }
    viewModel { DetailsViewModel(get()) }

}