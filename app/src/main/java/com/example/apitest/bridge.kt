package com.example.apitest

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [bridge.newInstance] factory method to
 * create an instance of this fragment.
 */
class bridge : Fragment() {

    lateinit var bridgeImg:ImageView
    lateinit var nameText:TextView
    lateinit var descriptionText:TextView



    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(com.example.apitest.R.layout.fragment_bridge, container, false)
        bridgeImg = view.findViewById(com.example.apitest.R.id.bridgeImg)
        nameText = view.findViewById(com.example.apitest.R.id.name)
        descriptionText = view.findViewById(com.example.apitest.R.id.description)

        val requestQueue = Volley.newRequestQueue(requireContext())

        val jsonUrl = "https://priyangsubanerjee.github.io/yogism/yogism-api.json"
        val jsonReq = StringRequest(
            Request.Method.GET,
            jsonUrl,
            { response ->
                try {
                    val dataArray = JSONObject(response).getJSONArray("featured")
                    val yogaSession = dataArray.getJSONObject(0)

                    val scheduledArray = yogaSession.getJSONArray("scheduled")
                    val firstScheduled = scheduledArray.getJSONObject(1)
                    val englishName = firstScheduled.getString("english_name")
                    val sanskrit = firstScheduled.getString("sanskrit_name")
                    val description = firstScheduled.getString("description")
                    val imageUrl = yogaSession.getString("image")
                    nameText.text = (sanskrit + ": " + englishName)
                    descriptionText.text = description

                    val imgReq = ImageRequest(
                        imageUrl,
                        { bitmap ->
                            bridgeImg.setImageBitmap(bitmap)
                        },
                        1000, 1000,
                        ImageView.ScaleType.FIT_END,
                        Bitmap.Config.RGB_565,
                        { error ->
                            Log.e("niko", "Image request failed", error)
                        }
                    )

                    requestQueue.add(imgReq)

                    Log.i("niko", "data successful" + sanskrit + englishName + description)
                } catch (e: Exception) {
                    Log.e("nioko", "error", e)
                }
            },
            { error ->
                Log.e("niko", "Request failed", error)
            }
        )

        requestQueue.add(jsonReq)

        return view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment bridge.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            bridge().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}