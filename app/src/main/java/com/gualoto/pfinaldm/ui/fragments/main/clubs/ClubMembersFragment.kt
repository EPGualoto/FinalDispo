package com.gualoto.pfinaldm.ui.fragments.main.clubs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.ui.adapters.clubs.MemberAdapter
import com.gualoto.pfinaldm.ui.viewmodels.clubs.ClubMembersViewModel

class ClubMembersFragment : Fragment() {
    private lateinit var viewModel: ClubMembersViewModel
    private lateinit var memberAdapter: MemberAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_club_members, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ClubMembersViewModel::class.java)
        memberAdapter = MemberAdapter(listOf())

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewMembers)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = memberAdapter

        val clubId = arguments?.getInt("club_id") // Asegúrate de que el clubId se recibe correctamente
        clubId?.let {
            viewModel.getClubMembers(it)
        }

        viewModel.members.observe(viewLifecycleOwner, { members ->
            memberAdapter.updateMembers(members)
        })
    }
}
