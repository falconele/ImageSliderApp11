package com.elementarylogics.imagesliderapp


import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.elementarylogics.imagesliderapp.activities.maps.MapsActivity
import com.elementarylogics.imagesliderapp.utils.FileCompressor
import com.elementarylogics.imagesliderapp.utils.PermissionsUtil
import com.elementarylogics.imagesliderapp.utils.PermissionsUtil.Companion.getRealPathFromUri
import com.elementarylogics.imagesliderapp.utils.PermissionsUtil.Companion.mPhotoFile
import com.elementarylogics.imagesliderapp.utils.PermissionsUtil.Companion.requestStoragePermission
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProfileSliderFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProfileSliderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileSliderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
//    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    lateinit var views: View
    val REQ_CODE_MAP = 102
    lateinit var tiAddress: TextInputLayout
    lateinit var etAddress: TextInputEditText
    lateinit var cardProfilePic: CardView
    lateinit var imgProfile: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        views = inflater.inflate(R.layout.fragment_profile_slider, container, false)
        tiAddress = views.findViewById(R.id.tietAddress)
        etAddress = views.findViewById(R.id.etAddress)
        cardProfilePic = views.findViewById(R.id.cardProfilePic)
        imgProfile = views.findViewById(R.id.imgProfile)

        Toast.makeText(context, "Profile Fragment Object Created", Toast.LENGTH_LONG).show()
        mCompressor = FileCompressor(activity!!)
        mPhotoFile = PermissionsUtil.createImageFile(activity as AppCompatActivity)

        etAddress.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            intent.putExtra("address", etAddress.text!!.toString())
            intent.putExtra("lat", 1234.5)
            intent.putExtra("lon", 1234.5)
            startActivityForResult(intent, REQ_CODE_MAP)
        })

        cardProfilePic.setOnClickListener(View.OnClickListener {
            showBottomSheetDialog()
        })

        return views
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }


    public fun updateMethod() {
        Toast.makeText(context, "Profile Fragment Updated", Toast.LENGTH_LONG).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(context, "View created", Toast.LENGTH_LONG).show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileSliderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileSliderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    lateinit var tvCancel: TextView
    lateinit var relCamera: RelativeLayout
    lateinit var relGallery: RelativeLayout

    val CAM_REQUEST_PERMISSION = 105
    fun showBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_pic_selection, null)
        tvCancel = view.findViewById(R.id.tvCancel)
        relCamera = view.findViewById(R.id.relCamera)
        relGallery = view.findViewById(R.id.relGallery)

        val dialog = BottomSheetDialog(activity!!)
        dialog.setCancelable(false)



        relCamera.setOnClickListener(View.OnClickListener {
            clicked = 1
            dialog.dismiss()

            if (ContextCompat.checkSelfPermission(
                    activity!!,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) !== PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    activity!!,
                    Manifest.permission.CAMERA
                ) !== PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA),
                    CAM_REQUEST_PERMISSION
                )
            }else{
                openCameraIntent()
            }


//            openCameraIntent()
//            requestStoragePermission(true, activity as AppCompatActivity, this)
        })
        relGallery.setOnClickListener(View.OnClickListener {
            clicked = 2
            dialog.dismiss()
            requestStoragePermission(false, activity as AppCompatActivity, this)
        })
        tvCancel.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        dialog.setContentView(view)
        dialog.show()
    }

    lateinit var mCompressor: FileCompressor
    var clicked = 0
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (data != null)
        if (resultCode === RESULT_OK) {
            Toast.makeText(context, "Image Picked", Toast.LENGTH_SHORT).show()
            if (clicked == 1) {
                try {
                    mPhotoFile = File(imageFilePath)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
//                    imgProfile.setImageURI(Uri.parse(imageFilePath))
                Glide.with(activity!!)
                    .load(mPhotoFile)
                    .apply(
                        RequestOptions().centerCrop()
                            .placeholder(R.drawable.ic_home_black_24dp)
                    )
                    .into(imgProfile)
            } else if (clicked == 2) {
                val selectedImage = data!!.getData()
                try {
                    mPhotoFile = mCompressor.compressToFile(
                        File(
                            getRealPathFromUri(
                                selectedImage!!,
                                activity as AppCompatActivity
                            )
                        )
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                Glide.with(activity!!)
                    .load(mPhotoFile)
                    .apply(
                        RequestOptions().centerCrop()

                            .placeholder(R.drawable.ic_home_black_24dp)
                    )
                    .into(imgProfile)
            }
        }

    }

    var photoFile: File? = null
    var photoUri: Uri? = null
    private fun openCameraIntent() {
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (pictureIntent.resolveActivity(activity!!.getPackageManager()) != null) {


            try {
                photoFile = createImageFile()
            } catch (e: IOException) {
                e.printStackTrace()
                return
            }

            photoUri =
                FileProvider.getUriForFile(
                    context!!,
                    activity!!.getPackageName() + ".provider",
                    photoFile!!
                )
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            activity!!.startActivityForResult(pictureIntent, REQUEST_IMAGE)
        }
    }

    val REQUEST_IMAGE = 107
    var imageFilePath = ""
    @Throws(IOException::class)
    private fun createImageFile(): File {

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "IMG_" + timeStamp + "_"
        val storageDir = activity!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageFileName, ".jpg", storageDir)
        imageFilePath = image.absolutePath

        return image
    }

}
