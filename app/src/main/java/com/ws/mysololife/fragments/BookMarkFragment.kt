package com.ws.mysololife.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.ws.mysololife.R
import com.ws.mysololife.contentsList.ContentModel
import com.ws.mysololife.contentsList.bookmarkRVAdapter
import com.ws.mysololife.databinding.FragmentBookMarkBinding
import com.ws.mysololife.utils.FBAuth
import com.ws.mysololife.utils.FBRef


class BookMarkFragment : Fragment() {

    private lateinit var binding : FragmentBookMarkBinding
    private val TAG = BookMarkFragment::class.java.simpleName
    lateinit var rvAdapter : bookmarkRVAdapter
    val bookmarkIDList = mutableListOf<String>()
    val items = ArrayList<ContentModel>()
    val itemKeyList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_book_mark, container, false )
        // Inflate the layout for this fragment

        getBookmarkData()

        rvAdapter = bookmarkRVAdapter(requireContext(), items, itemKeyList, bookmarkIDList)
        val rv : RecyclerView = binding.bookmarkRV
        rv.adapter = rvAdapter
        rv.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.tipTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookMarkFragment_to_tipFragment)
        }
        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookMarkFragment_to_homeFragment)
        }
        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookMarkFragment_to_storeFragment)
        }
        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookMarkFragment_to_talkFragment)
        }
        return binding.root
    }

    //user의 uid값에 저장된 bookmark의 key값과  content의 key값이 같을 시 데이터 받아오기
    private fun getCategoryData(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                for(dataModel in dataSnapshot.children){
                    Log.d(TAG, dataModel.toString())
                    val item = dataModel.getValue(ContentModel::class.java)
                    if (bookmarkIDList.contains(dataModel.key.toString())) {
                        items.add(item!!)
                        itemKeyList.add(dataModel.key.toString())
                    }
                }
                rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentsListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.category1.addValueEventListener(postListener)
        FBRef.category2.addValueEventListener(postListener)
        FBRef.category3.addValueEventListener(postListener)
    }

    //user의 uid에 저장된 북마크의 key값 받아오기
    private fun getBookmarkData(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                for(dataModel in dataSnapshot.children){
                    bookmarkIDList.add(dataModel.key.toString())
                }
                getCategoryData()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentsListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.bookMarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)
    }

}