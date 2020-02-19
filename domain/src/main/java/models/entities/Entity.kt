package models.entities

import services.ValidationService
import java.util.UUID


abstract class Entity{

    var id: UUID = UUID.randomUUID()

}