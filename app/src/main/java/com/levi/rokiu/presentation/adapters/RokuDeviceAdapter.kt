package com.levi.rokiu.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.levi.rokiu.R
import com.levi.rokiu.data.model.RokuDeviceUi

class RokuDeviceAdapter(
    private val onClick: (RokuDeviceUi) -> Unit
) : RecyclerView.Adapter<RokuDeviceAdapter.DeviceViewHolder>() {

    private val devices = mutableListOf<RokuDeviceUi>()

    fun submitList(list: List<RokuDeviceUi>) {
        devices.clear()
        devices.addAll(list)
        notifyDataSetChanged()
    }

    inner class DeviceViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name = view.findViewById<TextView>(R.id.tvDeviceName)
        val ip = view.findViewById<TextView>(R.id.tvDeviceIp)

        fun bind(device: RokuDeviceUi) {

            name.text = device.name
            ip.text = device.ip

            itemView.setOnClickListener {
                onClick(device)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_roku_device, parent, false)

        return DeviceViewHolder(view)
    }

    override fun getItemCount() = devices.size

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(devices[position])
    }
}