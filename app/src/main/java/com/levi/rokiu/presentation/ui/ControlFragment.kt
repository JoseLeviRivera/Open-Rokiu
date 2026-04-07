package com.levi.rokiu.presentation.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.*
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.levi.rokiu.R
import com.levi.rokiu.databinding.FragmentControlBinding
import com.levi.rokiu.presentation.adapters.ChannelAdapter
import com.levi.rokiu.presentation.adapters.RokuDeviceAdapter
import com.levi.rokiu.presentation.viewmodel.DiscoveryViewModel
import com.levi.rokiu.presentation.viewmodel.GetAppsViewModel
import com.levi.rokiu.presentation.viewmodel.LaunchAppViewModel
import com.levi.rokiu.presentation.viewmodel.RokuViewModel
import com.levi.rokiu.presentation.viewmodel.SendTextViewModel
import com.levi.rokiu.util.PreferencesHelper
import com.levi.rokiu.util.ToastUtil.showToast
import com.levi.rokiu.util.UrlUtil.navigateToURL
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * ControlFragment
 *
 * Main UI controller for interacting with Roku devices.
 *
 * Responsibilities:
 * - Discover and connect to Roku devices
 * - Send remote control commands
 * - Display installed channels
 * - Handle text input (keyboard)
 *
 * This fragment communicates with Roku devices using ViewModels
 * that implement the Roku External Control Protocol (ECP).
 */
@AndroidEntryPoint
class ControlFragment : Fragment(R.layout.fragment_control) {
    // ===========================================
    // VIEW BINDING
    // ===========================================

    private var _binding: FragmentControlBinding? = null
    private val binding get() = _binding!!

    // ===========================================
    // DEPENDENCIES
    // ===========================================

    @Inject
    lateinit var prefsHelper: PreferencesHelper

    // ===========================================
    // VIEWMODELS
    // ===========================================

    private val viewModel: RokuViewModel by viewModels()
    private val discoveryViewModel: DiscoveryViewModel by viewModels()
    private val getAppsViewModel: GetAppsViewModel by viewModels()
    private val launchAppViewModel: LaunchAppViewModel by viewModels()
    private val sendTextViewModel: SendTextViewModel by viewModels()

    // ===========================================
    // UI COMPONENTS
    // ===========================================

    private lateinit var vibrator: Vibrator
    private lateinit var channelAdapter: ChannelAdapter
    private lateinit var deviceAdapter: RokuDeviceAdapter


    // ===========================================
    // STATE
    // ===========================================

    private var currentDeviceUrl: String? = null
    private var isDeviceConnected: Boolean = false

    // ===========================================
    // HANDLERS
    // ===========================================

    private val handler = Handler(Looper.getMainLooper())
    private var repeatRunnable: Runnable? = null

    // ===========================================
    // CONSTANTS
    // ===========================================

    private companion object {
        const val VIBRATION_DURATION_MS = 25L
        const val LONG_PRESS_INITIAL_DELAY_MS = 400L
        const val LONG_PRESS_REPEAT_INTERVAL_MS = 120L
        const val CHANNEL_GRID_COLUMNS = 2
    }

    // ===========================================
    // LIFECYCLE
    // ===========================================
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentControlBinding.bind(view)
        Log.d("DEBUG", "El fragment fue creado")
        setUpUI()
        setUpObservers()
    }


    // ===========================================
    // INITIALIZATION
    // ===========================================
    private fun setUpUI() {
        setUpToolbarAction()
        setUpVibrator()
        setupButtons()
        checkFirstLaunch()
    }

    private fun setUpObservers() {
        observeChannels()
    }

    private fun setUpVibrator() {
        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager: VibratorManager =  requireContext().getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    // ===========================================
    // DEVICE CONNECTION
    // ===========================================
    /**
     * Validates if a Roku device is connected.
     */
    private fun validateDeviceConnection(): Boolean {
        if (!isDeviceConnected || currentDeviceUrl == null) {
            requireContext().showToast(getString(R.string.no_device_connected))
            return false
        }
        return true
    }

    /**
     * Connects to a selected Roku device and initializes channel loading.
     */
    private fun connectToDevice(deviceUrl: String, deviceName: String?) {
        currentDeviceUrl = deviceUrl
        isDeviceConnected = true

        binding.toolbar.title = deviceName
        requireContext().showToast(getString(R.string.connected_by) + deviceName)

        initializeChannelAdapter(deviceUrl)
        Handler(Looper.getMainLooper()).post {
            loadDeviceChannels(deviceUrl)
        }
    }

    // ===========================================
    // CHANNELS
    // ===========================================
    /**
     * Observes channel data and updates UI.
     */
    private fun observeChannels() {
        getAppsViewModel.channels.observe(viewLifecycleOwner) { channels ->
            Log.d("ControlFragment", "Channels received: ${channels.size}")
            if (::channelAdapter.isInitialized) {
                channelAdapter.submitList(channels)
            }
        }
    }

    private fun initializeChannelAdapter(deviceUrl: String) {
        channelAdapter = ChannelAdapter(deviceUrl) { channel ->
            launchAppViewModel.launch(deviceUrl, channel.id)
        }
        binding.recyclerChannels.adapter = channelAdapter
    }

    private fun loadDeviceChannels(deviceUrl: String) {
        getAppsViewModel.start(deviceUrl)
    }

    // ===========================================
    // DEVICE DISCOVERY
    // ===========================================

    /**
     * Displays a bottom sheet to discover and select Roku devices.
     */
    private fun showDeviceSelector() {
        discoveryViewModel.discover()

        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet_devices, null)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerDevices)
        val progress = view.findViewById<View>(R.id.progressDevices)

        discoveryViewModel.isLoading.observe(viewLifecycleOwner) { status ->
            progress.visibility = if (status) View.VISIBLE else View.GONE
            recycler.visibility = if (status) View.GONE else View.VISIBLE
        }

        deviceAdapter = RokuDeviceAdapter { device ->
            connectToDevice(device.location, device.name)
            dialog.dismiss()
        }

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = deviceAdapter

        discoveryViewModel.devices.removeObservers(viewLifecycleOwner)
        discoveryViewModel.devices.observe(viewLifecycleOwner) { devices ->
            deviceAdapter.submitList(devices)

            if (devices.isEmpty()) {
                dialog.dismiss()
            }
        }

        dialog.setContentView(view)
        dialog.show()
    }

    // ===========================================
    // COMMANDS
    // ===========================================

    /**
     * Sends a command to the connected Roku device.
     */
    private fun sendCommand(command: String) {
        if (!validateDeviceConnection()) return

        performVibration()
        viewModel.sendCommand(currentDeviceUrl!!, command)
    }

    private fun performVibration() {
        if (!::vibrator.isInitialized) return
        vibrator.vibrate(
            VibrationEffect.createOneShot(
                VIBRATION_DURATION_MS,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    }

    // ===========================================
    // FIRST LAUNCH DIALOG
    // ===========================================

    private fun checkFirstLaunch() {
        if (prefsHelper.isFirstLaunch) {
            showFirstLaunchDialog()
            prefsHelper.isFirstLaunch = false
        }
    }

    @SuppressLint("InflateParams")
    private fun showFirstLaunchDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .create()

        val view = layoutInflater.inflate(R.layout.dialog_first_launch, null)
        val btnGotIt = view.findViewById<MaterialButton>(R.id.btnGotIt)

        btnGotIt.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCanceledOnTouchOutside(true)
        dialog.setView(view)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    // ===========================================
    // BUTTON SETUP
    // ===========================================

    private fun setupButtons() {
        setupDPadButtons()
        setupSystemButtons()
        setupMediaButtons()
        setupExtraButtons()
        setupVolumeButtons()
        setupChannelsRecycler()
    }

    // ===========================================
    // CHANNELS RECYCLER VIEW
    // ===========================================
    private fun setupChannelsRecycler() {
        val layoutManager = GridLayoutManager(
            requireContext(),
            CHANNEL_GRID_COLUMNS,
            GridLayoutManager.HORIZONTAL,
            false
        )
        binding.recyclerChannels.layoutManager = layoutManager
    }

    private fun setupDPadButtons() = with(binding) {
        setupHoldButton(btnUp, "Up")
        setupHoldButton(btnDown, "Down")
        setupHoldButton(btnLeft, "Left")
        setupHoldButton(btnRight, "Right")
        btnOk.setOnClickListener { sendCommand("Select") }
    }

    private fun setupSystemButtons() = with(binding) {
        btnBack.setOnClickListener { sendCommand("Back") }
        btnHome.setOnClickListener { sendCommand("Home") }
        btnPower.setOnClickListener { sendCommand("Power") }
    }

    private fun setupMediaButtons() = with(binding) {
        btnReplay.setOnClickListener { sendCommand("InstantReplay") }
        btnRewind.setOnClickListener { sendCommand("Rev") }
        btnPlay.setOnClickListener { sendCommand("Play") }
        btnForward.setOnClickListener { sendCommand("Fwd") }
    }

    private fun setupExtraButtons() = with(binding) {
        btnSleep.setOnClickListener { sendCommand("Sleep") }
        btnStar.setOnClickListener { sendCommand("Info") }
    }

    private fun setupVolumeButtons() = with(binding) {
        setupHoldButton(btnVolumeUp, "VolumeUp")
        setupHoldButton(btnVolumeDown, "VolumeDown")
        btnMute.setOnClickListener { sendCommand("VolumeMute") }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupHoldButton(button: View, command: String) {
        button.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    handleButtonPress(command)
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    handleButtonRelease()
                    true
                }
                else -> false
            }
        }
    }

    private fun handleButtonPress(command: String) {
        sendCommand(command)
        repeatRunnable?.let { handler.removeCallbacks(it) }

        repeatRunnable = object : Runnable {
            override fun run() {
                if (isDeviceConnected && currentDeviceUrl != null) {
                    viewModel.sendCommand(currentDeviceUrl!!, command)
                }
                handler.postDelayed(this, LONG_PRESS_REPEAT_INTERVAL_MS)
            }
        }

        handler.postDelayed(repeatRunnable!!, LONG_PRESS_INITIAL_DELAY_MS)
    }

    private fun handleButtonRelease() {
        repeatRunnable?.let { handler.removeCallbacks(it) }
        repeatRunnable = null
    }

    private fun setUpToolbarAction() {
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_devices -> {
                    showDeviceSelector()
                    true
                }

                R.id.action_keyboard -> {
                    showKeyboard()
                    true
                }

                R.id.action_code -> {
                    requireContext().navigateToURL(getString(R.string.url_source))
                    true
                }

                R.id.action_license -> {
                    requireContext().navigateToURL(getString(R.string.url_license))
                    true
                }

                R.id.action_contribute -> {
                    requireContext().navigateToURL(getString(R.string.url_contribute))
                    true
                }

                else -> false
            }
        }
    }

    // ===========================================
    // KEYBOARD DIALOG
    // ===========================================
    /**
     * Displays a dialog for sending text input to the Roku device.
     */
    @SuppressLint("InflateParams")
    private fun showKeyboard() {
        if (!validateDeviceConnection()) {
            return
        }

        val dialog = AlertDialog.Builder(requireContext()).create()
        val view = layoutInflater.inflate(R.layout.dialog_keyboard, null)

        val etText = view.findViewById<TextInputEditText>(R.id.etText)
        val btnCancel = view.findViewById<MaterialButton>(R.id.btnCancel)
        val btnSend = view.findViewById<MaterialButton>(R.id.btnSend)

        setupKeyboardButtons(etText, btnCancel, btnSend, dialog)
        setupKeyboardActions(etText, dialog)

        dialog.setView(view)
        dialog.show()

        showSoftKeyboard(etText)
    }

    private fun setupKeyboardButtons(
        etText: TextInputEditText,
        btnCancel: MaterialButton,
        btnSend: MaterialButton,
        dialog: AlertDialog
    ) {
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnSend.setOnClickListener {
            sendTextIfValid(etText.text.toString(), dialog)
        }
    }

    private fun setupKeyboardActions(etText: TextInputEditText, dialog: AlertDialog) {
        etText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                sendTextIfValid(etText.text.toString(), dialog)
                true
            } else {
                false
            }
        }
    }

    private fun sendTextIfValid(text: String, dialog: AlertDialog) {
        if (text.isNotEmpty()) {
            currentDeviceUrl?.let { url ->
                sendTextViewModel.sendText(url, text)
                dialog.dismiss()
            }
        } else {
            requireContext().showToast(getString(R.string.add_text_first))
        }
    }

    private fun showSoftKeyboard(editText: TextInputEditText) {
        editText.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }
}