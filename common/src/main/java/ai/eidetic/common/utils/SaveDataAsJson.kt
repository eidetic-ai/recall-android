package ai.eidetic.common.utils

import ai.eidetic.domain.generatelessons.model.Deck
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

fun saveDataAsJson(file: File, data: Deck) {
    // Convert the data object to a JSON string using GSON
    val jsonString = Gson().toJson(data)

    // Create the folder if it doesn't exist
    val folder = file
    if (!folder.exists()) {
        folder.mkdir()
    }

    // Create a new file in the folder and write the JSON string to it
    val finalFile = File(folder.path + "/${data.id}.json")
    val fos = FileOutputStream(finalFile)
    val osw = OutputStreamWriter(fos)
    osw.write(jsonString)
    osw.close()
}