package com.example.projectmanager.ui.issue

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.projectmanager.R
import com.example.projectmanager.data.factories.IssuesViewModelFactory
import com.example.projectmanager.ui.chat.ChatFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_project.*
import kotlinx.android.synthetic.main.activity_start.*
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ViewPageAdapter(childFragmentManager)
        adapter.fragments.add(CreatedFragment())
        adapter.titles.add("Created")

        adapter.fragments.add(StartedFragment())
        adapter.titles.add("Started")

        adapter.fragments.add(FinishedFragment())
        adapter.titles.add("Finished")

        viewPager.adapter = adapter

        tabLayoutIssues.setupWithViewPager(viewPager)

        addIssueButton.setOnClickListener {viewButton ->
            //view.findNavController().navigate(R.id.action_nav_issues_to_nav_create_issue)
            //view.findNavController().navigate(R.id.action_nav_issues_to_nav_create_issue)
            (viewButton.context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment_project, CreateIssueFragment()
            ).commit()
        }

        toChatButton.setOnClickListener {viewButton ->
            //view.findNavController().navigate(R.id.action_nav_issues_to_nav_chat)
            (viewButton.context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment_project, ChatFragment()
            ).commit()
        }

    }
}
