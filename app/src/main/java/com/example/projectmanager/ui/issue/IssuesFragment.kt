package com.example.projectmanager.ui.issue

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.projectmanager.R
import com.example.projectmanager.data.factories.IssuesViewModelFactory
import kotlinx.android.synthetic.main.fragment_issues.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class IssuesFragment : Fragment(), KodeinAware {

    companion object {
        fun newInstance() = IssuesFragment()

        lateinit var adapter: IssuesAdapter
    }

    override val kodein by kodein()
    private val factory: IssuesViewModelFactory by instance()

    private lateinit var viewModel: IssuesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_issues, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this, factory).get(IssuesViewModel::class.java)

        val adapter = ViewPageAdapter(activity?.supportFragmentManager!!)
        adapter.fragments.add(CreatedFragment())
        adapter.titles.add("Created")

        adapter.fragments.add(StartedFragment())
        adapter.titles.add("Started")

        adapter.fragments.add(FinishedFragment())
        adapter.titles.add("Finished")

        viewPagerIssues.adapter = adapter

        tabLayoutIssues.setupWithViewPager(viewPagerIssues)

        viewPagerIssues.addOnPageChangeListener(object : OnPageChangeListener {
            // This method will be invoked when a new page becomes selected.
            override fun onPageSelected(position: Int) {
                viewPagerIssues.currentItem = position
                Log.d("test1", "Nuuuu kööörs jåååååå ${position} ${tabLayoutIssues.selectedTabPosition} ${tabLayoutIssues.isSelected}")
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) { // Code goes here
            }

            override fun onPageScrollStateChanged(state: Int) { // Code goes here
            }
        })

        /*
        tabLayoutIssues.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.d("test1", "Nuuuu kööörs jåååååå ${tab.position} ${tabLayoutIssues.selectedTabPosition} ${tabLayoutIssues.isSelected}")
                viewPagerIssues.currentItem = tab.position
                viewPagerIssues.setCurrentItem(tab.position)
                //tabLayoutIssues.setSelectedTabIndicator(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
*/

        /*
        viewModel.projectId = ProjectActivity.currentProject?.id!!

        viewModel.initFun()


        recyclerView_issues.layoutManager = LinearLayoutManager(activity)
        adapter = IssuesAdapter(listOf())
        recyclerView_issues.adapter = adapter
        viewModel.getIssues().observe(viewLifecycleOwner, Observer {
            adapter.setList(it.data!!)
            swipeLayoutIssues.isRefreshing = false
        })

        swipeLayoutIssues.setOnRefreshListener {
            viewModel.loadIssues()
        }*/

        addIssueButton.setOnClickListener {view ->
            view.findNavController().navigate(R.id.action_nav_issues_to_nav_create_issue)
        }

        toChatButton.setOnClickListener {view ->
            view.findNavController().navigate(R.id.action_nav_issues_to_nav_chat)
        }
    }
}
