package com.example.oskar

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.ViewModelProvider
import com.example.oskar.data.remote.dto.Nodes
import com.example.oskar.databinding.ActivityMainBinding
import com.example.oskar.databinding.ButtonLayoutBinding
import com.example.oskar.databinding.HaveAccountBinding
import com.example.oskar.databinding.InputLayoutBinding
import com.example.oskar.databinding.NotHaveAccountBinding
import com.example.oskar.viewModel.MainViewModel


class MainActivity : AppCompatActivity() {

    private val binder by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binder.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        initObservers()
        signup()
    }

    private fun initObservers() {
        viewModel.registerModel.observe(this) { registerModel ->
            if (registerModel != null) {

                viewModel.registerFlowId = registerModel.ui?.action?.substring(
                    registerModel.ui?.action!!.indexOf(
                        "flow="
                    ) + 5
                )

                if (!registerModel.ui?.nodes.isNullOrEmpty()) {
                    val rootView = binder.registerView
                    for (node in registerModel.ui?.nodes!!) {
                        when (node.attributes?.type) {
                            "email" -> {
                                registerEmail(node, rootView)
                            }

                            "password" -> {
                                registerPassword(node, rootView)
                            }

                            "text" -> {
                                registerText(node, rootView)
                            }

                            "submit" -> {
                                registerSubmit(node, rootView)

                            }
                        }
                    }

                    val haveAccountBind by lazy { HaveAccountBinding.inflate(layoutInflater) }
                    haveAccountBind.login.setOnClickListener {
                        viewModel.mapRegisterJson.clear()
                        viewModel.holdRegisterRef.clear()
                        viewModel.registerFlowId = null

                        viewModel.getLogin()
                    }

                    rootView.addView(haveAccountBind.root)
                }
            }

        }

        viewModel.loginModel.observe(this) { loginModel ->
            if (loginModel != null) {
                viewModel.mapRegisterJson.clear()
                viewModel.holdRegisterRef.clear()
                viewModel.registerFlowId = null

                viewModel.loginFlowId = loginModel.ui?.action?.substring(
                    loginModel.ui?.action!!.indexOf(
                        "flow="
                    ) + 5
                )
                binder.loginView.removeAllViews()

                binder.registerView.visibility = View.GONE
                binder.loginView.visibility = View.VISIBLE

                if (!loginModel.ui?.nodes.isNullOrEmpty()) {
                    val loginView = binder.loginView
                    for (node in loginModel.ui?.nodes!!) {
                        when (node.attributes?.type) {
                            "email" -> {
                                loginEmail(node, loginView)
                            }

                            "password" -> {
                                loginPassword(node, loginView)
                            }

                            "text" -> {
                                loginText(node, loginView)
                            }

                            "submit" -> {
                                loginSubmit(node, loginView)
                            }
                        }
                    }


                    val notHaveAccountBind by lazy {
                        NotHaveAccountBinding.inflate(
                            layoutInflater
                        )
                    }
                    notHaveAccountBind.signup.setOnClickListener {
                        signup()
                    }

                    loginView.addView(notHaveAccountBind.root)
                }
            }

        }

        viewModel.registerSuccess.observe(this) { success ->
            if (success != null) {
                if (success) {
                    Toast.makeText(this, "Register Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Register Not Successfully", Toast.LENGTH_SHORT).show()
                }
                viewModel.registerSuccess.value = false
            }
        }
        viewModel.loginSuccess.observe(this) { success ->
            if (success != null) {
                if (success) {
                    Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Login Not Successfully", Toast.LENGTH_SHORT).show()
                }
                viewModel.loginSuccess.value = false
            }
        }


    }

    private fun loginSubmit(node: Nodes, loginView: LinearLayoutCompat) {
        val buttonLayoutBind by lazy {
            ButtonLayoutBinding.inflate(
                layoutInflater
            )
        }

        buttonLayoutBind.et.text = node.meta?.label?.text
        loginView.addView(buttonLayoutBind.root)



        buttonLayoutBind.et.setOnClickListener {
            for (item in viewModel.holdLoginRef) {
                if (!item.value.text.isNullOrEmpty()) {
                    viewModel.mapLoginJson[item.key] =
                        item.value.text.toString()
                }
            }
            viewModel.mapLoginJson["method"] = "password"

            viewModel.doLogin()
        }
    }

    private fun loginText(node: Nodes, loginView: LinearLayoutCompat) {
        val inputLayoutBind by lazy {
            InputLayoutBinding.inflate(
                layoutInflater
            )
        }
        if (node.attributes?.required == true) {
            inputLayoutBind.require.visibility = View.VISIBLE
        } else {
            inputLayoutBind.require.visibility = View.GONE
        }

        inputLayoutBind.title.text = node.meta?.label?.text
        inputLayoutBind.et.inputType = InputType.TYPE_CLASS_TEXT

        viewModel.holdLoginRef[node.attributes?.name!!] = inputLayoutBind.et
        loginView.addView(inputLayoutBind.root)
    }

    private fun loginPassword(node: Nodes, loginView: LinearLayoutCompat) {
        val inputLayoutBind by lazy {
            InputLayoutBinding.inflate(
                layoutInflater
            )
        }
        if (node.attributes?.required == true) {
            inputLayoutBind.require.visibility = View.VISIBLE
        } else {
            inputLayoutBind.require.visibility = View.GONE
        }

        inputLayoutBind.title.text = node.meta?.label?.text
        inputLayoutBind.et.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD

        viewModel.holdLoginRef[node.attributes?.name!!] = inputLayoutBind.et
        loginView.addView(inputLayoutBind.root)
    }

    private fun loginEmail(node: Nodes, loginView: LinearLayoutCompat) {
        val inputLayoutBind by lazy {
            InputLayoutBinding.inflate(
                layoutInflater
            )
        }
        if (node.attributes?.required == true) {
            inputLayoutBind.require.visibility = View.VISIBLE
        } else {
            inputLayoutBind.require.visibility = View.GONE
        }

        inputLayoutBind.title.text = node.meta?.label?.text
        inputLayoutBind.et.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        viewModel.holdLoginRef[node.attributes?.name!!] = inputLayoutBind.et

        loginView.addView(inputLayoutBind.root)
    }

    private fun registerSubmit(node: Nodes, rootView: LinearLayoutCompat) {
        val buttonLayoutBind by lazy {
            ButtonLayoutBinding.inflate(
                layoutInflater
            )
        }

        buttonLayoutBind.et.text = node.meta?.label?.text
        rootView.addView(buttonLayoutBind.root)



        buttonLayoutBind.et.setOnClickListener {
            for (item in viewModel.holdRegisterRef) {
                if (!item.value.text.isNullOrEmpty()) {
                    viewModel.mapRegisterJson[item.key] = item.value.text.toString()
                }
            }
            viewModel.mapRegisterJson["method"] = "password"

            viewModel.doRegister()
        }
    }

    private fun registerText(node: Nodes, rootView: LinearLayoutCompat) {
        val inputLayoutBind by lazy {
            InputLayoutBinding.inflate(
                layoutInflater
            )
        }
        if (node.attributes?.required == true) {
            inputLayoutBind.require.visibility = View.VISIBLE
        } else {
            inputLayoutBind.require.visibility = View.GONE
        }

        inputLayoutBind.title.text = node.meta?.label?.text
        inputLayoutBind.et.inputType = InputType.TYPE_CLASS_TEXT

        viewModel.holdRegisterRef[node.attributes?.name!!] = inputLayoutBind.et
        rootView.addView(inputLayoutBind.root)
    }

    private fun registerPassword(node: Nodes, rootView: LinearLayoutCompat) {
        val inputLayoutBind by lazy {
            InputLayoutBinding.inflate(
                layoutInflater
            )
        }
        if (node.attributes?.required == true) {
            inputLayoutBind.require.visibility = View.VISIBLE
        } else {
            inputLayoutBind.require.visibility = View.GONE
        }

        inputLayoutBind.title.text = node.meta?.label?.text
        inputLayoutBind.et.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD

        viewModel.holdRegisterRef[node.attributes?.name!!] = inputLayoutBind.et
        rootView.addView(inputLayoutBind.root)
    }

    private fun registerEmail(node: Nodes, rootView: LinearLayoutCompat) {
        val inputLayoutBind by lazy {
            InputLayoutBinding.inflate(
                layoutInflater
            )
        }
        if (node.attributes?.required == true) {
            inputLayoutBind.require.visibility = View.VISIBLE
        } else {
            inputLayoutBind.require.visibility = View.GONE
        }

        inputLayoutBind.title.text = node.meta?.label?.text
        inputLayoutBind.et.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        viewModel.holdRegisterRef[node.attributes?.name!!] = inputLayoutBind.et

        rootView.addView(inputLayoutBind.root)
    }

    private fun signup() {
        viewModel.mapLoginJson.clear()
        viewModel.holdLoginRef.clear()
        viewModel.loginFlowId = null

        binder.registerView.removeAllViews()
        binder.registerView.visibility = View.VISIBLE
        binder.loginView.visibility = View.GONE

        viewModel.getRegister()
    }
}