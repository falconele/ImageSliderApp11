package com.elementarylogics.imagesliderapp.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.Constraints
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.activities.maps.MapsActivity
import com.elementarylogics.imagesliderapp.dataclases.User
import com.elementarylogics.imagesliderapp.network.Apis
import com.elementarylogics.imagesliderapp.network.ResponseResult
import com.elementarylogics.imagesliderapp.network.RetrofitClient
import com.elementarylogics.imagesliderapp.utils.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_profile_slider.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity() {


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

    lateinit var progressBar: ProgressBar


    var lattitude: Double = 0.0
    var longitude: Double = 0.0
    var profileFile: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile_slider)


        tietAddress = findViewById(R.id.tietAddress)
        etAddress = findViewById(R.id.etAddress)
        cardProfilePic = findViewById(R.id.cardProfilePic)
        imgProfile = findViewById(R.id.imgProfile)

        name = findViewById(R.id.etName)
        lastName = findViewById(R.id.etLastName)
        email = findViewById(R.id.etEmail)
        flatHouse = findViewById(R.id.etFlatHouse)
        areaColony = findViewById(R.id.etAreaColonySector)
        city = findViewById(R.id.etCity)
        btnSaveOrUpdate = findViewById(R.id.btnSaveOrUpdate)

        progressBar = findViewById(R.id.progressBar)


//        Toast.makeText(context, "Profile Fragment Object Created", Toast.LENGTH_LONG).show()
        mCompressor = FileCompressor(this)
        PermissionsUtil.mPhotoFile = PermissionsUtil.createImageFile(this)
        var relAddress = findViewById<RelativeLayout>(R.id.relAddress)
        etAddress.setOnClickListener(View.OnClickListener {
            if (!ApplicationUtils.isEnableGPS(this)) ApplicationUtils.enableGPS(
                this
            ) else {

                val intent = Intent(this, MapsActivity::class.java)
                intent.putExtra("address", etAddress.text!!.toString())
                intent.putExtra("lat", lattitude)
                intent.putExtra("lon", longitude)
                startActivityForResult(intent, REQ_CODE_MAP)
                clicked = 3
            }
        })



        cardProfilePic.setOnClickListener(View.OnClickListener {
            showBottomSheetDialog()
        })

        btnSaveOrUpdate.setOnClickListener(View.OnClickListener {
            validateData()
        })

        user = SharedPreference.getUserData(applicationContext)
        if (user != null) {
            etCellNo.setText(user.phone_number)
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

        val dialog = BottomSheetDialog(this)
        dialog.setCancelable(false)



        relCamera.setOnClickListener(View.OnClickListener {
            clicked = 1
            dialog.dismiss()

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) !== PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) !== PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
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
            PermissionsUtil.requestStoragePermissionProfile(false, this)
        })
        tvCancel.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        dialog.setContentView(view)
        dialog.show()
    }

    lateinit var mCompressor: FileCompressor
    var clicked = 0
    var address = ""
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (data != null)
        if (resultCode === Activity.RESULT_OK) {
//            Toast.makeText(context, "Image Picked", Toast.LENGTH_SHORT).show()
            if (clicked == 1) {
                try {
                    PermissionsUtil.mPhotoFile = File(imageFilePath)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
//                    imgProfile.setImageURI(Uri.parse(imageFilePath))
                profileFile = PermissionsUtil.mPhotoFile
                Glide.with(ProfileActivity@ this)
                    .load(profileFile)
                    .apply(
                        RequestOptions().centerCrop()
                            .placeholder(R.drawable.ic_home_black_24dp)
                    )
                    .into(imgProfile)
            } else if (clicked == 2) {
                val selectedImage = data!!.getData()
                try {
                    PermissionsUtil.mPhotoFile = mCompressor.compressToFile(
                        File(
                            PermissionsUtil.getRealPathFromUri(
                                selectedImage!!,
                                ProfileActivity@ this
                            )
                        )
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                profileFile = PermissionsUtil.mPhotoFile
                Glide.with(ProfileActivity@ this)
                    .load(profileFile)
                    .apply(
                        RequestOptions().centerCrop()

                            .placeholder(R.drawable.ic_home_black_24dp)
                    )
                    .into(imgProfile)
            } else if (clicked == 3) {
                if (data != null) {
                    address = data.getStringExtra("address")
                    lattitude = data.getDoubleExtra("lat", 0.0)
                    longitude = data.getDoubleExtra("lon", 0.0)
                    if (address != null && address !== "") {
                        etAddress.setText(address)
                    }
                }
            }

        }

    }

    var photoFile: File? = null
    var photoUri: Uri? = null
    private fun openCameraIntent() {
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {


            try {
                photoFile = createImageFile()
            } catch (e: IOException) {
                e.printStackTrace()
                return
            }

            photoUri =
                FileProvider.getUriForFile(
                    applicationContext,
                    getPackageName() + ".provider",
                    photoFile!!
                )
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivityForResult(pictureIntent, REQUEST_IMAGE)
        }
    }

    val REQUEST_IMAGE = 107
    var imageFilePath = ""
    @Throws(IOException::class)
    private fun createImageFile(): File {

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "IMG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageFileName, ".jpg", storageDir)
        imageFilePath = image.absolutePath

        return image
    }


    fun validateData() {


        ErrorCheckingUtils.setContextVal(this)

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

        saveUser()
    }

    lateinit var user: User
//    fun getData() {
//
//
//        Utility.showProgressBar(activity as AppCompatActivity, progressBar, true)
//        val api: Apis = RetrofitClient.getClient()!!.create(Apis::class.java)
//        val token: String =
//            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dnZWRJbkFzIjoiYWRtaW4iLCJpYXQiOjE0MjI3Nzk2Mzh9.gzSraSYS8EXBxLN_oWnFSRgCzcmJmMjLiuyu5CSpyHI"
//
//        val call: Call<ResponseResult<User>> =
//            api.getUser(token, "1")
//
//        call.enqueue(object : Callback<ResponseResult<User>> {
//            override fun onResponse(
//                call: Call<ResponseResult<User>>,
//                response: Response<ResponseResult<User>>
//            ) {
//                Utility.showProgressBar(activity as AppCompatActivity, progressBar, false)
//                try {
//                    if (response.isSuccessful()) {
//                        if (response.body().getStatus()!!) {
//                            if (response.body().getData() != null) {
//                                user = response.body().getData() as User
//                                setUserDetails()
//                            }
//                        } else {
//                            ApplicationUtils.showToast(
//                                activity,
//                                response.body().getMessage().toString() + "",
//                                false
//                            )
//                        }
//                    } else {
//                        val jsonObject = JSONObject(response.errorBody().string())
//                        ApplicationUtils.showToast(
//                            activity,
//                            jsonObject.getString("message") + "",
//                            false
//                        )
//                    }
//                } catch (e: Exception) {
////                    showProgressBar(false)
//                    e.printStackTrace()
//                }
//            }
//
//            override fun onFailure(
//                call: Call<ResponseResult<User>>,
//                t: Throwable
//            ) {
////                showProgressBar(false)
//                Utility.showProgressBar(activity as AppCompatActivity, progressBar, false)
//                Log.d(
//                    Constraints.TAG,
//                    "onFailure: " + t.message
//                )
//            }
//        })
//
//
//    }


    fun setUserDetails() {
        if (user != null) {

            var requestOptions = RequestOptions()
            requestOptions.error(R.drawable.ic_user)
            requestOptions.placeholder(R.drawable.ic_user)
            Glide.with(ProfileActivity@ this).setDefaultRequestOptions(requestOptions)
                .load(user.fullImagePath)
                .into(imgProfile)

            etName.setText(user.first_name)
            etLastName.setText(user.last_name)
            etEmail.setText(user.email)
            etAddress.setText(user.address)
            lattitude = user.latitude
            longitude = user.longitude
            etFlatHouse.setText(user.house_flate_number)
            etAreaColonySector.setText(user.area_colony)
            etCity.setText(user.city)

        }
    }

    fun saveUser() {
//        var token =
//            SharedPreference.getSharedPrefValue(activity as AppCompatActivity, Constants.USER_TOKEN)
//        token = "Bearer $token"


        val first_name: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"),
            etName.getText().toString()
        )
        val last_name = RequestBody.create(
            MediaType.parse("text/plain"),
            etLastName.text.toString()
        )

        val email = RequestBody.create(
            MediaType.parse("text/plain"),
            etEmail.text.toString()
        )

        val phone_number: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"),
            user.phone_number
        )
        val address = RequestBody.create(
            MediaType.parse("text/plain"),
            etAddress.text.toString()
        )

        val customer_id = RequestBody.create(
            MediaType.parse("text/plain"),
            user.id.toString()
        )
        val latitude: RequestBody =
            RequestBody.create(MediaType.parse("text/plain"), lattitude.toString() + "")
        val longitude: RequestBody =
            RequestBody.create(MediaType.parse("text/plain"), longitude.toString() + "")
        val flatHouse = RequestBody.create(
            MediaType.parse("text/plain"),
            etFlatHouse.text.toString()
        )
        val areaColony = RequestBody.create(
            MediaType.parse("text/plain"),
            etAreaColonySector.text.toString()
        )

        val city = RequestBody.create(
            MediaType.parse("text/plain"),
            etCity.text.toString()
        )

        var imageBodyPart: MultipartBody.Part? = null
        if (profileFile != null) {
            val image: RequestBody = RequestBody.create(
                MediaType.parse("image/jpeg"),
                profileFile
            )
            imageBodyPart =
                MultipartBody.Part.createFormData("photo", profileFile!!.getName(), image)
        }





        Utility.showProgressBar(this, progressBar, true)
        val api: Apis = RetrofitClient.getClient()!!.create(Apis::class.java)
        val token: String =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dnZWRJbkFzIjoiYWRtaW4iLCJpYXQiOjE0MjI3Nzk2Mzh9.gzSraSYS8EXBxLN_oWnFSRgCzcmJmMjLiuyu5CSpyHI"

        val call: Call<ResponseResult<User>> =
            api.saveOrUpdate(
                token,
                customer_id,
                first_name,
                last_name,
                email,
                latitude,
                longitude,
                address,
                phone_number,
                imageBodyPart!!,
                city,
                flatHouse,
                areaColony
            )

        call.enqueue(object : Callback<ResponseResult<User>> {
            override fun onResponse(
                call: Call<ResponseResult<User>>,
                response: Response<ResponseResult<User>>
            ) {
                Utility.showProgressBar(this@ProfileActivity, progressBar, false)
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()!!) {
                            if (response.body().getData() != null) {
                                user = response.body().getData() as User
//                                setUserDetails()
                                ApplicationUtils.showToast(
                                    this@ProfileActivity,
                                    response.body().getMessage(),
                                    true
                                )

                                SharedPreference.saveUserProfile(
                                    this@ProfileActivity,
                                    user
                                )
                                startActivity(
                                    Intent(
                                        this@ProfileActivity,
                                        AddressDateTimeActivity::class.java
                                    )
                                )
                                finish()

                            }
                        } else {
                            ApplicationUtils.showToast(
                                this@ProfileActivity,
                                response.body().getMessage().toString() + "",
                                false
                            )
                        }
                    } else {
                        val jsonObject = JSONObject(response.errorBody().string())
                        ApplicationUtils.showToast(
                            this@ProfileActivity,
                            jsonObject.getString("message") + "",
                            false
                        )
                    }
                } catch (e: Exception) {
//                    showProgressBar(false)
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                call: Call<ResponseResult<User>>,
                t: Throwable
            ) {
//                showProgressBar(false)
                Utility.showProgressBar(this@ProfileActivity, progressBar, false)
                Log.d(
                    Constraints.TAG,
                    "onFailure: " + t.message
                )
            }
        })


    }
}
