package com.ws.mysololife.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.ws.mysololife.R
import com.ws.mysololife.comment.commentLVAdapter
import com.ws.mysololife.comment.commentModel
import com.ws.mysololife.databinding.ActivityBoardInsideBinding
import com.ws.mysololife.utils.FBAuth
import com.ws.mysololife.utils.FBRef
import java.lang.Exception

class BoardInsideActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBoardInsideBinding
    private val TAG = BoardInsideActivity::class.java.simpleName
    private val commentDataList = mutableListOf<commentModel>()
    private lateinit var key:String
    private lateinit var commentAdapter : commentLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)
//        val title = intent.getStringExtra("title").toString()
//        val content = intent.getStringExtra("content").toString()
//        val time = intent.getStringExtra("time").toString()
//        binding.titleArea.text = title
//        binding.textArea.text = content
//        binding.timeArea.text = time

        binding.boardSettingIcon.setOnClickListener {
            showDialog()
        }

        key = intent.getStringExtra("key").toString()
        getBoardData(key)
        getImageData(key)

        binding.commentBtn.setOnClickListener {
            insertComment(key)
        }
        commentAdapter = commentLVAdapter(commentDataList)
        binding.commentLV.adapter = commentAdapter
        getCommentData(key)
    }


    //?????? ?????? ????????????
    fun getCommentData(key : String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                commentDataList.clear()
                for(dataModel in dataSnapshot.children){
                    val item = dataModel.getValue(commentModel::class.java)
                    commentDataList.add(item!!)
                }
                commentAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentsListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.commentRef.child(key).addValueEventListener(postListener)

    }

    //?????? ????????????
    fun insertComment(key : String){
        FBRef.commentRef
            .child(key)
            .push()
            .setValue(commentModel(binding.commentArea.text.toString(),FBAuth.getTime()))
        Toast.makeText(this, "?????? ?????? ??????",Toast.LENGTH_SHORT).show()
        binding.commentArea.setText("")
    }


    // ??????/?????? ??????
    private fun showDialog(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView).setTitle("????????? ??????/??????")
        val alertDialog = mBuilder.show()
        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {
            Toast.makeText(this, "??????????????? ???????????????.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, BoardEditActivity::class.java)
            intent.putExtra("key", key)
            startActivity(intent)
        }
        alertDialog.findViewById<Button>(R.id.removeBtn)?.setOnClickListener {
            FBRef.boardRef.child(key).removeValue()
            Toast.makeText(this, "?????? ?????????????????????.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    //????????? ????????? ????????????
    private fun getImageData(key : String){
        val storageReference = Firebase.storage.reference.child(key+".png")

        val imageView = binding.getImageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task->
            if(task.isSuccessful){
                Glide.with(this)
                    .load(task.result)
                    .into(imageView)
            }else{
                binding.getImageArea.isVisible = false
            }
        })
    }

    //??? ?????? ????????????
    private fun getBoardData(key : String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try{
                    val dataModel = dataSnapshot.getValue(BoardModel::class.java)
                    binding.titleArea.text = dataModel!!.title
                    binding.textArea.text = dataModel!!.content
                    binding.timeArea.text = dataModel!!.time
                    val myuid = FBAuth.getUid()
                    val writerUid = dataModel.uid
                    // uid?????? ??? ??????/?????? dialog ????????? ??????
                    if(myuid.equals(writerUid)){
                        binding.boardSettingIcon.isVisible=true
                    }else{

                    }
                }catch(e : Exception){
                    Log.d(TAG, "????????????")
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentsListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }
}