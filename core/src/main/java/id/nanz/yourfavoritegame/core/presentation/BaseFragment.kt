package id.nanz.yourfavoritegame.core.presentation

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T: ViewBinding>: Fragment(), DefaultLifecycleObserver {

    private var binding: T? = null
    private var baseLifecycle: Lifecycle? = null
    private lateinit var fragmentName: String

    fun initBinding(binding: T, fragment: Fragment, onBound: (T.() -> Unit)?): View {
        this.binding = binding
        baseLifecycle = fragment.viewLifecycleOwner.lifecycle
        baseLifecycle?.addObserver(this)
        fragmentName = fragment::class.simpleName ?: "N/A"
        onBound?.invoke(binding)
        return binding.root
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super<DefaultLifecycleObserver>.onDestroy(owner)
        baseLifecycle?.removeObserver(this) // not mandatory, but preferred
        baseLifecycle = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onFragmentDestroy()
        binding = null
    }

    abstract fun onFragmentDestroy()

}