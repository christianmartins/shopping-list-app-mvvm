package br.com.shoppinglistmvvmapp.utils

import br.com.shoppinglistmvvmapp.data.model.User

object LoggedUser {

    var user: User? = null
    var isLogged: Boolean = false
    var token = ""

    fun getUserId(): Int{
        return user?.id?: 0
    }

    fun getAuthorName(): String{
        return user?.let {
            "${it.firstName} ${it.lastName}"
        }?: "anonymous"
    }

    fun isLogged(token: String, user: User){
        this.token = token
        this.user = user
        this.isLogged = true
    }

    fun clear(){
        token = ""
        user = null
        isLogged = false
    }
}