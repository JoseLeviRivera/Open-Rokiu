package com.levi.rokiu.domain.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root


@Root(name = "device-info", strict = false)
data class DeviceInfo(

    @field:Element(name = "udn", required = false)
    var udn: String? = null,

    @field:Element(name = "serial-number", required = false)
    var serialNumber: String? = null,

    @field:Element(name = "device-id", required = false)
    var deviceId: String? = null,

    @field:Element(name = "advertising-id", required = false)
    var advertisingId: String? = null,

    @field:Element(name = "user-profile-type", required = false)
    var userProfileType: String? = null,

    @field:Element(name = "vendor-name", required = false)
    var vendorName: String? = null,

    @field:Element(name = "model-name", required = false)
    var modelName: String? = null,

    @field:Element(name = "model-number", required = false)
    var modelNumber: String? = null,

    @field:Element(name = "model-region", required = false)
    var modelRegion: String? = null,

    @field:Element(name = "is-tv", required = false)
    var isTv: Boolean = false,

    @field:Element(name = "is-stick", required = false)
    var isStick: Boolean = false,

    @field:Element(name = "screen-size", required = false)
    var screenSize: String? = null,

    @field:Element(name = "panel-id", required = false)
    var panelId: String? = null,

    @field:Element(name = "mobile-has-live-tv", required = false)
    var mobileHasLiveTv: Boolean = false,

    @field:Element(name = "ui-resolution", required = false)
    var uiResolution: String? = null,

    @field:Element(name = "tuner-type", required = false)
    var tunerType: String? = null,

    @field:Element(name = "supports-ethernet", required = false)
    var supportsEthernet: Boolean = false,

    @field:Element(name = "wifi-mac", required = false)
    var wifiMac: String? = null,

    @field:Element(name = "wifi-driver", required = false)
    var wifiDriver: String? = null,

    @field:Element(name = "has-wifi-5G-support", required = false)
    var hasWifi5GSupport: Boolean = false,

    @field:Element(name = "network-type", required = false)
    var networkType: String? = null,

    @field:Element(name = "network-name", required = false)
    var networkName: String? = null,

    @field:Element(name = "friendly-device-name", required = false)
    var friendlyDeviceName: String? = null,

    @field:Element(name = "friendly-model-name", required = false)
    var friendlyModelName: String? = null,

    @field:Element(name = "default-device-name", required = false)
    var defaultDeviceName: String? = null,

    @field:Element(name = "user-device-name", required = false)
    var userDeviceName: String? = null,

    @field:Element(name = "user-device-location", required = false)
    var userDeviceLocation: String? = null,

    @field:Element(name = "build-number", required = false)
    var buildNumber: String? = null,

    @field:Element(name = "software-version", required = false)
    var softwareVersion: String? = null,

    @field:Element(name = "software-build", required = false)
    var softwareBuild: String? = null,

    @field:Element(name = "lightning-base-build-number", required = false)
    var lightningBaseBuildNumber: String? = null,

    @field:Element(name = "ui-build-number", required = false)
    var uiBuildNumber: String? = null,

    @field:Element(name = "ui-software-version", required = false)
    var uiSoftwareVersion: String? = null,

    @field:Element(name = "ui-software-build", required = false)
    var uiSoftwareBuild: String? = null,

    @field:Element(name = "secure-device", required = false)
    var secureDevice: Boolean = false,

    @field:Element(name = "ecp-setting-mode", required = false)
    var ecpSettingMode: String? = null,

    @field:Element(name = "language", required = false)
    var language: String? = null,

    @field:Element(name = "country", required = false)
    var country: String? = null,

    @field:Element(name = "locale", required = false)
    var locale: String? = null,

    @field:Element(name = "closed-caption-mode", required = false)
    var closedCaptionMode: String? = null,

    @field:Element(name = "time-zone-auto", required = false)
    var timeZoneAuto: Boolean = false,

    @field:Element(name = "time-zone", required = false)
    var timeZone: String? = null,

    @field:Element(name = "time-zone-name", required = false)
    var timeZoneName: String? = null,

    @field:Element(name = "time-zone-tz", required = false)
    var timeZoneTz: String? = null,

    @field:Element(name = "time-zone-offset", required = false)
    var timeZoneOffset: String? = null,

    @field:Element(name = "clock-format", required = false)
    var clockFormat: String? = null,

    @field:Element(name = "uptime", required = false)
    var uptime: Int = 0,

    @field:Element(name = "power-mode", required = false)
    var powerMode: String? = null,

    @field:Element(name = "supports-suspend", required = false)
    var supportsSuspend: Boolean = false,

    @field:Element(name = "supports-find-remote", required = false)
    var supportsFindRemote: Boolean = false,

    @field:Element(name = "supports-audio-guide", required = false)
    var supportsAudioGuide: Boolean = false,

    @field:Element(name = "supports-rva", required = false)
    var supportsRva: Boolean = false,

    @field:Element(name = "has-hands-free-voice-remote", required = false)
    var hasHandsFreeVoiceRemote: Boolean = false,

    @field:Element(name = "developer-enabled", required = false)
    var developerEnabled: Boolean = false,

    @field:Element(name = "keyed-developer-id", required = false)
    var keyedDeveloperId: String? = null,

    @field:Element(name = "device-automation-bridge-enabled", required = false)
    var deviceAutomationBridgeEnabled: Boolean = false,

    @field:Element(name = "search-enabled", required = false)
    var searchEnabled: Boolean = false,

    @field:Element(name = "search-channels-enabled", required = false)
    var searchChannelsEnabled: Boolean = false,

    @field:Element(name = "voice-search-enabled", required = false)
    var voiceSearchEnabled: Boolean = false,

    @field:Element(name = "supports-private-listening", required = false)
    var supportsPrivateListening: Boolean = false,

    @field:Element(name = "private-listening-blocked", required = false)
    var privateListeningBlocked: Boolean = false,

    @field:Element(name = "supports-private-listening-dtv", required = false)
    var supportsPrivateListeningDtv: Boolean = false,

    @field:Element(name = "supports-warm-standby", required = false)
    var supportsWarmStandby: Boolean = false,

    @field:Element(name = "headphones-connected", required = false)
    var headphonesConnected: Boolean = false,

    @field:Element(name = "supports-audio-settings", required = false)
    var supportsAudioSettings: Boolean = false,

    @field:Element(name = "supports-ecs-textedit", required = false)
    var supportsEcsTextedit: Boolean = false,

    @field:Element(name = "supports-ecs-microphone", required = false)
    var supportsEcsMicrophone: Boolean = false,

    @field:Element(name = "supports-wake-on-wlan", required = false)
    var supportsWakeOnWlan: Boolean = false,

    @field:Element(name = "supports-airplay", required = false)
    var supportsAirplay: Boolean = false,

    @field:Element(name = "has-play-on-roku", required = false)
    var hasPlayOnRoku: Boolean = false,

    @field:Element(name = "has-mobile-screensaver", required = false)
    var hasMobileScreensaver: Boolean = false,

    @field:Element(name = "support-url", required = false)
    var supportUrl: String? = null,

    @field:Element(name = "grandcentral-version", required = false)
    var grandcentralVersion: String? = null,

    @field:Element(name = "supports-trc", required = false)
    var supportsTrc: Boolean = false,

    @field:Element(name = "trc-version", required = false)
    var trcVersion: String? = null,

    @field:Element(name = "trc-channel-version", required = false)
    var trcChannelVersion: String? = null,

    @field:Element(name = "av-sync-calibration-enabled", required = false)
    var avSyncCalibrationEnabled: String? = null
)