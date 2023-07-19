package com.example.garbagecollector

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.garbagecollector.viewModel.GCViewModel

class RecViewFragment : Fragment() {

    private lateinit var recView: RecyclerView
    private lateinit var gcViewModel: GCViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_rec_view, container, false)

        recView = view.findViewById(R.id.recyclerViewID)

        recView.layoutManager = LinearLayoutManager(context)
        gcViewModel = ViewModelProvider(this).get(GCViewModel::class.java)


        val rvAdapter = RVAdapter(gcViewModel,this)
        recView.adapter = rvAdapter

        gcViewModel.allUsers.observe(viewLifecycleOwner, Observer { list->
            list?.let {
                rvAdapter.updateList(it)
            }
        })



        return view
    }
}
