package day23

class ItemIterator<T>(start: Item<T>) : Iterator<Item<T>>{
    var curr : Item<T> = start
    var started = false

    override fun hasNext(): Boolean {
        return if (started) curr.next != null else true
    }

    override fun next(): Item<T> {
        return if (!started) {
            started = true
            curr
        } else {
            curr = curr.next!!
            curr
        }
    }

}

class CircularList<T>(private var head: Item<T>, var size: Int) : Iterable<Item<T>>{

    companion object {
        fun <T> from(data: List<T>) : CircularList<T> {
            val items = data.map { Item(it, null) }
            val head = items[0]
            items.windowed(2).forEach { (a, b) ->
                a.next = b
            }

            items.last().next = head
            return CircularList(head, data.size)
        }

        fun from(data: String) : CircularList<Int> = from(data.map { Character.getNumericValue(it) }.toList())
    }

    override fun iterator(): Iterator<Item<T>> {
        return ItemIterator(head)
    }

    fun cutNFrom(n: Int, item: Item<T>) : CircularList<T> {
        val newNext = nthFrom(item, n+1)
        nthFrom(item, n).next = null;
        val circularList = CircularList(item.next!!, 3)
        item.next = newNext
        this.size -= n
        if (head in circularList) {
            head = newNext
        }
        return circularList
    }

    fun insertAt(item: Item<T>, newList : CircularList<T>) {
        val next = item.next
        size += newList.size
        newList.last().next = next
        item.next = newList.first()

    }

    fun nthFrom(item: Item<T>, n : Int) : Item<T> {
        return (1..n).fold(item) { acc, i ->
            acc.next!!
        }
    }

    fun contains(t: T) : Boolean {
        var found = false

        var i = 0

        val iterator = this.iterator()
        while (iterator.hasNext() && i < size) {

            if (iterator.next().data == t) {
                found = true
                break;
            }
            i ++
        }
        return found
    }

    override fun toString(): String {
        var i = 0
        var curr : Item<T>? = head
        val sb = StringBuffer()
        while(curr != null && i < size) {
            sb.append("$curr")
            curr = curr.next
            if (curr != null && i < size -1) {
                sb.append(", ")
            }
            i++
        }
        return "CircularList($sb)"
    }


}

class Item<T>(val data: T, var next: Item<T>?) {

    override fun toString(): String {
        return "Item(${data})"
    }
}