package com.ws.mysololife.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ws.mysololife.MainActivity
import com.ws.mysololife.R
import com.ws.mysololife.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        auth = Firebase.auth
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        //회원가입
        binding.joinBtn2.setOnClickListener {
            var isGotoJoin = true
            val email = binding.emailArea.text.toString()
            val password1 = binding.passwordArea1.text.toString()
            val password2 = binding.passwordArea2.text.toString()
            if(email.isEmpty()){
                Toast.makeText(this,"이메일을 입력해주세요", Toast.LENGTH_LONG).show()
                isGotoJoin = false
            }
            if(password1.isEmpty()){
                Toast.makeText(this,"Password를 입력해주세요", Toast.LENGTH_LONG).show()
                isGotoJoin = false
            }
            if(password2.isEmpty()){
                Toast.makeText(this,"Password check를 입력해주세요", Toast.LENGTH_LONG).show()
                isGotoJoin = false
            }
            if(!password1.equals(password2)){
                Toast.makeText(this,"비밀번호를 똑같이 입력해주세요", Toast.LENGTH_LONG).show()
                isGotoJoin = false
            }
            if(password1.length<6){
                Toast.makeText(this,"Password를 6자 이상으로 입력해주세요", Toast.LENGTH_LONG).show()
                isGotoJoin = false
            }
            if(isGotoJoin){
                auth.createUserWithEmailAndPassword(email, password1)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(this,"성공",Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this,"실패",Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

    }
}