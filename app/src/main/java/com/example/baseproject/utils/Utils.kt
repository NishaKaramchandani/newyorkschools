package com.example.baseproject.utils

import androidx.navigation.NavController
import androidx.navigation.NavDirections

/**
 * Utils class for common functions/extension functions
 */
fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run {
        navigate(direction)
    }
}