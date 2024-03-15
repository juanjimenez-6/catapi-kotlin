package com.juanjimenez.catapi.ui.RandomCat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.juanjimenez.catapi.databinding.FragmentRandomCatBinding
import com.juanjimenez.catapi.services.NetworkClient

class RandomCatView : Fragment() {

    private var _binding: FragmentRandomCatBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var randomCatViewModel: RandomCatViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = RandomCatViewModelFactory(NetworkClient.catApiService)
        randomCatViewModel = ViewModelProvider(this, factory).get(RandomCatViewModel::class.java)

        _binding = FragmentRandomCatBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val randomCatCompose = binding.randomCatCompose
        randomCatCompose.setContent {
            MaterialTheme {
                RandomCatViewCompose(randomCatViewModel)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}