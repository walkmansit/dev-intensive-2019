package ru.skillbranch.devintensive.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.Dimension


class CircleImageView @JvmOverloads constructor (
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr:Int = 0
) : ImageView(context,attrs, defStyleAttr){

    companion object{
        private const val DEFAULT_AVATAR = ru.skillbranch.devintensive.R.drawable.avatar_default

        private const val DEFAULT_BORDER_COLOR = Color.WHITE

        private const val DEFAULT_BORDER_WIDTH = ru.skillbranch.devintensive.R.dimen.iv_border_width
    }

    private var borderColor = DEFAULT_BORDER_COLOR

    private var borderWidth = DEFAULT_BORDER_WIDTH

    private var avatarDrawable = DEFAULT_AVATAR

    private var viewWidth: Int = 0
    private var viewHeight: Int = 0
    private lateinit var image: Bitmap
    private lateinit  var paint: Paint
    private lateinit  var paintBorder: Paint
    private lateinit  var shader: BitmapShader

    init {
        if (attrs != null){
            val a = context.obtainStyledAttributes(attrs, ru.skillbranch.devintensive.R.styleable.CircleImageView)
            //avatarDrawable = a.get

            borderColor = a.getColor(ru.skillbranch.devintensive.R.styleable.CircleImageView_cv_borderColor, DEFAULT_BORDER_COLOR)
            borderWidth = a.getLayoutDimension(ru.skillbranch.devintensive.R.styleable.CircleImageView_cv_borderWidth, DEFAULT_BORDER_WIDTH)
            a.recycle()
        }
    }

    private fun setup() {
        // init paint
        paint = Paint()
        paint.isAntiAlias = true

        paintBorder = Paint()
        setBorderColor(Color.WHITE)
        paintBorder.isAntiAlias = true
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, paintBorder)
        paintBorder.setShadowLayer(4.0f, 0.0f, 2.0f, Color.BLACK)
    }

    private fun loadBitmap() {
        val bitmapDrawable = this.drawable as BitmapDrawable

        if (bitmapDrawable != null)
            image = bitmapDrawable.bitmap
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        // load the bitmap
        loadBitmap()

        // init shader
        if (image != null) {
            shader = BitmapShader(
                Bitmap.createScaledBitmap(image, canvas.width, canvas.height, false),
                Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP
            )
            paint.shader = shader
            val circleCenter = (viewWidth / 20).toFloat()

            // circleCenter is the x or y of the view's center
            // radius is the radius in pixels of the cirle to be drawn
            // paint contains the shader that will texture the shape
            canvas.drawCircle(
                circleCenter + borderWidth,
                circleCenter + borderWidth,
                circleCenter + borderWidth - 4.0f,
                paintBorder
            )
            canvas.drawCircle(
                circleCenter + borderWidth,
                circleCenter + borderWidth,
                circleCenter - 4.0f,
                paint
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = measureWidth(widthMeasureSpec)
        val height = measureHeight(heightMeasureSpec, widthMeasureSpec)

        viewWidth = width - borderWidth * 2
        viewHeight = height - borderWidth * 2

        setMeasuredDimension(width, height)
    }

    /*private fun resetDrawable(){

        val roundBitmapDrawable = RoundedBitmapDrawableFactory.create(resources,drawable.toBitmap())
        roundBitmapDrawable.isCircular = true

        borderWidth  = borderWidth
        borderColor = borderColor

        setImageDrawable(roundBitmapDrawable)
        setBackgroundColor(borderColor)
        setPadding(borderWidth,borderWidth,borderWidth,borderWidth)
    }*/


    @Dimension
    public fun  getBorderWidth():Int = borderWidth

    public fun  setBorderWidth(@Dimension dp:Int) {
        borderWidth = dp
        invalidate()
    }

    public fun  getBorderColor():Int = borderColor

    /*public fun  setBorderColor(hex:String){
        borderColor =
    }*/


    public fun  setBorderColor(@ColorRes colorId: Int){
        borderColor = colorId
        invalidate()
    }

    private fun measureWidth(measureSpec: Int): Int {
        var result = 0
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        result = if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            specSize
        } else {
            // Measure the text
            viewWidth
        }

        return result
    }

    private fun measureHeight(measureSpecHeight: Int, measureSpecWidth: Int): Int {
        var result = 0
        val specMode = MeasureSpec.getMode(measureSpecHeight)
        val specSize = MeasureSpec.getSize(measureSpecHeight)

        result = if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            specSize
        } else {
            // Measure the text (beware: ascent is a negative number)
            viewHeight
        }

        return result + 2
    }

}