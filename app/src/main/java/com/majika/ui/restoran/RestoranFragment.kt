package com.majika.ui.restoran

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.majika.R
import com.majika.api.RetrofitClient
import com.majika.api.branch.BranchAdapter
import com.majika.api.branch.BranchData
import com.majika.api.branch.BranchResponse
import com.majika.databinding.FragmentRestoranBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestoranFragment : Fragment() {

    private var _binding: FragmentRestoranBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val list = ArrayList<BranchData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val restoranViewModel =
            ViewModelProvider(this).get(RestoranViewModel::class.java)

        _binding = FragmentRestoranBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val toolbar: MaterialToolbar = requireActivity().findViewById(R.id.topAppBar)
        toolbar.title = "Restoran"
        val temp: TextView = requireActivity().findViewById(R.id.temp)
        temp.visibility = TextView.GONE

        val rvBranch = binding.rvBranch
        rvBranch.setHasFixedSize(true)
        rvBranch.layoutManager = LinearLayoutManager(context)

        // Get API
        RetrofitClient.instance.getBranch().enqueue(object : Callback<BranchResponse> {
            override fun onResponse(
                call: Call<BranchResponse>,
                response: Response<BranchResponse>
            ) {
                val responseCode = response.code()
                list.clear()
                response.body()?.let { list.addAll(it.data) }
                list.sortBy { it.name }
                val adapter = BranchAdapter(list)
                rvBranch.adapter = adapter
            }

            override fun onFailure(call: Call<BranchResponse>, t: Throwable) {

            }

        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}