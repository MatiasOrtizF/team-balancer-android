package com.mfo.teambalancerapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mfo.teambalancerapp.databinding.ActivityLoginBinding
import com.mfo.teambalancerapp.domain.model.LoginRequest
import com.mfo.teambalancerapp.ui.home.HomeActivity
import com.mfo.teambalancerapp.ui.signup.SignUpActivity
import com.mfo.teambalancerapp.utils.PreferencesHelper
import com.mfo.teambalancerapp.utils.PreferencesHelper.set
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initUISTate()
        initListeners()
    }

    private fun initUISTate() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.state.collect {
                    when(it) {
                        LoginState.Loading -> {}
                        is LoginState.Error -> errorState(it.error)
                        is LoginState.Success -> successState(it)
                    }
                }
            }
        }
    }

    private fun initListeners() {
        binding.apply {
            btnLogin.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                if(email.isNotEmpty() && password.isNotEmpty()) {
                    val loginRequest = LoginRequest(email, password)

                    loginViewModel.authenticationUser(loginRequest)
                    loadingState()
                }
            }
            btnSignup.setOnClickListener {
                goToSignup()
            }
        }
    }

    private fun loadingState() {
        binding.pbLogin.isVisible = true
        binding.llFooter.isVisible = false
        binding.llTitle.isVisible = false
        binding.llEditText.isVisible = false
    }

    private fun errorState(error: String) {
        binding.pbLogin.isVisible = false
        binding.llFooter.isVisible = true
        binding.llTitle.isVisible = true
        binding.llEditText.isVisible = true
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    private fun successState(state: LoginState.Success) {
        /*binding.pbLogin.isVisible = false
        binding.llFooter.isVisible = true
        binding.llTitle.isVisible = true
        binding.llEditText.isVisible = true*/
        // creo que tengo que borrar lo de arriba
        createSessionPreferences(state.token)
        goToHome()
    }

    private fun createSessionPreferences(jwt: String) {
        val preferences = PreferencesHelper.defaultPrefs(this)
        preferences["jwt"] = jwt
    }

    private fun goToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToSignup() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }
}