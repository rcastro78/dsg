package sv.com.seguridadcontrol.app.erp.tecnicos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_principal.*
import sv.com.seguridadcontrol.app.R
import sv.com.seguridadcontrol.app.adapter.TabAdapter
import sv.com.seguridadcontrol.app.fragments.TicketsAbiertosFragment
import sv.com.seguridadcontrol.app.fragments.TicketsCompletedFragment
import sv.com.seguridadcontrol.app.fragments.TicketsHoyFragment

class ERPPrincipalActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        val mSectionsPagerAdapter = SectionsPagerAdapter(
            supportFragmentManager
        )
        container.adapter = mSectionsPagerAdapter
        val adapter = TabAdapter(supportFragmentManager)
        adapter.addFragment(TicketsHoyFragment(), "Hoy")
        adapter.addFragment(TicketsAbiertosFragment(), "Abiertos")
        adapter.addFragment(TicketsCompletedFragment(), "Cerrados")
        adapter.addFragment(TicketsAbiertosFragment(), "Cancelados")
        container.adapter = adapter
        tabs.setupWithViewPager(container)


    }



}


class PlaceholderFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_tickets, container, false)
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            val fragment = PlaceholderFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }
}


class SectionsPagerAdapter(fm: FragmentManager?) :
    FragmentPagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {
        return PlaceholderFragment.newInstance(position + 1)
    }

    override fun getCount(): Int {
        return 2
    }
}
