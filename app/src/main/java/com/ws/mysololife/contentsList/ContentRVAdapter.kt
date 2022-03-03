package com.ws.mysololife.contentsList

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ws.mysololife.R
import com.ws.mysololife.utils.FBAuth
import com.ws.mysololife.utils.FBRef

class ContentRVAdapter(val context : Context,
                       val item : ArrayList<ContentModel>,
                       val itemKeyList : ArrayList<String>,
                       val bookmarkIDList : MutableList<String>) : RecyclerView.Adapter<ContentRVAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentRVAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.contents_rv_item,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ContentRVAdapter.ViewHolder, position: Int) {
        holder.bindItems(item[position], itemKeyList[position])
    }

    override fun getItemCount(): Int {
        return item.size
    }
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(item : ContentModel, key : String){

            //web 실행
            itemView.setOnClickListener {
                Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                val intent = Intent(context, ContentShowActivity::class.java)
                intent.putExtra("url", item.webUrl)
                itemView.context.startActivity(intent)
            }

            val contentTitle = itemView.findViewById<TextView>(R.id.textArea)
            contentTitle.text = item.title
            val imageArea = itemView.findViewById<ImageView>(R.id.imageArea)
            val bookMarkArea = itemView.findViewById<ImageView>(R.id.bookMarkArea)

            //북마크 생성
            if(bookmarkIDList.contains(key)){
                bookMarkArea.setImageResource(R.drawable.bookmark_color)
            }else{
                bookMarkArea.setImageResource(R.drawable.bookmark_white)
            }

            bookMarkArea.setOnClickListener {
                val uid = FBAuth.getUid()
                //북마크 있을때
                if(bookmarkIDList.contains(key)) {
                    FBRef.bookMarkRef
                        .child(uid)
                        .child(key)
                        .removeValue()
                }else{ // 없을때
                    FBRef.bookMarkRef
                        .child(uid)
                        .child(key)
                        .setValue(bookmarkModel(true))
                }
            }

            //web address 받아와서 image 출력
            Glide.with(context)
                .load(item.imageUrl)
                .apply(RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(imageArea)
        }
    }
}