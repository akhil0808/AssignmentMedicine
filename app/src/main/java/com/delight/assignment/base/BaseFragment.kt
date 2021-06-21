package com.delight.assignment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.delight.assignment.BaseActivity


abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> :
    androidx.fragment.app.Fragment() {

    private lateinit var mViewDataBinding: T
    private lateinit var mViewModel: V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mViewModel = getViewModel()
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()
        setFragmentTitle()
        return mViewDataBinding.root
    }


    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun getBindingVariable(): Int


    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): V

    /**
     * Override for set title in fragment
     */
    open fun setFragmentTitle() {
        //Empty override method
    }


    /**
     * Method for get View data binding
     */
    fun getViewDataBinding(): T {
        return mViewDataBinding
    }


    fun showToast(mesg:String){
        (activity as BaseActivity).showToast(mesg)
    }
    fun showLoading() {
        (activity as BaseActivity).showLoading()
    }

    fun hideLoading() {
        (activity as BaseActivity).hideLoading()
    }
}
