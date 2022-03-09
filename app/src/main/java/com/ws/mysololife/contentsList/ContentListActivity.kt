package com.ws.mysololife.contentsList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ws.mysololife.R
import com.ws.mysololife.utils.FBAuth
import com.ws.mysololife.utils.FBRef

class ContentListActivity : AppCompatActivity() {
    val bookmarkIDList = mutableListOf<String>()
    lateinit var rvAdapter : ContentRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_list)

        val database = Firebase.database
        val items = ArrayList<ContentModel>()
        val itemKeyList = ArrayList<String>()
        rvAdapter = ContentRVAdapter(baseContext, items, itemKeyList, bookmarkIDList)
        var myRef : DatabaseReference


        // category에 대한 정보 받아오기
        val category = intent.getStringExtra("category")
        if(category == "category1"){
            myRef = database.getReference("contents")
        }else if(category == "category2"){
            myRef = database.getReference("contents2")
        }else if(category == "category3"){
            myRef = database.getReference("contents3")
        }else if(category == "category4"){
            myRef = database.getReference("contents4")
        }else if(category == "category5"){
            myRef = database.getReference("contents5")
        }else if(category == "category6"){
            myRef = database.getReference("contents6")
        }else if(category == "category7"){
            myRef = database.getReference("contents7")
        }else{
            myRef = database.getReference("contents8")
        }

        //firebase에 저장되어있는 content에 대한 정보 ContentModel 형태로 데이터 받아오기
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                for(dataModel in dataSnapshot.children){
                    val item = dataModel.getValue(ContentModel::class.java)
                    Log.d("ContentListActivity", dataModel.key.toString())
                    items.add(item!!)
                    itemKeyList.add(dataModel.key.toString())
                }
                rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentsListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        myRef.addValueEventListener(postListener)

        val rv : RecyclerView = findViewById(R.id.rv)
        rv.adapter = rvAdapter
        rv.layoutManager = GridLayoutManager(this, 2)
        getBookmarkData()
    }

    //북마크 되어있는 것에 대한 정보 불러오기
    private fun getBookmarkData(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                bookmarkIDList.clear()
                for(dataModel in dataSnapshot.children){
                    bookmarkIDList.add(dataModel.key.toString())
                }
                Log.d("ContentsListActivity", bookmarkIDList.toString())
                rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentsListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.bookMarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)
    }
}