/*
 * Copyright 2019 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tensorflow.lite.examples.posenet

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.Rect
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.hardware.camera2.CaptureResult
import android.hardware.camera2.TotalCaptureResult
import android.media.Image
import android.media.ImageReader
import android.media.ImageReader.OnImageAvailableListener
import android.media.MediaPlayer
import android.os.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import android.util.Log
import android.util.Size
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.View.VISIBLE
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import org.tensorflow.lite.examples.posenet.lib.BodyPart
import org.tensorflow.lite.examples.posenet.lib.Person
import org.tensorflow.lite.examples.posenet.lib.Posenet
import kotlin.math.roundToInt
import kotlin.random.Random

class PosenetActivity :
  Fragment(),
  ActivityCompat.OnRequestPermissionsResultCallback {

  /** List of body joints that should be connected.    */
  private val bodyJoints = listOf(
    Pair(BodyPart.LEFT_WRIST, BodyPart.LEFT_ELBOW),
    Pair(BodyPart.LEFT_ELBOW, BodyPart.LEFT_SHOULDER),
    Pair(BodyPart.LEFT_SHOULDER, BodyPart.RIGHT_SHOULDER),
    Pair(BodyPart.RIGHT_SHOULDER, BodyPart.RIGHT_ELBOW),
    Pair(BodyPart.RIGHT_ELBOW, BodyPart.RIGHT_WRIST),
    Pair(BodyPart.LEFT_SHOULDER, BodyPart.LEFT_HIP),
    Pair(BodyPart.LEFT_HIP, BodyPart.RIGHT_HIP),
    Pair(BodyPart.RIGHT_HIP, BodyPart.RIGHT_SHOULDER),
    Pair(BodyPart.LEFT_HIP, BodyPart.LEFT_KNEE),
    Pair(BodyPart.LEFT_KNEE, BodyPart.LEFT_ANKLE),
    Pair(BodyPart.RIGHT_HIP, BodyPart.RIGHT_KNEE),
    Pair(BodyPart.RIGHT_KNEE, BodyPart.RIGHT_ANKLE)
  )

  /** Threshold for confidence score. */
  private val minConfidence = 0.5

  /** Radius of circle used to draw keypoints.  */
  private val circleRadius = 8.0f

  /** Paint class holds the style and color information to draw geometries,text and bitmaps. */
  private var paint = Paint()

  /** A shape for extracting frame data.   */
  private val PREVIEW_WIDTH = 320//640
  private val PREVIEW_HEIGHT = 240//480

  /** An object for the Posenet library.    */
  private lateinit var posenet: Posenet

  /** ID of the current [CameraDevice].   */
  private var cameraId: String? = null

  /** A [SurfaceView] for camera preview.   */
  private var surfaceView: SurfaceView? = null

  /** A [CameraCaptureSession] for camera preview.   */
  private var captureSession: CameraCaptureSession? = null

  /** A reference to the opened [CameraDevice].    */
  private var cameraDevice: CameraDevice? = null

  /** The [android.util.Size] of camera preview.  */
  private var previewSize: Size? = null

  /** The [android.util.Size.getWidth] of camera preview. */
  private var previewWidth = 0

  /** The [android.util.Size.getHeight] of camera preview.  */
  private var previewHeight = 0

  /** A counter to keep count of total frames.  */
  private var frameCounter = 0

  /** An IntArray to save image data in ARGB8888 format  */
  private lateinit var rgbBytes: IntArray

  /** A ByteArray to save image data in YUV format  */
  private var yuvBytes = arrayOfNulls<ByteArray>(3)

  /** An additional thread for running tasks that shouldn't block the UI.   */
  private var backgroundThread: HandlerThread? = null

  /** A [Handler] for running tasks in the background.    */
  private var backgroundHandler: Handler? = null

  /** An [ImageReader] that handles preview frame capture.   */
  private var imageReader: ImageReader? = null

  /** [CaptureRequest.Builder] for the camera preview   */
  private var previewRequestBuilder: CaptureRequest.Builder? = null

  /** [CaptureRequest] generated by [.previewRequestBuilder   */
  private var previewRequest: CaptureRequest? = null

  /** A [Semaphore] to prevent the app from exiting before closing the camera.    */
  private val cameraOpenCloseLock = Semaphore(1)

  /** Whether the current camera device supports Flash or not.    */
  private var flashSupported = false

  /** Orientation of the camera sensor.   */
  private var sensorOrientation: Int? = null

  /** Abstract interface to someone holding a display surface.    */
  private var surfaceHolder: SurfaceHolder? = null

  private lateinit var bgm: MediaPlayer
  private lateinit var cmd: MediaPlayer
  private lateinit var leftUp: MediaPlayer
  private lateinit var leftDown: MediaPlayer
  private lateinit var rightUp: MediaPlayer
  private lateinit var rightDown: MediaPlayer

  private lateinit var command: TextView
  private lateinit var upLeftCompass: ImageView
  private lateinit var upRightCompass: ImageView
  private lateinit var downLeftCompass: ImageView
  private lateinit var downRightCompass: ImageView

  private lateinit var remainingTime: TextView
  private lateinit var timeBar: ProgressBar
  private lateinit var scoreLabel: TextView
  private lateinit var startBtn: ImageButton

  private var rnd = 0 //random number for motion direction
  private var match = false
  private val delayTime = longArrayOf(20000,10000,5000)
  private var level = 0 //higher level -> shorter time for tasks
  private var score = 0 //
  private var progress = 100 //timebar
  private var countDownTime: Double = 20.0 //time remain in second
  private var counting = false

  private val LV1 = 1 //finished 5 times will enter lv 1 (shorter time for tasks)
  private val LV2 = 10

  /** [CameraDevice.StateCallback] is called when [CameraDevice] changes its state.   */
  private val stateCallback = object : CameraDevice.StateCallback() {

    override fun onOpened(cameraDevice: CameraDevice) {
      cameraOpenCloseLock.release()
      this@PosenetActivity.cameraDevice = cameraDevice
      createCameraPreviewSession()
    }

    override fun onDisconnected(cameraDevice: CameraDevice) {
      cameraOpenCloseLock.release()
      cameraDevice.close()
      this@PosenetActivity.cameraDevice = null
    }

    override fun onError(cameraDevice: CameraDevice, error: Int) {
      onDisconnected(cameraDevice)
      this@PosenetActivity.activity?.finish()
    }
  }

  /**
   * A [CameraCaptureSession.CaptureCallback] that handles events related to JPEG capture.
   */
  private val captureCallback = object : CameraCaptureSession.CaptureCallback() {
    override fun onCaptureProgressed(
      session: CameraCaptureSession,
      request: CaptureRequest,
      partialResult: CaptureResult
    ) {
    }

    override fun onCaptureCompleted(
      session: CameraCaptureSession,
      request: CaptureRequest,
      result: TotalCaptureResult
    ) {
    }
  }

  /**
   * Shows a [Toast] on the UI thread.
   *
   * @param text The message to show
   */

  private fun showToast(text: String) {
    val activity = activity
    activity?.runOnUiThread { Toast.makeText(activity, text, Toast.LENGTH_SHORT).show() }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.activity_posenet, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    surfaceView = view.findViewById(R.id.surfaceView)
    surfaceHolder = surfaceView!!.holder

    if(gameID == 1){
      game1init(view)
        val clickListener = View.OnClickListener { view ->
          when (view.id) {
            R.id.playButton -> {
              score = 0
              scoreLabel.text = "Score: 0"
              playRandomCommand()
            }
          }
        }

        startBtn.setOnClickListener(clickListener)
        startBtn.visibility = VISIBLE
        command.visibility = VISIBLE
        scoreLabel.visibility = VISIBLE
        timeBar.visibility = VISIBLE
        remainingTime.visibility = VISIBLE
    }
  }

  private fun game1init(view: View) {
    bgm = MediaPlayer.create(activity, R.raw.piano)
    bgm.isLooping = true
    bgm.setVolume(0.3f, 0.3f)
    bgm.start()
    cmd = MediaPlayer.create(activity, R.raw.begin)
    cmd.setVolume(0.5f, 0.5f)
    leftUp = MediaPlayer.create(activity, R.raw.leftup)
    leftDown = MediaPlayer.create(activity, R.raw.leftdown)
    rightUp = MediaPlayer.create(activity, R.raw.rightup)
    rightDown = MediaPlayer.create(activity, R.raw.rightdown)
    command = view.findViewById(R.id.command)
    upLeftCompass = view.findViewById(R.id.upleftstar)
    upRightCompass = view.findViewById(R.id.uprightstar)
    downLeftCompass = view.findViewById(R.id.downleftstar)
    downRightCompass = view.findViewById(R.id.downrightstar)
    remainingTime = view.findViewById(R.id.remainingTime)
    timeBar = view.findViewById(R.id.timeBar)
    scoreLabel = view.findViewById(R.id.scoreLabel)
    startBtn= view.findViewById(R.id.playButton)

    val builder = AlertDialog.Builder(this@PosenetActivity.activity)
    builder.setMessage("Press the start button and follow the text/audio commands!\n\nCatch me if you can A_A")
    builder.setCancelable(true)
    builder.setPositiveButton("OK!") { _, _ -> }
    builder.create().show()
  }

  private fun playRandomCommand() {
//    startBtn.isEnabled = false
    startBtn.visibility = GONE
    upLeftCompass.visibility = GONE
    upRightCompass.visibility = GONE
    downLeftCompass.visibility = GONE
    downRightCompass.visibility = GONE
    if (cmd.isPlaying) cmd.pause()
    if (leftUp.isPlaying) leftUp.pause()
    if (leftDown.isPlaying) leftDown.pause()
    if (rightUp.isPlaying) rightUp.pause()
    if (rightDown.isPlaying) rightDown.pause()
    cmd.start()
    val temp = rnd
    rnd = (temp + Random.nextInt(1, 4)) % 4 //prevent same number as last time
    when (rnd) {
      0 -> {
        upLeftCompass.visibility = VISIBLE
        command.text = "raise up your left hand"
        leftUp.start()
      }
      1 -> {
        downLeftCompass.visibility = VISIBLE
        command.text = "put down your left hand"
        leftDown.start()
      }
      2 -> {
        upRightCompass.visibility = VISIBLE
        command.text = "raise up your right hand"
        rightUp.start()
      }
      else -> {
        downRightCompass.visibility = VISIBLE
        command.text = "put down your right hand"
        rightDown.start()
      }
    }
    progress = 100
    countDownTime = delayTime[level]/1000.0
    when(level){
      0 -> {
        Handler().postDelayed({timer0.start()},3000)
      }
      1 -> {
        Handler().postDelayed({timer1.start()},3000)
      }
      2 -> {
        Handler().postDelayed({timer2.start()}, 3000)
      }
    }
    counting = true
  }

  private fun timerFinish(){
    counting = false
    upLeftCompass.visibility = GONE
    upRightCompass.visibility = GONE
    downLeftCompass.visibility = GONE
    downRightCompass.visibility = GONE
    command.text = "GAME OVER!"
    val pref = this@PosenetActivity.activity?.getSharedPreferences("Game_Data", AppCompatActivity.MODE_PRIVATE)
    val maxScore = pref?.getInt("MaxScore1", 0)
    var flag = false
    if(score > maxScore!!){
      pref?.edit()
        ?.putInt("MaxScore1", score)
        ?.apply()
      flag = true
    }
    level = 0
    progress = 100
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      timeBar.setProgress(progress, true)
    }
    remainingTime.text = "Time Remaining"
    startBtn.visibility = VISIBLE
    val builder = AlertDialog.Builder(this@PosenetActivity.activity)
    if(flag) builder.setMessage("Congrats! You broke the record!:D\n\n" +
            "Press anywhere to close me ><")
    else builder.setMessage("Add oil! You will get higher score next time!\n\n" +
            "Press anywhere to close me ><")
    builder.setCancelable(true)
    builder.create().show()
  }
  private val timer0 = object: CountDownTimer(delayTime[0], 200) {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onTick(millisUntilFinished: Long) {
      timeBar.setProgress(progress - 1, true)
      progress -= 1
      countDownTime -= 0.2
      remainingTime.text = countDownTime.roundToInt().toString()
    }

    override fun onFinish() {
      timerFinish()
    }
  }
  private val timer1 = object: CountDownTimer(delayTime[1], 200) {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onTick(millisUntilFinished: Long) {
      timeBar.setProgress(progress - 2, true)
      progress -= 2
      countDownTime -= 0.2
      remainingTime.text = countDownTime.roundToInt().toString()
    }

    override fun onFinish() {
      timerFinish()
    }
  }
  private val timer2 = object: CountDownTimer(delayTime[2], 200) {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onTick(millisUntilFinished: Long) {
      timeBar.setProgress(progress - 4, true)
      progress -= 4
      countDownTime -= 0.2
      remainingTime.text = countDownTime.roundToInt().toString()
    }

    override fun onFinish() {
      timerFinish()
    }
  }

  private fun taskFinished(){
    when(level){
      0 -> timer0.cancel()
      1 -> timer1.cancel()
      2 -> timer2.cancel()
    }
    counting = false
    score++
    val text = "Score: $score"
    scoreLabel.text = text
    if(score == LV1) level = 1
    if(score == LV2) level = 2
    progress = 100
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      timeBar.setProgress(progress, true)
    }
    countDownTime = delayTime[level]/1000.0
    remainingTime.text = "Time Remaining"
    playRandomCommand()
  }

  override fun onResume() {
    super.onResume()
    startBackgroundThread()
  }

  override fun onStart() {
    super.onStart()
    openCamera()
    posenet = Posenet(this.context!!)
    if(gameID == 1) bgm.start()
  }

  override fun onPause() {
    closeCamera()
    if(gameID == 1) bgm.pause()
    stopBackgroundThread()
    super.onPause()
  }

  override fun onStop() {
    closeCamera()
    super.onStop()
  }

  override fun onDestroy() {
    super.onDestroy()
    posenet.close()
  }

  private fun requestCameraPermission() {
    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
      ConfirmationDialog().show(childFragmentManager, FRAGMENT_DIALOG)
    } else {
      requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
    }
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    if (requestCode == REQUEST_CAMERA_PERMISSION) {
      if (allPermissionsGranted(grantResults)) {
        ErrorDialog.newInstance(getString(R.string.request_permission))
          .show(childFragmentManager, FRAGMENT_DIALOG)
      }
    } else {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
  }

  private fun allPermissionsGranted(grantResults: IntArray) = grantResults.all {
    it == PackageManager.PERMISSION_GRANTED
  }

  /**
   * Sets up member variables related to camera.
   */
  private fun setUpCameraOutputs() {

    val activity = activity
    val manager = activity!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    try {
      for (cameraId in manager.cameraIdList) {
        val characteristics = manager.getCameraCharacteristics(cameraId)

        // We don't use a front facing camera in this sample.
        val cameraDirection = characteristics.get(CameraCharacteristics.LENS_FACING)
        if (cameraDirection != null &&
        cameraDirection == CameraCharacteristics.LENS_FACING_BACK
//        cameraDirection == CameraCharacteristics.LENS_FACING_FRONT //DEBUG
        ) {
          continue
        }

        previewSize = Size(PREVIEW_WIDTH, PREVIEW_HEIGHT)

        imageReader = ImageReader.newInstance(
          PREVIEW_WIDTH, PREVIEW_HEIGHT,
          ImageFormat.YUV_420_888, /*maxImages*/ 2
        )
        sensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)!!

        previewHeight = previewSize!!.height
        previewWidth = previewSize!!.width

        // Initialize the storage bitmaps once when the resolution is known.
        rgbBytes = IntArray(previewWidth * previewHeight)

        // Check if the flash is supported.
        flashSupported =
          characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true

        this.cameraId = cameraId

        // We've found a viable camera and finished setting up member variables,
        // so we don't need to iterate through other available cameras.
        return
      }
    } catch (e: CameraAccessException) {
      Log.e(TAG, e.toString())
    } catch (e: NullPointerException) {
      // Currently an NPE is thrown when the Camera2API is used but not supported on the
      // device this code runs.
      ErrorDialog.newInstance(getString(R.string.camera_error))
        .show(childFragmentManager, FRAGMENT_DIALOG)
    }
  }

  /**
   * Opens the camera specified by [PosenetActivity.cameraId].
   */
  private fun openCamera() {
    val permissionCamera = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA)
    if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
      requestCameraPermission()
    }
    setUpCameraOutputs()
    val manager = activity!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    try {
      // Wait for camera to open - 2.5 seconds is sufficient
      if (!cameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
        throw RuntimeException("Time out waiting to lock camera opening.")
      }
      manager.openCamera(cameraId!!, stateCallback, backgroundHandler)
    } catch (e: CameraAccessException) {
      Log.e(TAG, e.toString())
    } catch (e: InterruptedException) {
      throw RuntimeException("Interrupted while trying to lock camera opening.", e)
    }
  }

  /**
   * Closes the current [CameraDevice].
   */
  private fun closeCamera() {
    if (captureSession == null) {
      return
    }

    try {
      cameraOpenCloseLock.acquire()
      captureSession!!.close()
      captureSession = null
      cameraDevice!!.close()
      cameraDevice = null
      imageReader!!.close()
      imageReader = null
    } catch (e: InterruptedException) {
      throw RuntimeException("Interrupted while trying to lock camera closing.", e)
    } finally {
      cameraOpenCloseLock.release()
    }
  }

  /**
   * Starts a background thread and its [Handler].
   */
  private fun startBackgroundThread() {
    backgroundThread = HandlerThread("imageAvailableListener").also { it.start() }
    backgroundHandler = Handler(backgroundThread!!.looper)
  }

  /**
   * Stops the background thread and its [Handler].
   */
  private fun stopBackgroundThread() {
    backgroundThread?.quitSafely()
    try {
      backgroundThread?.join()
      backgroundThread = null
      backgroundHandler = null
    } catch (e: InterruptedException) {
      Log.e(TAG, e.toString())
    }
  }

  /** Fill the yuvBytes with data from image planes.   */
  private fun fillBytes(planes: Array<Image.Plane>, yuvBytes: Array<ByteArray?>) {
    // Row stride is the total number of bytes occupied in memory by a row of an image.
    // Because of the variable row stride it's not possible to know in
    // advance the actual necessary dimensions of the yuv planes.
    for (i in planes.indices) {
      val buffer = planes[i].buffer
      if (yuvBytes[i] == null) {
        yuvBytes[i] = ByteArray(buffer.capacity())
      }
      buffer.get(yuvBytes[i]!!)
    }
  }

  /** A [OnImageAvailableListener] to receive frames as they are available.  */
  private var imageAvailableListener = object : OnImageAvailableListener {
    override fun onImageAvailable(imageReader: ImageReader) {
      // We need wait until we have some size from onPreviewSizeChosen
      if (previewWidth == 0 || previewHeight == 0) {
        return
      }

      val image = imageReader.acquireLatestImage() ?: return
      fillBytes(image.planes, yuvBytes)

      ImageUtils.convertYUV420ToARGB8888(
        yuvBytes[0]!!,
        yuvBytes[1]!!,
        yuvBytes[2]!!,
        previewWidth,
        previewHeight,
        /*yRowStride=*/ image.planes[0].rowStride,
        /*uvRowStride=*/ image.planes[1].rowStride,
        /*uvPixelStride=*/ image.planes[1].pixelStride,
        rgbBytes
      )

      // Create bitmap from int array
      val imageBitmap = Bitmap.createBitmap(
        rgbBytes, previewWidth, previewHeight,
        Bitmap.Config.ARGB_8888
      )

      // Create rotated version for portrait display
      val rotateMatrix = Matrix()
      rotateMatrix.postRotate(-90.0f)
      rotateMatrix.postScale(-1f, 1f)
      /*DEBUG*/
//      rotateMatrix.postRotate(90.0f)

      val rotatedBitmap = Bitmap.createBitmap(
        imageBitmap, 0, 0, previewWidth, previewHeight,
        rotateMatrix, true
      )
      image.close()

      // Process an image for analysis in every 3 frames.
      frameCounter = (frameCounter + 1) % 1
      if (frameCounter == 0) {
        processImage(rotatedBitmap)
      }
    }
  }

  /** Crop Bitmap to maintain aspect ratio of model input.   */
  private fun cropBitmap(bitmap: Bitmap): Bitmap {
    val bitmapRatio = bitmap.height.toFloat() / bitmap.width
    val modelInputRatio = MODEL_HEIGHT.toFloat() / MODEL_WIDTH
    var croppedBitmap = bitmap

    // Acceptable difference between the modelInputRatio and bitmapRatio to skip cropping.
    val maxDifference = 1e-5

    // Checks if the bitmap has similar aspect ratio as the required model input.
    when {
      abs(modelInputRatio - bitmapRatio) < maxDifference -> return croppedBitmap
      modelInputRatio < bitmapRatio -> {
        // New image is taller so we are height constrained.
        val cropHeight = bitmap.height - (bitmap.width.toFloat() / modelInputRatio)
        croppedBitmap = Bitmap.createBitmap(
          bitmap,
          0,
          (cropHeight / 2).toInt(),
          bitmap.width,
          (bitmap.height - cropHeight).toInt()
        )
      }
      else -> {
        val cropWidth = bitmap.width - (bitmap.height.toFloat() * modelInputRatio)
        croppedBitmap = Bitmap.createBitmap(
          bitmap,
          (cropWidth / 2).toInt(),
          0,
          (bitmap.width - cropWidth).toInt(),
          bitmap.height
        )
      }
    }
    return croppedBitmap
  }

  /** Set the paint color and size.    */
  private fun setPaint() {
    paint.color = Color.BLUE
    paint.textSize = 50.0f
    paint.strokeWidth = 6.0f
  }

  /** Draw bitmap on Canvas.   */
  private fun draw(canvas: Canvas, person: Person, bitmap: Bitmap) {
    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
    // Draw `bitmap` and `person` in square canvas.
    val screenWidth: Int
    val screenHeight: Int
    val left: Int
    val right: Int
    val top: Int
    val bottom: Int
    if (canvas.height > canvas.width) {
      screenWidth = canvas.width
      screenHeight = canvas.width
      left = 0
      top = (canvas.height - canvas.width) / 2
    } else {
      screenWidth = canvas.height
      screenHeight = canvas.height
      left = (canvas.width - canvas.height) / 2
      top = 0
    }
    right = left + screenWidth
    bottom = top + screenHeight

    setPaint()
    canvas.drawBitmap(
      bitmap,
      Rect(0, 0, bitmap.width, bitmap.height),
      Rect(left, top, right, bottom),
      paint
    )

    val widthRatio = screenWidth.toFloat() / MODEL_WIDTH
    val heightRatio = screenHeight.toFloat() / MODEL_HEIGHT

    // Draw key points over the image.
    for (keyPoint in person.keyPoints) {
      if (keyPoint.score > minConfidence) {
        val position = keyPoint.position
        val adjustedX: Float = position.x.toFloat() * widthRatio + left
        val adjustedY: Float = position.y.toFloat() * heightRatio + top
//        canvas.drawCircle(adjustedX, adjustedY, circleRadius, paint)
      } else {  //default releasing arms
        if(keyPoint.bodyPart == BodyPart.RIGHT_WRIST||keyPoint.bodyPart == BodyPart.RIGHT_ELBOW){
          armspos[0] = false
        }
        if(keyPoint.bodyPart == BodyPart.LEFT_WRIST||keyPoint.bodyPart == BodyPart.RIGHT_WRIST){
          armspos[1] = false
        }
        if(!armspos[0] && !armspos[1]){
          handup = false
        }
      }
    }
    for (line in bodyJoints) {
      if ((person.keyPoints[line.first.ordinal].score > minConfidence)
        and (person.keyPoints[line.second.ordinal].score > minConfidence)) {
          if(!(line == Pair(BodyPart.LEFT_HIP, BodyPart.LEFT_KNEE) ||
            line == Pair(BodyPart.LEFT_KNEE, BodyPart.LEFT_ANKLE) ||
            line == Pair(BodyPart.RIGHT_HIP, BodyPart.RIGHT_KNEE) ||
            line == Pair(BodyPart.RIGHT_KNEE, BodyPart.RIGHT_ANKLE)))
              canvas.drawLine(
                person.keyPoints[line.first.ordinal].position.x.toFloat() * widthRatio + left,
                person.keyPoints[line.first.ordinal].position.y.toFloat() * heightRatio + top,
                person.keyPoints[line.second.ordinal].position.x.toFloat() * widthRatio + left,
                person.keyPoints[line.second.ordinal].position.y.toFloat() * heightRatio + top,
                paint)
        /*Left and right are reversed*/
        if(gameID != 1){
          if (line == Pair(BodyPart.LEFT_WRIST, BodyPart.LEFT_ELBOW)) {
            val leftWristY = person.keyPoints[line.first.ordinal].position.y.toFloat()
            val leftElbowY = person.keyPoints[line.second.ordinal].position.y.toFloat()
            if (leftElbowY > leftWristY) {
              canvas.drawText("up", (right - 40.0f * widthRatio), (20.0f * heightRatio + top), paint)
              handup = true
              armspos[1] = true
            } else {
              canvas.drawText("down", (right - 70.0f * widthRatio), (20.0f * heightRatio + top), paint)
              armspos[1] = false
            }
          }
          else if (line == Pair(BodyPart.RIGHT_ELBOW, BodyPart.RIGHT_WRIST)) {
            val rightElbowY = person.keyPoints[line.first.ordinal].position.y.toFloat()
            val rightWristY = person.keyPoints[line.second.ordinal].position.y.toFloat()
            if (rightWristY < rightElbowY) {
              canvas.drawText("up", (20.0f * widthRatio), (20.0f * heightRatio + top), paint)
              handup = true
              armspos[0] = true
            } else {
              canvas.drawText("down", (20.0f * widthRatio), (20.0f * heightRatio + top), paint)
              armspos[0] = false
            }
          }
        }

        if (gameID == 1) {
          if (line == Pair(BodyPart.LEFT_ELBOW, BodyPart.LEFT_SHOULDER)) {
            val leftElbowY = person.keyPoints[line.first.ordinal].position.y.toFloat()
            val leftShoulderY = person.keyPoints[line.second.ordinal].position.y.toFloat()
            if (leftShoulderY > leftElbowY) {
              canvas.drawText("up", (right - 40.0f * widthRatio), (20.0f * heightRatio + top), paint)
              if (
                !rightUp.isPlaying &&
                counting && rnd == 2) {
                Handler(Looper.getMainLooper()).post { taskFinished() }
                match = true
              }
            } else {
              canvas.drawText("down", (right - 70.0f * widthRatio), (20.0f * heightRatio + top), paint)
              if (
                !rightDown.isPlaying &&
                counting && rnd == 3) {
                Handler(Looper.getMainLooper()).post { taskFinished() }
                match = true
              }
            }
          }
          else if (line == Pair(BodyPart.RIGHT_SHOULDER, BodyPart.RIGHT_ELBOW)) {
            val rightShoulderY = person.keyPoints[line.first.ordinal].position.y.toFloat()
            val rightElbowY = person.keyPoints[line.second.ordinal].position.y.toFloat()
            if (rightElbowY < rightShoulderY) {
              canvas.drawText("up", (20.0f * widthRatio), (20.0f * heightRatio + top), paint)
              if (
                !leftUp.isPlaying &&
                counting && rnd == 0) {
                Handler(Looper.getMainLooper()).post { taskFinished() }
                match = true
              }
            } else {
              canvas.drawText("down", (20.0f * widthRatio), (20.0f * heightRatio + top), paint)
              if (
                !leftDown.isPlaying &&
                counting && rnd == 1) {
                Handler(Looper.getMainLooper()).post { taskFinished() }
                match = true
              }
            }
          }
//          if(match){
//            match = false
//            Handler(Looper.getMainLooper()).post { taskFinished() }
//          }
        }
      }
    }

//    canvas.drawText(
//      "Score: %.2f".format(person.score),
//      (15.0f * widthRatio),
//      (30.0f * heightRatio + bottom),
//      paint
//    )
//    canvas.drawText(
//      "Device: %s".format(posenet.device),
//      (15.0f * widthRatio),
//      (50.0f * heightRatio + bottom),
//      paint
//    )
//    canvas.drawText(
//      "Time: %.2f ms".format(posenet.lastInferenceTimeNanos * 1.0f / 1_000_000),
//      (15.0f * widthRatio),
//      (70.0f * heightRatio + bottom),
//      paint
//    )

    // Draw!
    surfaceHolder!!.unlockCanvasAndPost(canvas)
  }

  /** Process image using Posenet library.   */
  private fun processImage(bitmap: Bitmap) {
    // Crop bitmap.
    val croppedBitmap = cropBitmap(bitmap)

    // Created scaled version of bitmap for model input.
    val scaledBitmap = Bitmap.createScaledBitmap(croppedBitmap, MODEL_WIDTH, MODEL_HEIGHT, true)

    // Perform inference.
    val person = posenet.estimateSinglePose(scaledBitmap)
    val canvas: Canvas = surfaceHolder!!.lockCanvas()
    draw(canvas, person, scaledBitmap)
  }

  /**
   * Creates a new [CameraCaptureSession] for camera preview.
   */
  private fun createCameraPreviewSession() {
    try {

      // We capture images from preview in YUV format.
      imageReader = ImageReader.newInstance(
        previewSize!!.width, previewSize!!.height, ImageFormat.YUV_420_888, 2
      )
      imageReader!!.setOnImageAvailableListener(imageAvailableListener, backgroundHandler)

      // This is the surface we need to record images for processing.
      val recordingSurface = imageReader!!.surface

      // We set up a CaptureRequest.Builder with the output Surface.
      previewRequestBuilder = cameraDevice!!.createCaptureRequest(
        CameraDevice.TEMPLATE_PREVIEW
      )
      previewRequestBuilder!!.addTarget(recordingSurface)

      // Here, we create a CameraCaptureSession for camera preview.
      cameraDevice!!.createCaptureSession(
        listOf(recordingSurface),
        object : CameraCaptureSession.StateCallback() {
          override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
            // The camera is already closed
            if (cameraDevice == null) return

            // When the session is ready, we start displaying the preview.
            captureSession = cameraCaptureSession
            try {
              // Auto focus should be continuous for camera preview.
              previewRequestBuilder!!.set(
                CaptureRequest.CONTROL_AF_MODE,
                CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
              )
              // Flash is automatically enabled when necessary.
              setAutoFlash(previewRequestBuilder!!)

              // Finally, we start displaying the camera preview.
              previewRequest = previewRequestBuilder!!.build()
              captureSession!!.setRepeatingRequest(
                previewRequest!!,
                captureCallback, backgroundHandler
              )
            } catch (e: CameraAccessException) {
              Log.e(TAG, e.toString())
            }
          }

          override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession) {
            showToast("Failed")
          }
        },
        null
      )
    } catch (e: CameraAccessException) {
      Log.e(TAG, e.toString())
    }
  }

  private fun setAutoFlash(requestBuilder: CaptureRequest.Builder) {
    if (flashSupported) {
      requestBuilder.set(
        CaptureRequest.CONTROL_AE_MODE,
        CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH
      )
    }
  }

  /**
   * Shows an error message dialog.
   */
  class ErrorDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
      AlertDialog.Builder(activity)
        .setMessage(arguments!!.getString(ARG_MESSAGE))
        .setPositiveButton(android.R.string.ok) { _, _ -> activity!!.finish() }
        .create()

    companion object {

      @JvmStatic
      private val ARG_MESSAGE = "message"

      @JvmStatic
      fun newInstance(message: String): ErrorDialog = ErrorDialog().apply {
        arguments = Bundle().apply { putString(ARG_MESSAGE, message) }
      }
    }
  }

  companion object {
    /**
     * Conversion from screen rotation to JPEG orientation.
     */
    private val ORIENTATIONS = SparseIntArray()
    private const val FRAGMENT_DIALOG = "dialog"
    var gameID = 0
    var armspos  = booleanArrayOf(false, false) //left down and right down
    var handup = false

    init {
      ORIENTATIONS.append(Surface.ROTATION_0, 90)
      ORIENTATIONS.append(Surface.ROTATION_90, 0)
      ORIENTATIONS.append(Surface.ROTATION_180, 270)
      ORIENTATIONS.append(Surface.ROTATION_270, 180)
    }

    /**
     * Tag for the [Log].
     */
    private const val TAG = "PosenetActivity"
  }
}
