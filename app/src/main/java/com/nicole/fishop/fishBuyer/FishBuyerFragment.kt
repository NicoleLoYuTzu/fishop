package com.nicole.fishop.fishBuyer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import com.nicole.fishop.NavFragmentDirections
import com.nicole.fishop.databinding.FragmentFishBuyerBinding
import com.nicole.fishop.ext.getVmFactory
import com.nicole.fishop.util.Logger


class FishBuyerFragment : Fragment() {

    private lateinit var list: MutableList<String>
    private val viewModel by viewModels<FishBuyerViewModel> { getVmFactory(
    )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFishBuyerBinding.inflate(inflater)
//        viewModel.getFishTodayFilterResult("")
        viewModel.fishToday.observe(viewLifecycleOwner, Observer {
            (binding.recyclerView.adapter as FishBuyerAdapter).submitList(it)
        })
        binding.recyclerView.adapter = FishBuyerAdapter(FishBuyerAdapter.OnClickListener {
//            viewModel.navigateToDetail(it)
//            findNavController(binding.root).navigate(NavFragmentDirections.actionToFishBuyerGoogleMap())
            viewModel.navigateToGoogleMap(it)
        })

        viewModel.navigateToGoogleMap.observe( viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController(binding.root).navigate(NavFragmentDirections.actionToFishBuyerGoogleMap(it))
                    viewModel.onGoogleMapNavigated()
                }
            }
        )
        /**
         * Set up search view with list view to show the user enter text
         */
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String): Boolean {
                viewModel.getFishTodayFilterResult(p0)
                Logger.d("searchView p0 $p0")
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Logger.d("onQueryTextChange p0 $p0")
                if (p0 == null || p0 == "") {
                    viewModel.getFishTodayAllResult()
                }
                return false
            }

        })

//        list = mutableListOf(
//            "黃魚"
//        )
//        list.add(0, "請選擇項目")
//        val adapter1: ArrayAdapter<String> = object : ArrayAdapter<String>(
//            requireContext(),
//            android.R.layout.simple_spinner_dropdown_item,
//            list
//        ) {
//            override fun getDropDownView(
//                position: Int,
//                convertView: View?,
//                parent: ViewGroup
//            ): View {
//                val view: TextView = super.getDropDownView(
//                    position,
//                    convertView,
//                    parent
//                ) as TextView
//                // set item text bold
//                view.setTypeface(view.typeface, Typeface.BOLD)
//
//                // set selected item style
//                if (position == binding.spinner.selectedItemPosition && position != 0) {
//                    view.background = ColorDrawable(Color.parseColor("#F7E7CE"))
//                    view.setTextColor(Color.parseColor("#333399"))
//                }
//
//                // make hint item color gray
//                if (position == 0) {
//                    view.setTextColor(Color.LTGRAY)
//                }
//
//                return view
//            }
//
//            override fun isEnabled(position: Int): Boolean {
//                // disable first item
//                // first item is display as hint
//                return position != 0
//            }
//        }
//        binding.spinner.adapter = adapter1


        return binding.root
    }


}