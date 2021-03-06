package smu.miso

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_splash.*
import smu.miso.Chat.ChatActivity

class SplashActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val userRef = database.reference
    private val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private var getRoomId: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar!!.setDisplayShowTitleEnabled(false) // 타이틀 안보이게 하기



        end_button.setOnClickListener {
            Toast.makeText(this, "대기상태 종료", Toast.LENGTH_LONG).show()
            finish()
        }

        if (intent.hasExtra("splash_room_id")) {//user1
            getRoomId = intent.getStringExtra("splash_room_id")
            Toast.makeText(this, getRoomId, Toast.LENGTH_SHORT).show()
            end_button.text = getRoomId
            userRef.child("rooms").child(getRoomId!!).child("users").addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var cnt = 0
                    for (singleSnapshot in snapshot.children) {
                        cnt++
                        if (cnt == 2) {
                            finish()
                            val nextIntent = Intent(this@SplashActivity, ChatActivity::class.java)
                            startActivity(nextIntent)
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })

        } else {//user2
            Toast.makeText(this, "사용자가 입장했습니다. 채팅을 엽니다.", Toast.LENGTH_LONG).show()
            finish()
        }


    }
}