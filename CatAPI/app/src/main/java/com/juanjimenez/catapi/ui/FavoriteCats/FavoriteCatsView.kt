package com.juanjimenez.catapi.ui.FavoriteCats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.juanjimenez.catapi.databinding.FragmentFavoritesBinding
import com.juanjimenez.catapi.services.NetworkClient

class FavoriteCatsView : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var favoriteCatsViewModel: FavoriteCatsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = FavoriteCatsViewModelFactory(NetworkClient.catApiService)
        favoriteCatsViewModel = ViewModelProvider(this, factory).get(FavoriteCatsViewModel::class.java)

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val favoriteCatsCompose = binding.favoriteCatsCompose
        favoriteCatsCompose.setContent {
            MaterialTheme {
                FavoriteCatsViewCompose(favoriteCatsViewModel)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}