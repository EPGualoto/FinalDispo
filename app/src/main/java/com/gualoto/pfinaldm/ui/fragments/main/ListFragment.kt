package com.gualoto.pfinaldm.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.data.network.entities.anime.entities.FullInfoAnimeLG
import com.gualoto.pfinaldm.databinding.FragmentListBinding
import com.gualoto.pfinaldm.logic.usercase.anime.JikanGetTopAnimesUserCase
import com.gualoto.pfinaldm.ui.adapters.UsersAdapterDiffUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private var userList: MutableList<FullInfoAnimeLG> = ArrayList()
    private var userDiffAdapter =
        UsersAdapterDiffUtil({ deleteUserDiffUtil(it) }, { selectAnime(it) })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            FragmentListBinding.bind(
                inflater.inflate(
                    R.layout.fragment_list,
                    container,
                    false
                )
            )
        return binding.root
    }


    //Para llamar a mi recycle view lo que debo hacer es sobreescribir el estado OnViewCreate
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
    }

    fun initRecycleView() {
        binding.rvUsers.adapter = userDiffAdapter
        binding.rvUsers.layoutManager =
            LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
        loadDataRecycleView()
    }

    //Carga de Informacion Recycle View
    private fun loadDataRecycleView() {
        //TODO("Llamar a la API de Jikan")
        lifecycleScope.launch(Dispatchers.Main) {
            binding.progresBar.visibility = View.VISIBLE
            //Hago el llamado intermedio entre dos estados, el llamado a mi logica a la base de datos
            //se hace dentro de una IO
            val respuesta = withContext(Dispatchers.IO) {
                JikanGetTopAnimesUserCase().invoke()
            }
            respuesta.onSuccess { listAnime ->
                userList.addAll(listAnime)
                //Cuando inicio el Recycle view es cuando debo poner la insercion
                insertUsersDiffUtil(userList)
            }
            respuesta.onFailure { exeption ->
                Snackbar.make(
                    //Con esto accedo desde un fragment  al contexto de mi Activity
                    requireActivity(),
                    binding.rvUsers,
                    exeption.message.toString(),
                    Snackbar.LENGTH_LONG
                ).show()

            }
            binding.progresBar.visibility = View.GONE
        }

    }

    private fun insertUsersDiffUtil(animes: List<FullInfoAnimeLG>) {
        userList.addAll(animes)
        userDiffAdapter.submitList(userList.toList())
    }

    private fun deleteUserDiffUtil(position: Int) {
        userList.removeAt(position)
        userDiffAdapter.submitList(userList.toList())

    }

    //Me muestra el usuario seleccionado
    private fun selectAnime(animes: FullInfoAnimeLG) {
        //Esta funcion me permite navegar
        findNavController().navigate(
            ListFragmentDirections
                .actionListFragmentToDetailFragment(idAnime =animes.id )
        )

    }
}