package com.example.mobileapplication.dto.script

class ScriptDTO {
    // Getters and Setters
    var id: Long? = null
        private set

    //      I don't want ID to be changed
    //    public void setId(Long id) {
    //        this.id = id;
    //    }
    var name: String? = null
    var location: String? = null
    var protectionLevel: String? = null
    var language: String? = null
    var inputFiles: String? = null
    var outputFiles: String? = null
    var userId: Long? = null


    fun setScriptId(scriptId: Long?) {
        this.id = scriptId
    }
}
