package ru.skillbranch.devintensive


import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.PersistableBundle

import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.models.Bender

private const val BUNDLE_STATUS_KEY = "STATUS"
private const val BUNDLE_QUESTION_KEY = "QUESTION"
private const val BUNDLE_INPUT_KEY = "INPUT"

class MainActivity : AppCompatActivity(), View.OnClickListener, TextView.OnEditorActionListener{

    private lateinit var benderImage : ImageView
    private lateinit var textTv : TextView
    private lateinit var messageEd : EditText
    private lateinit var sendBtn : ImageView

    private lateinit var bender : Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MainActivity","onCreate")

        benderImage = iv_bender
        textTv = tv_text
        messageEd = et_message
        sendBtn = iv_send
        sendBtn.setOnClickListener(this)
        messageEd.setOnEditorActionListener() { v, actionId, event ->
            when(actionId){
            EditorInfo.IME_ACTION_DONE -> { handleSubmit(); true }
            else -> false
        }
        }

        val status = savedInstanceState?.getString(BUNDLE_STATUS_KEY) ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString(BUNDLE_QUESTION_KEY) ?: Bender.Question.NAME.name
        val input = savedInstanceState?.getString(BUNDLE_INPUT_KEY)
        if (input != null) messageEd.setText(input)

        bender = Bender(Bender.Status.valueOf(status),Bender.Question.valueOf(question))

        textTv.text = bender.askQuestion()
        val (r,g,b) = bender.status.color
        benderImage.setColorFilter(Color.rgb(r,g,b),PorterDuff.Mode.MULTIPLY)
    }

    override fun onStart() {
        super.onStart()
        Log.d("M_MainActivity","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("M_MainActivity","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("M_MainActivity","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("M_MainActivity","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("M_MainActivity","onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("M_MainActivity","onRestart")
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.iv_send){
            handleSubmit()
        }
    }

    private fun handleSubmit(){
        val (phase, color) =  bender.listerAnswer(messageEd.text.toString())
        messageEd.setText("")
        val (r,g,b) = color

        benderImage.setColorFilter(Color.rgb(r,g,b),PorterDuff.Mode.MULTIPLY)
        textTv.text = phase
        hideKeyboard()
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE){
            handleSubmit()
            return true
        }
        return false
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState?.putString(BUNDLE_STATUS_KEY,bender.status.name)
        outState?.putString(BUNDLE_QUESTION_KEY,bender.question.name)
        outState?.putString(BUNDLE_INPUT_KEY,messageEd.text.toString())
        Log.d("M_MainActivity","onSaveInstanceState")
    }
}
