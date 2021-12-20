package com.example.ml_kit_reconocimento_texto
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.TextRecognizerOptionsInterface
import com.google.mlkit.vision.text.TextRecognizerOptionsInterface.LATIN
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.util.zip.DeflaterInputStream
class MainActivity : AppCompatActivity() {
    private val TAG = "ML_Kit_text-recognition"
    private val business_card_id = R.drawable.trafico
    private val traffic_sign_id = R.drawable.trafico
    //View Elements
    private lateinit var btn: Button
    private lateinit var tvContent: TextView
    private lateinit var pb: ProgressBar
    private lateinit var imageView: ImageView
    private lateinit var spinner:Spinner
    //Bitmap
    private var selectedImage: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//Inicializaciones
        btn = findViewById(R.id.btn_start)
        tvContent =findViewById(R.id.tv_content)
        pb = findViewById(R.id.progressBar)
        imageView = findViewById(R.id.imageView2)
        spinner = findViewById(R.id.spinner)
        //Button - ClickListener
        btn.setOnClickListener{
            startTexRecognition()
            pb.visibility= View.VISIBLE
        }
        imageView.setImageResource(traffic_sign_id)
        selectedImage= BitmapFactory.decodeResource(resources, traffic_sign_id)
//Spinner
        val content:Array<String> = arrayOf("Traffic Sign","Business Card")
        val adapter = ArrayAdapter<String> ( this,android.R.layout.simple_expandable_list_item_1,content)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                tvContent.text = ""
                if(p2 == 0)
                {
                    imageView.setImageResource(traffic_sign_id)
                    selectedImage= BitmapFactory.decodeResource(resources,traffic_sign_id)
                }
                else
                {
                    imageView.setImageResource(business_card_id)
                    selectedImage = BitmapFactory.decodeResource(resources,business_card_id)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }//spinner
    }
    private fun startTexRecognition()
    {
        val inputImage:InputImage = InputImage.fromBitmap(selectedImage!!, 0)
        val recognizer: TextRecognizer =TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(inputImage).addOnSuccessListener{
            pb.visibility = View.GONE
            tvContent.text = it.text
            Log.d(TAG,"Succesful Recognition!")
        }
            .addOnFailureListener{
                Log.d(TAG,"NO Succesful Recognition!", it)
            }
    }//startTexRecognition()
}