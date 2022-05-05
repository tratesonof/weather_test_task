package com.example.weather_task.navigation

import android.os.Handler
import android.os.Looper
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.Back
import com.github.terrakok.cicerone.BackTo
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen

private data class Add(val screen: Screen) : Command

interface NoAnimationFragment

/*
    Этот навигатор используется при add fragment и для использования DialogFragment/BottomSheetDialogFragment
 */
open class SupportAppNavigator(
    activity: FragmentActivity,
    fragmentManager: FragmentManager,
    @IdRes containerId: Int = -1
) : AppNavigator(activity, containerId, fragmentManager) {

    private val handler = Handler(Looper.getMainLooper())

    override fun applyCommands(commands: Array<out Command>) {
        try {
            super.applyCommands(commands)
        } catch (exception: IllegalStateException) {
            /**
             * Исправляет ошибку "FragmentManager is already executing transactions"
             * https://github.com/terrakok/Cicerone/issues/104
             */
            handler.post { super.applyCommands(commands) }
        }
    }

    override fun applyCommand(command: Command) {
        when (command) {
            is Add -> add(command)
            is Replace -> replace(command)
            else -> super.applyCommand(command)
        }
    }

    private fun setupFragmentTransaction(
        command: Command,
        nextFragment: Fragment?,
        fragmentTransaction: FragmentTransaction
    ) {
        when (command) {
            is Back,
            is BackTo -> fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)

            is Forward,
            is Replace,
            is Add -> {
                if (nextFragment is NoAnimationFragment) {
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE)
                } else {
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                }
            }
        }
    }

    override fun replace(command: Replace) {
        if (command.screen is FragmentScreen) {
            val screen = command.screen as FragmentScreen
            val fragment = screen.createFragment(fragmentFactory)

            if (fragment is DialogFragment) {
                forwardDialogFragment(fragment, screen)
            } else {
                super.replace(command)
            }
        } else {
            super.replace(command)
        }
    }

    private fun add(command: Add) {
        if (command.screen is FragmentScreen) {
            val screen = command.screen
            val fragment = screen.createFragment(fragmentFactory)

            if (fragment is DialogFragment) {
                forwardDialogFragment(fragment, screen)
            } else {
                forwardFragmentBase(fragment, command, screen)
            }
        }
    }

    private fun forwardDialogFragment(
        fragment: DialogFragment,
        screen: Screen
    ) {
        if (fragmentManager.findFragmentByTag(screen.screenKey) == null) {
            fragment.show(fragmentManager, screen.screenKey)
        }
    }

    private fun forwardFragmentBase(
        fragment: Fragment?,
        command: Add,
        screen: Screen
    ) {

        val fragmentTransaction = fragmentManager.beginTransaction()

        setupFragmentTransaction(
            command,
            fragment,
            fragmentTransaction
        )

        fragment?.let {
            fragmentTransaction
                .add(containerId, it)
                .addToBackStack(screen.screenKey)
                .commit()
            localStackCopy.add(screen.screenKey)
        }
    }
}

open class AppRouter(qualifierName: String) : Router() {

    val name = qualifierName

    fun addTo(screen: Screen) {
        executeCommands(Add(screen))
    }

    fun backToRoot() {
        executeCommands(BackTo(null))
    }
}
