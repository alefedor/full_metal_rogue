package ru.hse.spb.sd.full_metal_rogue.view

import com.google.gson.GsonBuilder
import ru.hse.spb.sd.full_metal_rogue.logic.inventory.Item
import ru.hse.spb.sd.full_metal_rogue.logic.map.MapJsonAdapter

object ViewJsonAdapter {
    val viewGson = GsonBuilder()
        .registerTypeHierarchyAdapter(View::class.java, MapJsonAdapter.HierarchyAdapter<View>(MapJsonAdapter.baseGson))
        .create()
    val menuGson = GsonBuilder()
        .registerTypeHierarchyAdapter(Menu::class.java, MapJsonAdapter.HierarchyAdapter<Menu<Item>>())
        .create()


    fun serialize(view: View): String = viewGson.toJson(view)

    fun deserialize(json: String): View = viewGson.fromJson(json, View::class.java)
}