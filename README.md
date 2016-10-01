# Strukt
_Thread-safe mux referenced "structures" with an easy Kotlin syntax_

## Declaring a Strukt

The declaration syntax is very similar to regular classes, except:
* You need to declare as `object`, rather than ` class`
* You need to use the `by!` operator instead of `=`
* All types should be primitives or immutable classes (like `String`)
* They must implement `Strukt`

```kotlin
object Point : Strukt {
    var x by! 0 // 0 is the default value
    var y by! 0
}
```

You can also extend `DataStrukt`, which gives you `data class`-like features:

```kotlin
object Point : DataStrukt() {
    var x by! 0
    var y by! 0
}
```

## Strukt pointers (references)

Strukt pointers are keys used to access the multiplexed values.

#### Getting a pointer (allocation)

The syntax is a bit difference from a regular class constructor.
Instead, you use the `invoke` operator to set the values to anything you would like.

```kotlin
Point {
    x = 2
    y = 3
}   
```

You can also do it in a single line by using semicolons:

```kotlin
Point { x = 2; y = 3 }
```

Any vars that you don't set will be the default value specified by the declaration.

```kotlin
Point {} // x and y will be 0
```

The allocation operator returns a pointer ("reference"), so you can hold onto it with a value:
```kotlin
val a = Point { x = 2; y = 3 }
```

Also note that Strukt allocation conveniently sets the scope pointer (more on this below).

#### Using ("dereferencing") pointers

The `get` or "deference" operator sets the scope pointer, which is used by members to provide
the correct values. You can think of the scope pointer similar to a stack pointer, as in it
is the next key to be used for value retrieval of members. The scope pointer and all member
values are local to the current thread (and also fiber from [Quasar](https://github.com/puniverse/quasar)).

The deference operator returns the Strukt, which lets you access members with regular syntax:

```kotlin
Point[a].x // 2
Point[a].y // 3
```

Since the deference operator sets the scope pointer you can omit it when accessing `y`,
and use the Strukt (`Point`) directly:

```kotlin
Point[a].x // 2
Point.y // 3
```

Remembering that Strukt allocation sets the scope pointer, and that `DataStrukt`
overrides `toString` for you:

```kotlin
Point { x = 2; y = 3 }
println(Point) // prints "Point(x=2, y=3)"
```

## Bringing it all together (an example)

With just the allocation and deference operator you can juggle multiple references:

```kotlin
val a = Point {}
println(Point) // prints "Point(x=0, y=0)"

val b = Point { x = 1; y = 2 }
println(Point) // prints "Point(x=1, y=2)"

val c = Point { y = 5 }
println(Point) // prints "Point(x=0, y=5)"

println(Point[b]) // prints "Point(x=1, y=2)"
Point.x = 7
println(Point.x) // prints 7

Point[a].x = 20
println(Point) // prints "Point(x=20, y=0)"

println(Point[c]) // prints "Point(x=0, y=5)"

Point[a].y += Point[c].y
println(Point[a]) // prints "Point(x=20, y=5)"
```