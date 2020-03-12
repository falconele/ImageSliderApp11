package com.elementarylogics.imagesliderapp.utils

import android.Manifest
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.elementarylogics.imagesliderapp.fragments.profile.ProfileSliderFragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class PermissionsUtil {
    companion object {
        /**
         * Requesting multiple permissions (storage and camera) at once
         * This uses multiple permission model from dexter
         * On permanent denial opens settings dialog
         */






        fun requestStoragePermission(
            isCamera: Boolean,
            activity: AppCompatActivity,
            profileSliderFragment: ProfileSliderFragment
        ) {
            Dexter.withActivity(activity)
                .withPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if (isCamera) {
//                                dispatchTakePictureIntent(activity, profileSliderFragment)
                                takePicture(activity)
                            } else {
                                dispatchGalleryIntent(activity)
                            }
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog(activity)
                        }
                    }

//                     fun onPermissionRationaleShouldBeShown(
//                        permissions: List<PermissionRequest>,
//                        token: PermissionToken
//                    ) {
//                        token.continuePermissionRequest()
//                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        token!!.continuePermissionRequest()
                    }
                })
                .withErrorListener { error ->
                    Toast.makeText(activity, "Error occurred! ", Toast.LENGTH_SHORT)
                        .show()
                }
                .onSameThread()
                .check()
        }

        fun requestStoragePermissionProfile(
            isCamera: Boolean,
            activity: AppCompatActivity
        ) {
            Dexter.withActivity(activity)
                .withPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if (isCamera) {
//                                dispatchTakePictureIntent(activity, profileSliderFragment)
                                takePicture(activity)
                            } else {
                                dispatchGalleryIntent(activity)
                            }
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog(activity)
                        }
                    }

//                     fun onPermissionRationaleShouldBeShown(
//                        permissions: List<PermissionRequest>,
//                        token: PermissionToken
//                    ) {
//                        token.continuePermissionRequest()
//                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        token!!.continuePermissionRequest()
                    }
                })
                .withErrorListener { error ->
                    Toast.makeText(activity, "Error occurred! ", Toast.LENGTH_SHORT)
                        .show()
                }
                .onSameThread()
                .check()
        }


        /**
         * Showing Alert Dialog with Settings option
         * Navigates user to app settings
         * NOTE: Keep proper title and message depending on your app
         */
        fun showSettingsDialog(activity: AppCompatActivity) {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Need Permissions")
            builder.setMessage(
                "This app needs permission to use this feature. You can grant them in app settings."
            )
            builder.setPositiveButton("GOTO SETTINGS", { dialog, which ->
                dialog.cancel()
                openSettings(activity)
            })
            builder.setNegativeButton("Cancel", { dialog, which -> dialog.cancel() })
            builder.show()
        }

        // navigating user to app settings
        fun openSettings(activity: AppCompatActivity) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", activity.getPackageName(), null)
            intent.setData(uri)
            activity.startActivityForResult(intent, 101)
        }

        /**
         * Create file with current timestamp name
         * @throws IOException
         */
        @Throws(IOException::class)
        fun createImageFile(activity: AppCompatActivity): File {
            // Create an image file name
            val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
            val mFileName = "JPEG_" + timeStamp + "_"
            val storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile(mFileName, ".jpg", storageDir)
        }

        /**
         * Capture image from camera
         */
        val REQUEST_TAKE_PHOTO = 106
         var mPhotoFile: File?=null


        fun dispatchTakePictureIntent(
            activity: AppCompatActivity,
            profileSliderFragment: ProfileSliderFragment
        ) {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
                // Create the File where the photo should go
                var photoFile: File? = null
                try {
                    photoFile = createImageFile(activity)
                } catch (ex: IOException) {
                    ex.printStackTrace()
                    // Error occurred while creating the File
                }

                if (photoFile != null) {
                    val photoURI = FileProvider.getUriForFile(
                        activity,
                        activity.packageName + ".provider",
                        photoFile
                    )
                    mPhotoFile = photoFile
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }


        fun takePicture(activity: AppCompatActivity) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            val uri = Uri.fromFile(getOutputMediaFile())

            val photoURI = FileProvider.getUriForFile(
                activity,
                activity.packageName + ".provider",
                getOutputMediaFile()!!
            )

            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

            activity.startActivityForResult(intent, 100)
        }


        private fun getOutputMediaFile(): File? {
            val mediaStorageDir = File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES
                ), "CameraDemo"
            )

            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null
                }
            }

            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            return File(
                mediaStorageDir.path + File.separator +
                        "IMG_" + timeStamp + ".jpg"
            )
        }

        /**
         * Select image fro gallery
         */
        val REQUEST_GALLERY_PHOTO = 105

        private fun dispatchGalleryIntent(activity: AppCompatActivity) {
            val pickPhoto = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            activity.startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO)
        }


        /**
         * Get real file path from URI
         */
        fun getRealPathFromUri(contentUri: Uri, activity: AppCompatActivity): String {
            var cursor: Cursor? = null
            try {
                val proj = arrayOf(MediaStore.Images.Media.DATA)
                cursor = activity.getContentResolver().query(contentUri, proj, null, null, null)
                assert(cursor != null)
                val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor!!.moveToFirst()
                return cursor!!.getString(column_index)
            } finally {
                if (cursor != null) {
                    cursor!!.close()
                }
            }
        }

    }
}