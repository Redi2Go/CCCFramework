
package at.htlperg.simplereader

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.Buffer
import java.util.stream.Collectors
import kotlin.reflect.typeOf

open class SimpleReader(content:String) {
    public var lines = content.split("\n").map { it.trim() }


    public fun <T>getSplitLine(index:Int, clazz:Class<T>, delim:String = " "):List<T> = lines[index].split(delim).map {
        when(clazz){
            Class.forName("java.lang.Integer") -> it.toInt()
            Class.forName("java.lang.Character") -> it.toCharArray()[0]
            else -> it
        } as T
    }

    public fun getRange(from:Int, to:Int):List<String>{
        return lines.subList(from,to)
    }
    public fun get(index:Int) = lines[index]


    public fun <T>getRangeSplit(from:Int, to:Int, clazz:Class<T>, delim:String=" "):List<List<T>>{
        return getRange(from,to).map { it.split(delim).map {
            when(clazz){
                Class.forName("java.lang.Integer") -> it.toInt()
                Class.forName("java.lang.Character") -> it.toCharArray()[0]
                else -> it
            } as T
        }}
    }

    public fun <T>getSplitLineReverse(index:Int, clazz:Class<T>, delim:String = " "):List<T> = getSplitLine(index, clazz, delim).asReversed()


    public fun <T>getExactPosition(x:Int, y:Int, clazz:Class<T>, delim:String = " "):T?{
        if(lines.size <= x) return null
        if (getSplitLine(x, clazz, delim).size <= y) return null

        return getSplitLine(x, clazz, delim)[y]
    }





}