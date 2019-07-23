package com.dingdonginc.zhangfang.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.layoutservice.WalletViewPagerAdapter
import com.dingdonginc.zhangfang.services.MainActivityDialogService
import com.dingdonginc.zhangfang.viewmodels.WalletViewModel
import com.google.android.material.tabs.TabLayout
import org.kodein.di.generic.instance

class WalletFragment : Fragment() {


    companion object {
        fun newInstance() = WalletFragment()
    }

    private lateinit var listViewModel: WalletViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.wallet_fragment, container, false)
//        binding = DataBindingUtil.inflate(inflater, R.layout.wallet_fragment, container, false)
//        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listViewModel = ViewModelProviders.of(this).get(WalletViewModel::class.java)

        val pager = view!!.findViewById<ViewPager>(R.id.wallet_pager)
        val lst = ArrayList<WalletViewModel>()
        lst.add(listViewModel)
        lst.add(listViewModel)
        val adapter = WalletViewPagerAdapter(lst, layoutInflater)
        pager.adapter = adapter
        pager.addOnPageChangeListener(OnPageChange())
//        pager.arrowScroll(0)
        val tab = view!!.findViewById<TabLayout>(R.id.wallet_tab)
        tab.setupWithViewPager(pager)
        pager.currentItem = 0
        val mainActivityDialogService: MainActivityDialogService by App.getKodein().instance()
        mainActivityDialogService.setFm(fragmentManager!!)
    }

    private inner class OnPageChange: ViewPager.OnPageChangeListener {
        /**
         * Called when the scroll state changes. Useful for discovering when the user
         * begins dragging, when the pager is automatically settling to the current page,
         * or when it is fully stopped/idle.
         *
         * @param state The new scroll state.
         * @see ViewPager.SCROLL_STATE_IDLE
         *
         * @see ViewPager.SCROLL_STATE_DRAGGING
         *
         * @see ViewPager.SCROLL_STATE_SETTLING
         */
        override fun onPageScrollStateChanged(state: Int) {
//            Log.i("onPageScrollStateChanged", state.toString())
        }

        /**
         * This method will be invoked when the current page is scrolled, either as part
         * of a programmatically initiated smooth scroll or a user initiated touch scroll.
         *
         * @param position Position index of the first page currently being displayed.
         * Page position+1 will be visible if positionOffset is nonzero.
         * @param positionOffset Value from [0, 1) indicating the offset from the page at position.
         * @param positionOffsetPixels Value in pixels indicating the offset from position.
         */
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//            Log.i("onPageScrolled", "$position $positionOffset $positionOffsetPixels")
        }

        /**
         * This method will be invoked when a new page becomes selected. Animation is not
         * necessarily complete.
         *
         * @param position Position index of the new selected page.
         */
        override fun onPageSelected(position: Int) {
            // Log.i("onPageSelected", position.toString())
            listViewModel.switch(position)
        }

        fun showDialog(dialog: DialogFragment, tag: String) {
            dialog.show(fragmentManager, tag)
        }
    }
}
