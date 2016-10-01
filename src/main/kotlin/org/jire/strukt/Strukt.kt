/*
 * Copyright 2016 Thomas Nappo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jire.strukt

import org.jire.strukt.member.PrimitiveMapWorkaround
import org.jire.strukt.member.PrimitiveMapWorkaround.put
import org.jire.strukt.internal.StruktData
import org.jire.strukt.internal.strukt
import org.jire.strukt.member.*

interface Strukt {

	operator fun Byte.not() = ByteMember(this)
	operator fun Short.not() = ShortMember(this)
	operator fun Int.not() = IntMember(this)
	operator fun Long.not() = LongMember(this)
	operator fun Float.not() = FloatMember(this)
	operator fun Double.not() = DoubleMember(this)
	operator fun Char.not() = CharMember(this)
	operator fun <T : Any> T.not() = ObjectMember(this)

	operator fun unaryMinus() {
		for (i in 0..strukt.members.size - 1) {
			val member = strukt.members[i]
			member.default(strukt.pointer)
		}
	}

}

inline operator fun <reified T : Strukt> T.invoke(init: T.() -> Unit): StruktPointer {
	val pointer = ++strukt.pointer

	val instance = T::class.objectInstance!!
	instance.init()

	return pointer
}

inline operator fun <reified T : Strukt> T.get(pointer: StruktPointer) = apply {
	strukt.pointer = pointer
}