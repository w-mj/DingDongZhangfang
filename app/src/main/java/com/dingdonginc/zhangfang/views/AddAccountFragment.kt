package com.dingdonginc.zhangfang.views

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.dingdonginc.zhangfang.BR

import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.layoutservice.ViewPagerAdapter
import com.dingdonginc.zhangfang.models.Tag
import com.dingdonginc.zhangfang.viewmodels.AddAccountViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog

class AddAccountFragment : Fragment() {
    init {
        Log.i("AddAccountFragment", "init")
    }

    companion object {
        fun newInstance() = AddAccountFragment()
    }

    private lateinit var viewModel: AddAccountViewModel
    private lateinit var binding: com.dingdonginc.zhangfang.databinding.AddAccountFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("AddAccountFragment", "onCreateView")

        binding = DataBindingUtil.inflate(inflater, R.layout.add_account_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(AddAccountViewModel::class.java)
        binding.vm = viewModel
        return binding.root
    }

    private inner class OnTimePickerCallBack: TimePickerDialog.OnTimeSetListener {
        override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
            Log.i("Time set", "$hourOfDay:$minute:$second")
            viewModel.datetime.set(
                String.format("%s\n%02d:%02d", viewModel.datetime.get()!!, hourOfDay, minute))
        }

    }

    private inner class OnDatePickerCallBack: DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
            Log.i("Date set", "$year-$monthOfYear-$dayOfMonth")
            val timePicker = TimePickerDialog.newInstance(OnTimePickerCallBack(), true)
            timePicker.setTimeInterval(1, 5)
            timePicker.enableSeconds(false)
            timePicker.show(fragmentManager, "Time Picker")
            viewModel.datetime.set(String.format("%4d-%02d-%02d", year, monthOfYear, dayOfMonth))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("AddAccountFragment", "onActivityCreated")
        val datetimeButton = view!!.findViewById<Button>(R.id.digital_datetime)
        datetimeButton.setOnClickListener {
            val datePicker = DatePickerDialog.newInstance(OnDatePickerCallBack())
            datePicker.show(fragmentManager, "Date Picker")
        }
    }

    override fun onStart() {
        super.onStart()
        var viewPager : ViewPager = getView()!!.findViewById(R.id.typeview)
        var adapter = ViewPagerAdapter<Tag>(viewModel.typeList, viewModel, BR.tag , R.layout.typelist_item, getLayoutInflater())
        viewPager?.setAdapter(adapter)
    }
}
