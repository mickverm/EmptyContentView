package be.mickverm.widget.emptycontentview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes

class EmptyContentView : LinearLayout, View.OnClickListener {

    companion object {
        const val NO_LABEL = 0
        const val NO_IMAGE = 0
    }

    private lateinit var imageView: ImageView
    private lateinit var descriptionView: TextView
    private lateinit var actionView: TextView

    private var listener: OnActionClickListener? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    override fun onClick(view: View) {
        listener?.onActionClicked(this)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        orientation = VERTICAL
        View.inflate(context, R.layout.empty_content_view, this)
        isClickable = true

        imageView = findViewById(R.id.empty_content_view_image)
        descriptionView = findViewById(R.id.empty_content_view_description)
        actionView = findViewById(R.id.empty_content_view_action)
        actionView.setOnClickListener(this)

        if (attrs == null)
            return

        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.EmptyContentView
        )
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
            setImageResource(a.getDrawable(R.styleable.EmptyContentView_ecv_drawable))
        else
            setImageResource(
                a.getResourceId(
                    R.styleable.EmptyContentView_ecv_drawable,
                    NO_IMAGE
                )
            )
        setIconTint(a.getColor(R.styleable.EmptyContentView_ecv_drawable_tint, Color.LTGRAY))
        setDescription(a.getString(R.styleable.EmptyContentView_ecv_description))
        setActionLabel(a.getString(R.styleable.EmptyContentView_ecv_action))
        setActionTextColor(a.getColorStateList(R.styleable.EmptyContentView_ecv_action_tint))
        a.recycle()
    }

    fun setDescription(text: CharSequence?) {
        if (TextUtils.isEmpty(text)) {
            descriptionView.text = null
            descriptionView.visibility = View.GONE
        } else {
            descriptionView.text = text
            descriptionView.visibility = View.VISIBLE
        }
    }

    fun setDescription(@StringRes resourceId: Int) {
        if (resourceId == NO_LABEL) {
            descriptionView.text = null
            descriptionView.visibility = View.GONE
        } else {
            descriptionView.setText(resourceId)
            descriptionView.visibility = View.VISIBLE
        }
    }

    fun setImageResource(drawable: Drawable?) {
        if (drawable == null) {
            imageView.setImageDrawable(null)
            imageView.visibility = View.GONE
        } else {
            imageView.setImageDrawable(drawable)
            imageView.visibility = View.VISIBLE
        }
    }

    fun setImageResource(@DrawableRes resourceId: Int) {
        if (resourceId == NO_IMAGE) {
            imageView.setImageDrawable(null)
            imageView.visibility = View.GONE
        } else {
            imageView.setImageResource(resourceId)
            imageView.visibility = View.VISIBLE
        }
    }

    fun setIconTint(@ColorInt colorInt: Int) {
        imageView.setColorFilter(colorInt, PorterDuff.Mode.SRC_IN)
    }

    fun setActionLabel(text: CharSequence?) {
        if (TextUtils.isEmpty(text)) {
            actionView.text = null
            actionView.visibility = View.GONE
        } else {
            actionView.text = text
            actionView.visibility = View.VISIBLE
        }
    }

    fun setActionLabel(@StringRes resourceId: Int) {
        if (resourceId == NO_LABEL) {
            actionView.text = null
            actionView.visibility = View.GONE
        } else {
            actionView.setText(resourceId)
            actionView.visibility = View.VISIBLE
        }
    }

    fun setActionTextColor(@ColorInt colorInt: Int) {
        actionView.setTextColor(colorInt)
    }

    fun setActionTextColor(colors: ColorStateList?) {
        colors ?: return
        actionView.setTextColor(colors)
    }

    fun setOnActionClickListener(listener: OnActionClickListener?) {
        this.listener = listener
    }

    fun setOnActionClickListener(listener: (View) -> Unit) {
        this.listener = object : OnActionClickListener {
            override fun onActionClicked(view: View) {
                return listener.invoke(view)
            }
        }
    }

    interface OnActionClickListener {
        fun onActionClicked(view: View)
    }
}
