package com.example.tigran.laykandroid

import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_reset_password.*

class LoginActivity : AppCompatActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, LoginCardFragment())
                .commit()
        }
    }

    override fun onBackPressed() {
        // Maybe if current view is reset password, then back to the main and only from main call finish?
        finish()
    }

    class LoginCardFragment: Fragment() {

        private lateinit var auth: FirebaseAuth

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            // Initialize Firebase Auth
            auth = FirebaseAuth.getInstance()

            return inflater.inflate(R.layout.fragment_login, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            // Call it here since CardView is still not created so if we call it onCreateView it return null reference
            // to button
            val scale = context?.resources?.displayMetrics?.density
            if (scale != null) {
                view.cameraDistance = 8000 * scale
            }

            performAuthentication()
            flipCard()

        }

        // Perform authentication to Firebase
        private fun performAuthentication() {
            loginBtn.setOnClickListener {
                val activity = this.activity as AppCompatActivity
                auth.signInWithEmailAndPassword(emailTextInput.text.toString(), passwordTextInput.text.toString())
                    .addOnCompleteListener(activity) { signInTask ->
                        if (signInTask.isSuccessful) {
                            activity.finish()
                            Log.d(TAG, "signInWithEmail:success")
                        } else {
                            auth.createUserWithEmailAndPassword(emailTextInput.text.toString(), emailTextInput.text.toString())
                                .addOnCompleteListener(activity) { createUserTask ->
                                    if (createUserTask.isSuccessful) {
                                       activity.finish()
                                        Log.d(TAG, "createUserWithEmail: success")
                                    } else {
                                        Log.d(TAG, "createUserWithEmail: failure", createUserTask.exception)
                                        Toast.makeText(
                                            activity.baseContext, "Authentication failed.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        }
                    }
            }
        }

        private fun flipCard() {

            forgotPasswordBtn.setOnClickListener {

                fragmentManager?.beginTransaction()?.setCustomAnimations(
                    R.animator.card_flip_right_in,
                    R.animator.card_flip_right_out,
                    R.animator.card_flip_left_in,
                    R.animator.card_flip_left_out
                )?.replace(R.id.container, ResetPasswordCardFragment())?.addToBackStack(null)?.commit()
            }
        }
    }

    class ResetPasswordCardFragment: Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_reset_password, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            val scale = context?.resources?.displayMetrics?.density
            if (scale != null) {
                view.cameraDistance = 8000 * scale
            }

            flipCard()
        }

        private fun flipCard() {

            backToLoginBtn.setOnClickListener {

                fragmentManager?.beginTransaction()?.setCustomAnimations(
                    R.animator.card_flip_left_in,
                    R.animator.card_flip_left_out,
                    R.animator.card_flip_right_in,
                    R.animator.card_flip_right_out
                )?.replace(R.id.container, LoginCardFragment())?.addToBackStack(null)?.commit()
            }
        }

    }


}