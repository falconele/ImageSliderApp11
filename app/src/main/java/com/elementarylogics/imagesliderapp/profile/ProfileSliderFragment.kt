package com.elementarylogics.imagesliderapp.profile


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
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.activities.maps.MapsActivity
import com.elementarylogics.imagesliderapp.utils.ErrorCheckingUtils
import com.elementarylogics.imagesliderapp.utils.FileCompressor
import com.elementarylogics.imagesliderapp.utils.PermissionsUtil
import com.elementarylogics.imagesliderapp.utils.PermissionsUtil.Companion.getRealPathFromUri
import com.elementarylogics.imagesliderapp.utils.PermissionsUtil.Companion.mPhotoFile
import com.elementarylogics.imagesliderapp.utils.PermissionsUtil.Companion.requestStoragePermission
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_profile_slider.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ProfileSliderFragment : Fragment() {
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


    lateinit var views: View
    val REQ_CODE_MAP = 102
    lateinit var tietAddress: TextInputLayout
    lateinit var etAddress: TextInputEditText
    lateinit var cardProfilePic: CardView
    lateinit var imgProfile: ImageView

    lateinit var name: TextInputEditText
    lateinit var lastName: TextInputEditText
    lateinit var email: TextInputEditText
    lateinit var flatHouse: TextInputEditText
    lateinit var areaColony: TextInputEditText
    lateinit var city: TextInputEditText
    lateinit var btnSaveOrUpdate: MaterialButton


    var lattitude: Double = 0.0
    var longitude: Double = 0.0
    var profileFile: File? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        views = inflater.inflate(R.layout.fragment_profile_slider, container, false)
        tietAddress = views.findViewById(R.id.tietAddress)
        etAddress = views.findViewById(R.id.etAddress)
        cardProfilePic = views.findViewById(R.id.cardProfilePic)
        imgProfile = views.findViewById(R.id.imgProfile)

        name = views.findViewById(R.id.etName)
        lastName = views.findViewById(R.id.etLastName)
        email = views.findViewById(R.id.etEmail)
        flatHouse = views.findViewById(R.id.etFlatHouse)
        areaColony = views.findViewById(R.id.etAreaColonySector)
        city = views.findViewById(R.id.etCity)
        btnSaveOrUpdate = views.findViewById(R.id.btnSaveOrUpdate)





        Toast.makeText(context, "Profile Fragment Object Created", Toast.LENGTH_LONG).show()
        mCompressor = FileCompressor(activity!!)
        mPhotoFile = PermissionsUtil.createImageFile(activity as AppCompatActivity)
        var relAddress = views.findViewById<RelativeLayout>(R.id.relAddress)
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

        btnSaveOrUpdate.setOnClickListener(View.OnClickListener {
            validateData()
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
                    activity!!,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                    CAM_REQUEST_PERMISSION
                )
            } else {
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
                profileFile = mPhotoFile
                Glide.with(activity!!)
                    .load(profileFile)
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
                profileFile = mPhotoFile
                Glide.with(activity!!)
                    .load(profileFile)
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


    fun validateData() {


        ErrorCheckingUtils.setContextVal(activity as AppCompatActivity)

        if (!ErrorCheckingUtils.profileVerification(profileFile)) return
        if (!ErrorCheckingUtils.checkEmpty(
                etName.text.toString(),
                resources.getString(R.string.empty_name)
            )
        ) return
        if (!ErrorCheckingUtils.checkEmpty(
                etLastName.text.toString(),
                resources.getString(R.string.empty_last_name)
            )
        ) return

        if (!ErrorCheckingUtils.emailVerification(etEmail.text.toString()))
            return

        if (!ErrorCheckingUtils.checkEmpty(
                etAddress.text.toString(),
                resources.getString(R.string.empty_address)
            )
        ) return

        if (!ErrorCheckingUtils.checkEmpty(
                etFlatHouse.text.toString(),
                resources.getString(R.string.empty_flat_house)
            )
        ) return

        if (!ErrorCheckingUtils.checkEmpty(
                etAreaColonySector.text.toString(),
                resources.getString(R.string.empty_area_colony_sector)
            )
        ) return
        if (!ErrorCheckingUtils.checkEmpty(
                etCity.text.toString(),
                resources.getString(R.string.empty_city)
            )
        ) return


    }

}
