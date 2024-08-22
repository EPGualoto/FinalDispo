package com.gualoto.pfinaldm.ui.fragments.main.news

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gualoto.pfinaldm.data.network.entities.news.Data
import com.gualoto.pfinaldm.data.network.entities.news.NewsAPI
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.data.network.endpoints.AnimeListResponse
import com.gualoto.pfinaldm.data.network.repository.NewsClient
import com.gualoto.pfinaldm.databinding.FragmentNewsBinding
import com.gualoto.pfinaldm.ui.adapters.news.NewsAdapter
import com.gualoto.pfinaldm.ui.viewmodels.news.OrderViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private var newsAdapter: NewsAdapter? = null
    private val orderViewModel: OrderViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)

        newsAdapter = NewsAdapter()
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.newsRecyclerView.adapter = newsAdapter

        // Actualizar el orden en la UI
        updateOrderText(orderViewModel.order.value ?: "recent-newest")

        // Configurar SwipeRefreshLayout
        binding.swipeRefreshLayout.setOnRefreshListener {
            fetchAnimeList(orderViewModel.order.value ?: "recent-newest")
        }

        // Observar cambios en el ViewModel
        orderViewModel.order.observe(viewLifecycleOwner) { order ->
            fetchAnimeList(order)
            updateOrderText(order)
        }

        // Configurar el botón de ordenar
        binding.btnSort.setOnClickListener {
            showSortDialog()
        }

        // Inicializar la carga de noticias con el valor inicial del orden
        fetchAnimeList(orderViewModel.order.value ?: "recent-newest")

        updateProfileIcon() // Actualiza el icono de perfil al cargar el fragmento
    }

    private fun updateOrderText(order: String) {
        val orderText = when (order) {
            "alphabetical-az" -> "Alfabético: A-Z"
            "alphabetical-za" -> "Alfabético: Z-A"
            "updated-newest" -> "Fecha actualizada: Más reciente"
            "updated-oldest" -> "Fecha actualizada: Más antiguo"
            else -> "Orden: Más reciente"
        }
        binding.orderTextView.text = orderText
    }

    private fun fetchAnimeList(order: String) {
        showLottieAnimation(true)
        NewsClient.instance.getAnimeList(page = 1).enqueue(object : Callback<AnimeListResponse> {
            override fun onResponse(call: Call<AnimeListResponse>, response: Response<AnimeListResponse>) {
                binding.swipeRefreshLayout.isRefreshing = false
                if (response.isSuccessful) {
                    val animeList = response.body()?.data ?: emptyList()
                    if (animeList.isEmpty()) {
                        showNoResultsDialog()
                        showLottieAnimation(false)
                    } else {
                        val allNews = mutableListOf<Data>()
                        var pendingRequests = animeList.size

                        animeList.forEach { anime ->
                            fetchNews(anime.mal_id) { news ->
                                allNews.addAll(news)
                                pendingRequests--
                                if (pendingRequests == 0) {
                                    if (allNews.isEmpty()) {
                                        showNoResultsDialog()
                                    } else {
                                        // Ordenar noticias según la selección
                                        val sortedNews = sortNews(allNews, order)
                                        newsAdapter?.submitList(sortedNews)
                                    }
                                    showLottieAnimation(false)
                                }
                            }
                        }
                    }
                } else {
                    Log.e("NewsFragment", "Error fetching anime list: ${response.code()}")
                    showLottieAnimation(false)
                    showNoResultsDialog()
                }
            }

            override fun onFailure(call: Call<AnimeListResponse>, t: Throwable) {
                binding.swipeRefreshLayout.isRefreshing = false
                Log.e("NewsFragment", "Error fetching anime list", t)
                showLottieAnimation(false)
                showNoResultsDialog()
            }
        })
    }

    private fun sortNews(newsList: List<Data>, order: String): List<Data> {
        val (sortType, sortOrder) = order.split("-")
        return when (sortType) {
            "alphabetical" -> {
                when (sortOrder) {
                    "az" -> newsList.sortedBy { it.title }
                    "za" -> newsList.sortedByDescending { it.title }
                    else -> newsList.sortedBy { it.title }
                }
            }
            "oldest" -> newsList.sortedBy { parseDate(it.date) }
            else -> newsList.sortedByDescending { parseDate(it.date) }
        }
    }

    private fun fetchNews(animeId: Int, callback: (List<Data>) -> Unit) {
        NewsClient.newsService.getNews(animeId).enqueue(object : Callback<NewsAPI> {
            override fun onResponse(call: Call<NewsAPI>, response: Response<NewsAPI>) {
                if (response.isSuccessful) {
                    val news = response.body()?.data ?: emptyList()
                    callback(news)
                } else {
                    Log.e("NewsFragment", "Error fetching news: ${response.code()}")
                    callback(emptyList())
                }
            }

            override fun onFailure(call: Call<NewsAPI>, t: Throwable) {
                Log.e("NewsFragment", "Error fetching news", t)
                callback(emptyList())
            }
        })
    }

    private fun parseDate(dateString: String): ZonedDateTime? {
        return try {
            ZonedDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME)
        } catch (e: Exception) {
            null
        }
    }

    private fun showLottieAnimation(show: Boolean) {
        binding.lottieAnimationView.visibility = if (show) View.VISIBLE else View.GONE
        if (show) {
            binding.lottieAnimationView.playAnimation()
        } else {
            binding.lottieAnimationView.pauseAnimation()
        }
    }

    private fun showNoResultsDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("No Results")
            .setMessage("No news items found.")
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showSortDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_sort_options, null)
        val radioGroupSortOptions = dialogView.findViewById<RadioGroup>(R.id.radioGroupSortOptions)
        val radioGroupSortOrder = dialogView.findViewById<RadioGroup>(R.id.radioGroupSortOrder)

        val radioAZ = dialogView.findViewById<RadioButton>(R.id.radioAZ)
        val radioZA = dialogView.findViewById<RadioButton>(R.id.radioZA)
        val radioNewest = dialogView.findViewById<RadioButton>(R.id.radioNewest)
        val radioOldest = dialogView.findViewById<RadioButton>(R.id.radioOldest)

        // Configurar la visibilidad inicial de los RadioButtons
        radioAZ.visibility = View.GONE
        radioZA.visibility = View.GONE
        radioNewest.visibility = View.GONE
        radioOldest.visibility = View.GONE

        // Cambiar opciones dinámicamente según la selección
        radioGroupSortOptions.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioUpdated -> {
                    // Mostrar opciones de fecha
                    radioAZ.visibility = View.GONE
                    radioZA.visibility = View.GONE
                    radioNewest.visibility = View.VISIBLE
                    radioOldest.visibility = View.VISIBLE

                    // Seleccionar por defecto "Más nuevo" para fecha
                    radioGroupSortOrder.check(R.id.radioNewest)
                }
                R.id.radioAlphabetical -> {
                    // Mostrar opciones alfabéticas
                    radioAZ.visibility = View.VISIBLE
                    radioZA.visibility = View.VISIBLE
                    radioNewest.visibility = View.GONE
                    radioOldest.visibility = View.GONE

                    // Seleccionar por defecto "A-Z" para alfabético
                    radioGroupSortOrder.check(R.id.radioAZ)
                }
            }
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Ordenar")
            .setView(dialogView)
            .setPositiveButton("Actualizar orden") { _, _ ->
                val selectedSortOption = when (radioGroupSortOptions.checkedRadioButtonId) {
                    R.id.radioUpdated -> "updated"
                    R.id.radioAlphabetical -> "alphabetical"
                    else -> "updated"
                }

                val selectedSortOrder = when (radioGroupSortOrder.checkedRadioButtonId) {
                    R.id.radioAZ -> "az"
                    R.id.radioZA -> "za"
                    R.id.radioNewest -> "newest"
                    R.id.radioOldest -> "oldest"
                    else -> "newest"
                }

                val combinedOrder = "$selectedSortOption-$selectedSortOrder"
                orderViewModel.setOrder(combinedOrder)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }


private fun updateProfileIcon() {
    val profileImageResId = sharedPreferences.getInt("profileImage", R.drawable.ic_profile)
    binding.profileIcon.setImageResource(profileImageResId)
}
}
