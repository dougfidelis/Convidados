package com.example.convidados.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.convidados.R
import com.example.convidados.databinding.ActivityGuestFormBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.repository.GuestsRepository
import com.example.convidados.viewmodel.GuestsFormViewModel

class GuestsFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestsFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(GuestsFormViewModel::class.java)

        binding.confirmationButton.setOnClickListener(this)
        binding.radioPresent.isChecked = true
    }

    override fun onClick(view: View) {
        if (view.id == R.id.confirmation_button) {
            val name = binding.editName.text.toString()
            val presence = binding.radioPresent.isChecked
            val guest = GuestModel(0, name, presence)
            viewModel.insert(guest)

        }
    }
}