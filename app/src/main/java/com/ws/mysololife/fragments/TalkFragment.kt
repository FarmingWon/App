package com.ws.mysololife.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.ws.mysololife.R
import com.ws.mysololife.board.BoardInsideActivity
import com.ws.mysololife.board.BoardListLVAdapter
import com.ws.mysololife.board.BoardModel
import com.ws.mysololife.board.BoardWriteActivity
import com.ws.mysololife.contentsList.ContentModel
import com.ws.mysololife.databinding.FragmentTalkBinding
import com.ws.mysololife.utils.FBRef


class TalkFragment : Fragment() {

    private lateinit var binding : FragmentTalkBinding
    private val boardDataList = mutableListOf<BoardModel>()
    private val boardKeyList = mutableListOf<String>()
    private lateinit var boardAdapter : BoardListLVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_talk, container, false )
        // Inflate the layout for this fragment
        val boardList =  mutableListOf<BoardModel>()

        //board 미리보기 연결
        boardAdapter = BoardListLVAdapter(boardDataList)
        binding.boardListView.adapter = boardAdapter

        binding.boardListView.setOnItemClickListener{parent, view, position, id ->
            val intent = Intent(context, BoardInsideActivity::class.java)
//            intent.putExtra("title", boardDataList[position].title)
//            intent.putExtra("content", boardDataList[position].content)
//            intent.putExtra("time", boardDataList[position].time)
            intent.putExtra("key", boardKeyList[position])
            startActivity(intent)
        }

        binding.writeBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteActivity::class.java)
            startActivity(intent)
        }

        binding.tipTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_tipFragment)
        }
        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_homeFragment)
        }
        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_storeFragment)
        }
        binding.bookmarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_bookMarkFragment)
        }
        getFBBoardData()
        return binding.root
    }

    //board data 불러오기
    private fun getFBBoardData(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                boardDataList.clear()
                // Get Post object and use the values to update the UI
                for(dataModel in dataSnapshot.children){
                    val item = dataModel.getValue(BoardModel::class.java)
                    boardDataList.add(item!!)
                    boardKeyList.add(dataModel.key.toString())
                }
                //시간순으로 보여주기 위해 reverse
                boardKeyList.reverse()
                boardDataList.reverse()
                boardAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentsListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.addValueEventListener(postListener)
    }
}