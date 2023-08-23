package com.aamirashraf.stateflows

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.aamirashraf.stateflows.databinding.ActivityMainBinding
import com.aamirashraf.stateflows.ui.FlowViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel:FlowViewModel by viewModels()   //this after adding the dependency Activity ktx for viewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.etUsername.text.toString().trim(),  //trim is very useful in case of the empty space
                binding.etPassword.text.toString().trim()
            )
        }
            lifecycleScope.launchWhenStarted {
                viewModel.loginUiState.collect{
                    when(it){
                        is FlowViewModel.LoginUiState.Success->{
                            Snackbar.make(binding.root,"successfully login",Snackbar.LENGTH_SHORT).show()
                            binding.progressBar.isVisible=false
                            //we do binding.root in snackbar because we want view as the parameter
                        }
                        is FlowViewModel.LoginUiState.Error->{
                            Snackbar.make(binding.root,it.message,Snackbar.LENGTH_SHORT).show()
                            binding.progressBar.isVisible=false
                        }
                        is FlowViewModel.LoginUiState.Loading->{
                            binding.progressBar.isVisible=true

                        }
                        else -> Unit
                    }

                }

        }
    }


}