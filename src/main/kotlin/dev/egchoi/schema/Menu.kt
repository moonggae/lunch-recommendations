package dev.egchoi.schema

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

// 데이터베이스 테이블 정의
object MenuCategories : IntIdTable() {
    val name = varchar("name", 50)
}

object Menus : IntIdTable() {
    val name = varchar("name", 100)
    val category = reference("category_id", MenuCategories)
    val description = varchar("description", 500).nullable()
    val spicyLevel = integer("spicy_level").default(0)
    val isAvailableForLunch = bool("is_available_for_lunch").default(true)
}

// 엔티티 정의
class MenuCategory(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<MenuCategory>(MenuCategories)
    var name by MenuCategories.name
    val menus by Menu referrersOn Menus.category
}

class Menu(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Menu>(Menus)
    var name by Menus.name
    var category by MenuCategory referencedOn Menus.category
    var description by Menus.description
    var spicyLevel by Menus.spicyLevel
    var isAvailableForLunch by Menus.isAvailableForLunch
}