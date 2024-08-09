package com.gualoto.pfinaldm.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.data.network.entities.anime.entities.FullInfoAnimeLG
import com.gualoto.pfinaldm.databinding.FragmentDetailBinding
import com.gualoto.pfinaldm.logic.usercase.anime.GetAnimeTopUserCase
import com.gualoto.pfinaldm.ui.viewmodels.main.DetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    val args: DetailFragmentArgs by navArgs()
    private var usersList: FullInfoAnimeLG? = null

    private val detailVM: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentDetailBinding.bind(inflater.inflate(R.layout.fragment_detail, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListener()
        detailVM.loadInfoAnime(args.idAnime)
    }

    private fun initListener() {
        binding.btnRefresh.setOnClickListener {
            detailVM.loadInfoAnime(28977)
        }
    }

    private fun initObservers() {
        detailVM.anime.observe(requireActivity()) { anime ->
            binding.txtTitleEnglish.text = anime.name
            binding.txtSypnosis.text=anime.sypnosis
            binding.imgAnime.load(anime.big_image)

        }

        detailVM.error.observe(requireActivity()) { errorMesage ->
            Snackbar
                .make(
                    requireActivity(),
                    binding.btnRefresh,
                    errorMesage, Snackbar.LENGTH_LONG
                ).show()
        }
    }


    private fun loadDataRecyclerView() {
        var id = args.idAnime.toInt()

        lifecycleScope.launch(Dispatchers.Main) {

            val resp = withContext(Dispatchers.IO) {
                GetAnimeTopUserCase().getFullAnimeInfo(id)
            }
            resp.onSuccess {
                binding.textIdAnime.text = it.id.toString()
                binding.txtTitleEnglish.text = it.name.toString()
                binding.txtSypnosis.text = it.sypnosis.toString()

            }
            resp.onFailure { ex ->
                Snackbar.make(
                    requireActivity(),
                    binding.linear,
                    ex.message.toString(),
                    Snackbar.LENGTH_LONG
                ).show()
            }


        }
    }
}