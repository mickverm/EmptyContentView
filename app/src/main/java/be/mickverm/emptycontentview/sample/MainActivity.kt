package be.mickverm.emptycontentview.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import be.mickverm.widget.EmptyContentView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emptyContentView = findViewById<EmptyContentView>(R.id.empty_content_view)
        emptyContentView.setOnActionClickListener {
            Toast.makeText(this, "Action clicked!", Toast.LENGTH_SHORT).show()
        }
    }
}
