package com.candybytes.taco.ui.fragments

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {
    protected open var binding: B? = null
}