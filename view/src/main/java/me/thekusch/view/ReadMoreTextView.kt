package me.thekusch.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import java.lang.Exception

open class ReadMoreTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = R.attr.readMoreTextView
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var _readMoreText: String? = READ_MORE

    private var _readLessText: String? = READ_LESS

    @ColorInt
    private var _anchorTextColor: Int = getDefColor()

    private var _anchorText: CharSequence? = null

    private var _buffer: CharSequence? = null

    private var _isExpanded: Boolean = false

    private var _textMode = TextMode.LENGTH

    private var _anchorPoint: Int = 2

    private var textClickableSpan: TextClickableSpan? = null


    var readMoreText: String?
        get() = _readMoreText
        set(value) {
            _readMoreText = value
        }

    var readLessText: String?
        get() = _readLessText
        set(value) {
            _readLessText = value
        }

    var anchorTextColor: Int
        @ColorInt get() = _anchorTextColor
        set(@ColorInt value) {
            _anchorTextColor = value
        }

    var textMode: TextMode
        get() = _textMode
        set(value) {
            _textMode = value
        }

    var anchorText: CharSequence?
        get() = _anchorText
        set(value) {
            _anchorText = value
        }

    var isExpanded: Boolean
        get() = _isExpanded
        set(value) {
            _isExpanded = value
            anchorText = if(isExpanded) {
                readLessText
            }else {
                readMoreText
            }
        }

    var anchorPoint: Int
        get() = _anchorPoint
        set(value) {
            _anchorPoint = value
        }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        _buffer = text
    }

    init {
        textClickableSpan = TextClickableSpan()
        obtainStyledAttributes(attrs, defStyleAttr)
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
                )
                readLessText = getString(
                    R.styleable.ReadMoreTextView_readLessText
                )
                anchorTextColor = getColor(
                    R.styleable.ReadMoreTextView_anchorTextColor,
                    _anchorTextColor
                )
                isExpanded = getBoolean(
                    R.styleable.ReadMoreTextView_isExpanded,
                    isExpanded
                )
                anchorPoint = getInt(
                    R.styleable.ReadMoreTextView_anchorPoint,
                    anchorPoint
                )
                textMode = if (typedArray.getInt(
                        R.styleable.ReadMoreTextView_textMode,
                        0
                    ) == 0
                ) TextMode.LENGTH else TextMode.LINE

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


    private fun displayedText(): CharSequence {
        var displayText: CharSequence = ""
        if(isExpanded) {
            displayText = this.text
        }else {
            when(textMode) {
                TextMode.LENGTH -> {
                    val length = this.length()
                    if(length>anchorPoint) {
                        displayText =  this.text.subSequence(
                            0,
                            anchorPoint
                        )
                    }
                }
                TextMode.LINE -> {
                    val lines = this.lineCount
                    if(lines > anchorPoint) {
                        val lineEndIndex = this.layout.getLineEnd(anchorPoint)
                        displayText =  this.text.subSequence(
                            0,
                            lineEndIndex - anchorText!!.length
                        )
                    }
                }
            }
        }
        return displayText
    }

    private fun setText() {
        val finalText = StringBuilder().append(displayedText()).append(anchorText)
        val spannable = SpannableStringBuilder(finalText)
        spannable.setSpan(
            textClickableSpan,
            displayedText().length-1,
            displayedText().length-1+anchorText!!.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

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
        const val READ_LESS = "Read Less.."
        const val READ_MORE = "Read More.."
        enum class TextMode {
            LENGTH, LINE
        }
    }
}