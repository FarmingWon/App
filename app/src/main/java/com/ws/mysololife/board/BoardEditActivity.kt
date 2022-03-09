package com.ws.mysololife.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.ws.mysololife.R
import com.ws.mysololife.databinding.ActivityBoardEditBinding
import com.ws.mysololife.utils.FBAuth
import com.ws.mysololife.utils.FBRef
import java.lang.Exception

class BoardEditActivity : AppCompatActivity() {
    private lateinit var key: String
    private lateinit var binding: ActivityBoardEditBinding
    private lateinit var writerUid : String
    private val TAG = BoardEditActivity::class.java.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_edit)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_edit)
        key = intent.getStringExtra("key").toString()
        getBoardData(key)
        getImageData(key)

        //게시판 수정
        binding.editBtn.setOnClickListener {
            editBoardData(key)
        }
    }

    //이미지 받아오기
    private fun getImageData(key : String){
        val storageReference = Firebase.storage.reference.child(key+".png")

        val imageView = binding.imageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task->
            if(task.isSuccessful){
                Glide.with(this)
                    .load(task.result)
                    .into(imageView)
            }else{

            }
        })
    }

    //수정값 전달
    private fun editBoardData(key: String){
        FBRef.boardRef
            .child(key)
            .setValue(BoardModel(binding.titleArea.text.toString(), binding.contentArea.text.toString(), writerUid, FBAuth.getTime()))
        Toast.makeText(this, "수정완료", Toast.LENGTH_SHORT).show()
        finish()
    }

    // key값에 저장해두었던 내용 받아오기
    private fun getBoardData(key: String) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    val dataModel = dataSnapshot.getValue(BoardModel::class.java)
                    binding.titleArea.setText(dataModel?.title)
                    binding.contentArea.setText(dataModel?.content)
                    writerUid = dataModel!!.uid.toString()
                } catch (e: Exception) {
                    Log.d(TAG, "삭제완료")
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }
}