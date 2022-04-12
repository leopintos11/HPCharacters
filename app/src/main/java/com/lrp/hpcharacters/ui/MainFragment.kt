package com.lrp.hpcharacters.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.lrp.hpcharacters.R
import com.lrp.hpcharacters.databinding.FragmentMainBinding
import com.lrp.hpcharacters.model.HpCharacter


class MainFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding : FragmentMainBinding
    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var charactersAdapter : CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMainBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        context?.let { itContext ->
            charactersAdapter = CharactersAdapter(itContext) { character: HpCharacter ->
                findNavController().navigate(
                    R.id.navigate_to_character_details_fragment,
                    CharacterDetailsFragment.Contract.createBundle(character)
                )
            }
            binding.charactersRecyclerView.layoutManager = LinearLayoutManager(itContext, LinearLayoutManager.VERTICAL, false)
            binding.charactersRecyclerView.adapter = charactersAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = MainFragmentViewModelFactory(context!!).create(MainFragmentViewModel::class.java)
        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }
        binding.navigationView.setNavigationItemSelectedListener(this)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.change_theme_option -> Toast.makeText(context, "change theme", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(context, "nothing", Toast.LENGTH_SHORT).show()
            }
            true
        }
        viewModel.results.observe(this, {
            charactersAdapter.updateData(it)
        })
        viewModel.getMarsRealEstateProperties()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.about_item) {
            findNavController().navigate(R.id.about_fragment)
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
        }
        return true;
    }
}