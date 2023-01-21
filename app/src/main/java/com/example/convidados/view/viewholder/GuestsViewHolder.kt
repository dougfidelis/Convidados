package com.example.convidados.view.viewholder

import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.R
import com.example.convidados.databinding.RowGuestBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.view.listener.OnGuestListener

class GuestsViewHolder(private val bind: RowGuestBinding, private val listener: OnGuestListener) :
    RecyclerView.ViewHolder(bind.root) {

    fun bind(guest: GuestModel) {

        bind.textName.text = guest.name
        bind.textPresence.text = if (guest.presence) "NO" else "YES"

        bind.textName.setOnClickListener {
            listener.onClick(guest.id)
        }

        bind.textName.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(itemView.context.getString(R.string.remove_guest))
                .setMessage(itemView.context.getString(R.string.remove_guest_message))
                .setPositiveButton(
                    itemView.context.getString(R.string.confirm)
                ) { _, _ -> listener.onDelete(guest.id) }
                .setNegativeButton(
                    itemView.context.getString(R.string.cancel), null
                )
                .create()
                .show()

            true
        }
    }
}