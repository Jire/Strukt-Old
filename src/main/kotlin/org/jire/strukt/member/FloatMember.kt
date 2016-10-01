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

package org.jire.strukt.member

import it.unimi.dsi.fastutil.longs.Long2FloatArrayMap
import org.jire.strukt.Strukt
import org.jire.strukt.StruktPointer
import org.jire.strukt.member.PrimitiveMapWorkaround.put
import org.jire.strukt.internal.strukt
import kotlin.reflect.KProperty

class FloatMember(private val default: Float) : Member() {

	private val current = ThreadLocal.withInitial { Long2FloatArrayMap() }

	operator fun getValue(ref: Strukt, prop: KProperty<*>) = current.get()[ref.strukt.pointer]

	operator fun setValue(ref: Strukt, prop: KProperty<*>, value: Float) {
		put(current.get(), ref.strukt.pointer, value)
	}

	override fun default(pointer: StruktPointer) {
		put(current.get(), pointer, default)
	}

	init {
		current.get().defaultReturnValue(default)
	}

}