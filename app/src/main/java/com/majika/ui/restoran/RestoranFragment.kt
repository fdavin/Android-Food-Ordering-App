package com.majika.ui.restoran

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Callback
import com.majika.api.RetrofitClient
import com.majika.api.branch.BranchAdapter
import com.majika.api.branch.BranchData
import com.majika.api.branch.BranchResponse
import com.majika.databinding.FragmentRestoranBinding
import retrofit2.Call
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

        val textView: TextView = binding.textRestoran
        restoranViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        binding.rvBranch.setHasFixedSize(true)
        binding.rvBranch.layoutManager = LinearLayoutManager(context)

        RetrofitClient.instance.getBranch().enqueue(object: Callback<BranchResponse>{
            override fun onResponse(
                call: Call<BranchResponse>,
                response: Response<BranchResponse>
            ) {
                val responseCode = response.code()
                response.body()?.let {list.addAll(it.data)}
                val adapter = BranchAdapter(list)
                binding.rvBranch.adapter = adapter
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