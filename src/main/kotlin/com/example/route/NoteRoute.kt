package com.example.route

import com.example.model.Note
import com.example.model.noteStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.noteRouting(){
    route("/note"){
        get {
            if (noteStorage.isNotEmpty()){
                call.respond(noteStorage)
            }else {
                call.respondText("no notes found in database " , status = HttpStatusCode.OK )
            }
        }
        get("{id?}"){
            val id = call.parameters["id"] ?: return@get call.respondText( "missing id ", status = HttpStatusCode.BadRequest)
            val note = noteStorage.find { it.id == id } ?: return@get call.respondText("no notes with id $id" , status = HttpStatusCode.NotFound)
            call.respond(note)
        }
        post {
            val note = call.receive<Note>()
            noteStorage.add(note)
            call.respondText("note stored successfully" , status = HttpStatusCode.Created)
        }
        delete("{id}?"){
            val id = call.parameters["id"]  ?: return@delete call.respond(HttpStatusCode.BadRequest)
           if (noteStorage.removeIf{it.id == id}){
               call.respondText("Customer removed correctly", status = HttpStatusCode.Accepted)
           } else{
               call.respondText("Not found", status = HttpStatusCode.NotFound)
           }
        }
    }
}