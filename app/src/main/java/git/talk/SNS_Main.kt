package git.talk

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.*
import com.google.firebase.database.snapshot.ChildKey
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_sns__main.*
import kotlinx.android.synthetic.main.card_background.*
import kotlinx.android.synthetic.main.card_background.view.*
import kotlinx.android.synthetic.main.card_post.view.*
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Hours
import org.joda.time.Minutes
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class SNS_Main : AppCompatActivity() {

    //글목록을 저장하는 변수
    var posts: MutableList<Post> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sns__main)
        //Actionbar 의 타이틀 "글목록"으로 변경
        supportActionBar?.title = "글목록"

        //하단의 floatingActionbarButton 이 클릭될때의 리스너를 설정한다.
        floatingActionButton.setOnClickListener {
            //Intent 생성
            val intent = Intent(this@SNS_Main, WriteActivity::class.java)
            //Intent 로 WriteActivty 실행
            startActivity(intent)
        }

        //Recyclerview 에 Layoutmanager 설정
        val layoutManager = LinearLayoutManager(this@SNS_Main)

        // 리사이클러뷰의 아이템을 역순으로 정렬하게함
        layoutManager.reverseLayout = true

        //리사이클러뷰의 아이템을쌓는순서를 끝부터 쌓게함
        layoutManager.stackFromEnd = true


    }
}