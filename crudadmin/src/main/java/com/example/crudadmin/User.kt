package com.example.it21296550

class User {


        var name: String? = null
        var phone: String? = null
        var location: String? = null

        constructor() // Empty constructor required for Firebase

        constructor(name: String?, phone: String?, location: String?) {
            this.name = name
            this.phone = phone
            this.location = location

    }
}