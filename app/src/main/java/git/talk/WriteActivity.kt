package git.talk

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.card_background.view.*

class WriteActivity : AppCompatActivity() {



    var currentBgPosition = 0
    //현재 선택된 배경이미지의 포지션을 저장하는 변수


    //배경 리스트 데이터
    val bgList = mutableListOf(
            "android.resource://git.talk/drawable/snsdefault",
            "android.resource://git.talk/drawable/sns_default",
            "android.resource://git.talk/drawable/snsdog1",
            "android.resource://git.talk/drawable/snscat1"


    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        //actionbar 의 타이틀
        supportActionBar?.title = "SNS 게시판 글 올리기"

        //recycleview 에서 사용할 레이아웃 매니저를 생성
        val layoutManager = LinearLayoutManager(this@WriteActivity)
        //recycleview 를 횡으로 스크롤
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL


        //recyclerView에 레이아웃 매니저를 방금생성한 layoutmaneger로 설정
        SNSwrite_recyclerView.layoutManager = layoutManager
        //recyclerView 에 adapter 를 설정
        SNSwrite_recyclerView.adapter = MyAdapter()

        //공유하기 버튼이 클릭된 경우에 이벤트 리스너를 설정한다.
        WriteSendButton.setOnClickListener{
            //메세지가 없는 경우 토스트 메세지로 알림
            if(TextUtils.isEmpty(input.text)){
                Toast.makeText(applicationContext,"메세지를 입력하세요",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //post 객체 생성
            val post = Post()

            //firebase 의 posts 참조에서 객체를 저장하기 위한 새로운 카를 생성하고 참조를 newRef에 저장
            val newRef = FirebaseDatabase.getInstance().getReference("Posts").push()

            //글이 쓰여진 시간은 Firebase 서버의 시간으로 설정
            post.writeTime = ServerValue.TIMESTAMP

            //배경 Uri주소를 현재 선택된 배경의 주소로 할당
            post.bgUri = bgList[currentBgPosition]

            //메세지는 input edittext의 텍스트 내용을 할당
            post.message = input.text.toString()

            //글쓴 사람의 ID는 디바이스의 아이디로 할당
            post.writerId = getMyId()

            //글의 Id는 새로 생성된 파이어베이스 참조의 키로 할당
            post.postId = newRef.key.toString()

            //post 객체를 새로 생성한 참조에 저장
            newRef.setValue(post)

            //저장성공 토스트 알림을 보여주고 Activity 종료
            Toast.makeText(applicationContext,"공유되었습니다",Toast.LENGTH_SHORT).show()
            finish()

        }

    }


    //디바이스의 ID를 변환하는 메소드
    //글쓴 사람의 ID를 인식합니다
    fun getMyId(): String{
        return Settings.Secure.getString(this.contentResolver,Settings.Secure.ANDROID_ID)
    }



    //recyclerView에서 사용하는 view 홀더 클래스
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageview = itemView.imageView
    }

    //recyclerview 의 어댑터 클래스
    inner class MyAdapter : RecyclerView.Adapter<MyViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewGroup: Int): MyViewHolder {
            return MyViewHolder(LayoutInflater.from(this@WriteActivity).inflate(R.layout.card_background,
                    parent, false))
        }


        //RecyclerView에서 몇개의 행을 그릴지 기준이 되는 메소드

        override fun getItemCount(): Int {
            return bgList.size
        }

        //각행의 포지션에서 그려야할 Viewholder UI 에 데이터를 적용하는 메소드

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            //이미지 로딩 라이브러리인 피카소 객체로 뷰홀더에 존재하는 imageview 에 이미지 로딩
            Picasso.get()
                    .load(Uri.parse(bgList[position]))
                    .fit()
                    .centerCrop()
                    .into(holder.imageview)


            //각 배경화면 행이 클릭된 경우에 이벤트 리스너 설정
            holder.itemView.setOnClickListener{
                Picasso.get()
                        .load(Uri.parse(bgList[position]))
                        .fit()
                        .centerCrop()
                        .into(writeBackground)





            }

        }


    }
}
