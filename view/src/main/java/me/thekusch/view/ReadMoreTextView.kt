package me.thekusch.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.doOnLayout
import java.lang.Exception

open class ReadMoreTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = R.attr.readMoreTextView
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var _readMoreText: CharSequence = READ_MORE

    private var _readLessText: CharSequence = READ_LESS

    @ColorInt
    private var _anchorTextColor: Int = getDefColor()

    private var _isExpanded: Boolean = false

    private var _textMode = TextMode.LINE.ordinal

    private var _anchorPoint: Int = 20

    private var mText: CharSequence? = null

    private var mBuffer: BufferType? = null

    private var _lineEnd: Int = 0

    private var _lineCount: Int = 0

    private var textClickableSpan: TextClickableSpan = TextClickableSpan()

    var readMoreText: CharSequence
        get() = _readMoreText
        set(value) {
            _readMoreText = value
        }

    var readLessText: CharSequence
        get() = _readLessText
        set(value) {
            _readLessText = value
        }

    var anchorTextColor: Int
        @ColorInt get() = _anchorTextColor
        set(@ColorInt value) {
            _anchorTextColor = value
        }

    var textMode: Int
        get() = _textMode
        set(value) {
            _textMode = value
        }

    val anchorText: CharSequence
        get(){
            if(isExpanded) {
                return readLessText
            }else {
                return readMoreText
            }
        }

    var isExpanded: Boolean
        get() = _isExpanded
        set(value) {
            _isExpanded = value
        }

    var anchorPoint: Int
        get() = _anchorPoint
        set(value) {
            _anchorPoint = value
        }

    init {
        obtainStyledAttributes(attrs, defStyleAttr)
        setText()
    }

    private fun obtainStyledAttributes(attrs: AttributeSet, defStyleAttr: Int) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ReadMoreTextView,
            defStyleAttr,
            0
        )

        try{
            with(typedArray) {
                readMoreText = getString(
                    R.styleable.ReadMoreTextView_readMoreText
                ) ?: readMoreText
                readLessText = getString(
                    R.styleable.ReadMoreTextView_readLessText
                ) ?: readLessText
                anchorTextColor = getColor(
                    R.styleable.ReadMoreTextView_anchorTextColor,
                    _anchorTextColor
                )
                isExpanded = getBoolean(
                    R.styleable.ReadMoreTextView_isExpanded,
                    _isExpanded
                )
                anchorPoint = getInt(
                    R.styleable.ReadMoreTextView_anchorPoint,
                    _anchorPoint
                )
                textMode = if (typedArray.getInt(
                        R.styleable.ReadMoreTextView_textMode,
                        0
                    ) == 0
                ) TextMode.LENGTH.ordinal else TextMode.LINE.ordinal

            }
        }catch (e: Exception) {
            // no-op
        }finally {
            typedArray.recycle()
        }
    }

    private fun getDefColor(): Int {
        return ContextCompat.getColor(context, R.color.defaultColor);
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        movementMethod = LinkMovementMethod.getInstance()
        highlightColor = Color.TRANSPARENT
        mText = text
        mBuffer = type
    }

    private fun setText() {
        super.setText(getFinalText(),mBuffer)
    }

    private fun setFinalText(): CharSequence {
        val text = getTrimmedText()
        if(isExpanded) {
            val spannable = SpannableStringBuilder(text,
                0,
                text.length).append(anchorText)
            return  setClickableSpan(spannable,anchorText)
        }else {
            val spannable = SpannableStringBuilder(text,
                0,
                text.length).append(anchorText)
            return setClickableSpan(spannable,anchorText)
        }


    }

    private fun setClickableSpan(span: SpannableStringBuilder, text: CharSequence): CharSequence{
        span.setSpan(
            textClickableSpan,span.length-text.length,span.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return span
    }

    private fun getTrimmedText(): CharSequence {
        var displayText: CharSequence = ""
        if(isExpanded) {
            displayText = this.mText!!
        }else {
            when(textMode) {
                TextMode.LENGTH.ordinal -> {
                    if(mText!!.length>anchorPoint) {
                        displayText =  mText!!.toString().subSequence(
                            0,
                            anchorPoint
                        )
                    }
                }
                TextMode.LINE.ordinal -> {
                    val lineCount = this.layout.lineCount
                    if(lineCount > anchorPoint) {
                        val lineEndIndex = this.layout.getLineEnd(anchorPoint)
                        displayText =  mText!!.toString().subSequence(
                            0,
                            _lineEnd - anchorText.length
                        )
                    }

                }
            }
        }
        return displayText
    }
    private fun removeGlobalLayoutListener() {
        if(textMode == TextMode.LINE.ordinal) {
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    setLineEndIndex()
                }
            })
        }
    }
    private fun setLineEndIndex() {
        try {
            if(anchorPoint == 0){
                _lineEnd = layout.getLineEnd(0)
            }else if(lineCount > anchorPoint) {
                _lineEnd = layout.getLineEnd(anchorPoint-1)
            }else{
                _lineEnd = INVALID_LINE_END
            }
        }catch (e: Exception) {
            //no-op
        }
    }

    private fun getFinalText(): CharSequence {
        return setFinalText()
    }

     inner class TextClickableSpan: ClickableSpan() {
        override fun onClick(widget: View) {
            isExpanded = !isExpanded
            setText()
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = anchorTextColor
        }
    }

    companion object {
        val READ_LESS: CharSequence = " Read Less.."
        val READ_MORE: CharSequence = " Read More.."
        const val INVALID_LINE_END = -999
        enum class TextMode {
            LENGTH, LINE
        }
    }
}